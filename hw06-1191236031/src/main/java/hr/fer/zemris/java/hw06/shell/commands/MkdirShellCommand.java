package hr.fer.zemris.java.hw06.shell.commands;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * Class that represents 'mkdir' shell command.
 * 
 * @author Margarita Tolja
 *
 */
public class MkdirShellCommand implements ShellCommand {

	/**
	 * The arguments should be consisted of single directory path.
	 * Method creates the appropriate directory structure.
	 * If the passed argument is not a directory path, method outputs the appropriate message.
	 * Method returns continue status.
	 */
	@Override
	public ShellStatus executeCommand( Environment env, String arguments ) {
		
		//check if there are any arguments
		if ( arguments.isBlank() ) {
			env.writeln( "'mkdir' command expects a directory path as an argument." );
			return ShellStatus.CONTINUE;
		}
		
		StringBuilder args = new StringBuilder( arguments.strip() );
		Path path = null;
		try {
			path = Util.validPath( args );
		} catch ( IllegalArgumentException e ) {
			env.writeln( e.getMessage() );
			return ShellStatus.CONTINUE;
		}
		//check for unwanted arguments
		if ( !args.toString().isBlank() ) {
			env.writeln( "'mkdir' command expects only a directory path as an argument." );
			return ShellStatus.CONTINUE;
		}
		//create directories
		try {
			Files.createDirectories( path );
		} catch ( IOException e ) {
			env.writeln( "Unable to create the directory structure." );
		}
		
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return new String( "mkdir" );
	}

	@Override
	public List<String> getCommandDescription() {
		String[] description = new String[] {
				"'mkdir' command creates the appropriate directory structure.",
				"The command takes a single argument – directory.",
				"If there is any, the command creates all nonexistent parent directories first."
		};
		List<String> listDescription = Arrays.asList( description );
		return java.util.Collections.unmodifiableList( listDescription );
	}

}
