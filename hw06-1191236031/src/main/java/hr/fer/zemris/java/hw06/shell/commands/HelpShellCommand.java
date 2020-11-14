package hr.fer.zemris.java.hw06.shell.commands;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;

import hr.fer.zemris.java.hw06.shell.*;

/**
 * Class that represents 'help' shell command.
 * 
 * @author Margarita Tolja
 *
 */
public class HelpShellCommand implements ShellCommand {
	
	private SortedMap<String, ShellCommand> commands;
	
	/**
	 * Sets the map containing all valid commands to the passed value.
	 * @param commands Sorted map (command name, shell command).
	 */
	public HelpShellCommand( SortedMap<String,ShellCommand> commands ) {
		this.commands = commands;
	}

	/**
	 * Arguments should contain at most one command name.
	 * If there is none, the list of all valid shell command names is printed.
	 * If the command name is given, method outputs the description of that command.
	 * If the passed argument is not a command, method outputs the appropriate message.
	 * Method returns continue status.
	 */
	@Override
	public ShellStatus executeCommand( Environment env, String arguments ) {
		
		String args = arguments.strip();
		//to many arguments
		if ( args.split( "//s+" ).length > 1 ) {
			env.writeln( "'help' command takes at most one argument." );
		}
		//no arguments
		else if ( args.isBlank() ) {
			for( Map.Entry<String,ShellCommand> command : commands.entrySet() ) {
				env.writeln( command.getKey() );
			}
		}
		//one argument
		else {
			ShellCommand command = commands.get( args );
			if ( command == null ) {
				env.writeln( args + " is not a valid shell command." );
			} else {
				List<String> description = command.getCommandDescription();
				for ( String line : description ) {
					env.writeln( line );
				}
			}
		}
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return new String( "help" );
	}

	@Override
	public List<String> getCommandDescription() {
		String[] description = new String[] {
				"'help' command offers information about valid shell commands.",
				"The command takes at most one argument - a command name.",
				"If no command name is given, the list of all valid command names is printed.",
				"Otherwise, user is offered description of the command of interest."
		};
		List<String> listDescription = Arrays.asList( description );
		return java.util.Collections.unmodifiableList( listDescription );
	}

}
