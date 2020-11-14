package hr.fer.zemris.java.hw06.shell;

import java.util.List;

/**
 * Interface of a shell command.
 * 
 * @author Margarita Tolja
 *
 */
public interface ShellCommand {

	/**
	 * Executes the shell command with given arguments using given environment object.
	 * @param env Environment object.
	 * @param arguments Arguments that followed the input of command name.
	 * @return Shell status after the command execution.
	 */
	ShellStatus executeCommand(Environment env, String arguments);
	/**
	 * Gets the name of the shell command.
	 * @return Name of the command.
	 */
	String getCommandName();
	/**
	 * Gets the description of command's usage.
	 * @return Command description.
	 */
	List<String> getCommandDescription();
}
