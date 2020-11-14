package hr.fer.zemris.java.hw06.shell.commands;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.*;

/**
 * Class that represents the cat command.
 * 
 * @author Margarita Tolja
 *
 */
public class CatShellCommand implements ShellCommand {

	/**
	 * The arguments string should contain path to some file, and possibly a charset name.
	 * Method outputs the given file contents to console using the given charset (or default one).
	 * If arguments are not as expected the appropriate message is written to console.
	 * Method returns continue status.
	 */
	@Override
	public ShellStatus executeCommand( Environment env, String arguments ) {
		
		//check if there are any arguments
		if ( arguments.isBlank() ) {
			env.writeln( "'cat' command's argument should be a file path possibly followed by a charset name." );
			return ShellStatus.CONTINUE;
		}
		
		StringBuilder args = new StringBuilder( arguments.strip() );
		Path path = null;
		try {
			path = Util.pathFile( args );
		} catch ( IllegalArgumentException e ) {
			env.writeln( e.getMessage() );
			return ShellStatus.CONTINUE;
		}
		//check if path to file
		if ( !Files.isRegularFile( path, LinkOption.NOFOLLOW_LINKS ) ) {
			env.writeln( "No such file." );
			return ShellStatus.CONTINUE;
		}
		//extract charset argument if available
		String charsetName = args.toString().strip();
		Charset charset = Charset.defaultCharset();
		if ( !charsetName.isBlank() ) {
			charset = Charset.availableCharsets().get( charsetName );
			if ( charset == null ) {
				env.writeln( "Invalid charset argument." );
				return ShellStatus.CONTINUE;
			}
		}
		//read file
		try ( InputStream is = Files.newInputStream( path ) ) {
			
			byte[] buffer = new byte[4*1024];
			while( true ) {
				int read = is.read( buffer );
				
				String text = new String( buffer, charset );
				env.write( text );
				
				if ( read < 4*1024 ) {
					env.writeln( "" );
					break;
				}
			}
		} catch(IOException ex) {
			env.writeln( ex.getMessage() );
		}
		
		return ShellStatus.CONTINUE;
	}
	
	@Override
	public String getCommandName() {
		return new String( "cat" );
	}

	@Override
	public List<String> getCommandDescription() {
		String[] description = new String[] {
				"'cat' command opens given file and writes its content to console.",
				"The command takes one or two arguments.",
				"The first argument is path to some file and is mandatory.",
				"The second argument is charset name that will be used to interpret chars from bytes.",
				"If not provided, a default platform charset is used."
		};
		List<String> listDescription = Arrays.asList( description );
		return java.util.Collections.unmodifiableList( listDescription );
	}
	
}
