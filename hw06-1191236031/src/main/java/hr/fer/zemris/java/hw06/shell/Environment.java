package hr.fer.zemris.java.hw06.shell;

import java.util.SortedMap;

/**
 * Interface of shell environment.
 * 
 * @author Margarita Tolja
 *
 */
public interface Environment {

	/**
	 * Reads the line that was last inputed in shell.
	 * @return String containing that line.
	 * @throws ShellIOException If reading fails.
	 */
	String readLine() throws ShellIOException;
	/**
	 * Outputs the text in argument to shell.
	 * @param text Text to be written.
	 * @throws ShellIOException If writing fails.
	 */
	void write(String text) throws ShellIOException;
	/**
	 * Outputs the text in argument to shell and moves the cursor to the next line.
	 * @param text Text to be written.
	 * @throws ShellIOException If writing fails.
	 */
	void writeln(String text) throws ShellIOException;
	/**
	 * Gets the map of all valid shell commands. 
	 * @return Unmodifiable map with command names as keys and command objects as values.
	 */
	SortedMap<String, ShellCommand> commands();
	/**
	 * Gets the symbol that the shell writes at the beginning of a line 
	 * that is part of multi-line command.
	 * @return Multiline character.
	 */
	Character getMultilineSymbol();
	/**
	 * Sets the multiline symbol to the symbol passed as argument.
	 * @param symbol New multiline symbol.
	 */
	void setMultilineSymbol(Character symbol);
	/**
	 * Gets the symbol that the shell writes before each user input.
	 * @return Prompt character.
	 */
	Character getPromptSymbol();
	/**
	 * Sets the prompt symbol to the symbol passed as argument.
	 * @param symbol New prompt symbol.
	 */
	void setPromptSymbol(Character symbol);
	/**
	 * Gets the symbol that the user write to indicate that the command spans across 
	 * the next line as well.
	 * @return Morelines character.
	 */
	Character getMorelinesSymbol();
	/**
	 * Sets the morelines symbol to the symbol passed as argument.
	 * @param symbol New morelines symbol.
	 */
	void setMorelinesSymbol(Character symbol);
	
}
