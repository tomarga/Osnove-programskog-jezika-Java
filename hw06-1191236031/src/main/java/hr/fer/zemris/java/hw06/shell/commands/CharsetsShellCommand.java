package hr.fer.zemris.java.hw06.shell.commands;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;
import java.util.Map.Entry;
import java.util.SortedMap;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * Class that represents 'charsets' shell command.
 * 
 * @author Margarita Tolja
 *
 */
public class CharsetsShellCommand implements ShellCommand {

	/**
	 * Lists the names of supported character sets for current Java platform.
	 */
	@Override
	public ShellStatus executeCommand( Environment env, String arguments ) {
		//check for arguments
		if ( !arguments.isBlank() ) {
			env.writeln( "'charsets' command expects no arguments." );
			return ShellStatus.CONTINUE;
		}
		//map of available character sets
		SortedMap<String,Charset> charsetMap = Charset.availableCharsets();
		//output
		for( Entry<String, Charset> charsetEntry : charsetMap.entrySet() ) {
			env.writeln( charsetEntry.getKey() );
		}
	
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return new String( "charsets" );
	}

	@Override
	public List<String> getCommandDescription() {
		String[] description = new String[] {
				"'charsets' command lists the names of supportet character sets for current Java platform.",
				"The command doesn't expect any arguments."
		};
		List<String> listDescription = Arrays.asList( description );
		return java.util.Collections.unmodifiableList( listDescription );
	}

}
