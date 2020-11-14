package hr.fer.zemris.java.hw06.shell.commands;

import java.util.Arrays;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * Class that represents the exit command.
 * 
 * @author Margarita Tolja
 *
 */
public class ExitShellCommand implements ShellCommand {

	/**
	 * If the command is valid, meaning no argument values are passed, 
	 * this method changes the shell status to terminate. Otherwise, 
	 * user receives the appropriate message and is free to continue.
	 */
	@Override
	public ShellStatus executeCommand( Environment env, String arguments ) {
		if ( !arguments.isBlank() ) {
			env.writeln( "Invalid exit command." );
			return ShellStatus.CONTINUE;
		}
		return ShellStatus.TERMINATE;
	}

	@Override
	public String getCommandName() {
		return new String( "exit" );
	}

	@Override
	public List<String> getCommandDescription() {
		String[] description = new String[] {
				"'exit' command terminates the program.",
				"It doesn't expect argument values."
		};
		List<String> listDescription = Arrays.asList( description );
		return java.util.Collections.unmodifiableList( listDescription );
	}
	
}
