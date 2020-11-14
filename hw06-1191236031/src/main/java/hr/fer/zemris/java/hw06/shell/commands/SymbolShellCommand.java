package hr.fer.zemris.java.hw06.shell.commands;

import java.util.Arrays;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * Class that represents 'symbol' shell command.
 * 
 * @author Margarita Tolja
 *
 */
public class SymbolShellCommand implements ShellCommand {

	/**
	 * Depending on the number of passed arguments, the command gives the
	 * information about current special symbol or changes it to a new value.
	 * Method returns continue status.
	 */
	@Override
	public ShellStatus executeCommand( Environment env, String arguments ) {
		
		arguments = arguments.strip();
		String[] args = arguments.split( "\\s+" );
		//check the number of arguments
		if ( args.length != 1 && args.length != 2 ) {
			env.writeln( "'symbol' command expects the name of the symbol possibly followed by a new symbol." );
			return ShellStatus.CONTINUE;
		}
		//extract new symbol if possible
		Character newSymbol = null;
		if ( args.length == 2 ) {
			if ( args[1].length() != 1 ) {
				env.writeln( "New value has to be a symbol." );
				return ShellStatus.CONTINUE;
			}
			newSymbol = args[1].charAt( 0 );
			if ( Character.isLetter( newSymbol ) || Character.isDigit( newSymbol ) ) {
				env.writeln( "New value has to be a symbol." );
				return ShellStatus.CONTINUE;			
			}	
		}
		//execute command
		switch ( args[0] ) {
		case "PROMPT" :
			if ( args.length == 2 ) {
				if ( newSymbol != null ) {
					env.writeln( "Symbol for PROMPT changed from '" 
							+ env.getPromptSymbol() + "' to '" + newSymbol + "'." );
					env.setPromptSymbol( newSymbol );
				}
			} else {
				env.writeln( "Symbol for PROMPT is '" + env.getPromptSymbol() + "'." );
			}
			break;
		case "MORELINES" :
			if ( args.length == 2 ) {
				if ( newSymbol != null ) {
					env.writeln( "Symbol for MORELINES changed from '" 
							+ env.getMorelinesSymbol() + "' to '" + newSymbol + "'." );
					env.setMorelinesSymbol( newSymbol );
				}
			} else {
				env.writeln( "Symbol for MORELINES is '" + env.getMorelinesSymbol() + "'." );
			}
			break;
		case "MULTILINE" :
			if ( args.length == 2 ) {
				if ( newSymbol != null ) {
					env.writeln( "Symbol for MULTILINE changed from '" 
							+ env.getMultilineSymbol() + "' to '" + newSymbol + "'." );
					env.setMultilineSymbol( newSymbol );
				}
			} else {
				env.writeln( "Symbol for MULTILINE is '" + env.getMultilineSymbol() + "'." );
			}
			break;
		default : 
			env.writeln( "Valid symbol's keywords are PROMPT, MORELINES and MULTILINE." );
		}
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return new String( "symbol" );
	}

	@Override
	public List<String> getCommandDescription() {
		String[] description = new String[] {
				"'symbol' command expects one or two arguments.",
				"First argument has to be a special symbol's keyword: PROMPT/MORELINES/MULTILINE. ",
				"Second argument has to be a symbol.",
				"If only one argument is given, the command offers the information about current symbol used"
				+ " for specified purpose.",
				"If both arguments are given, the special symbol is changed to the new one."				
		};
		List<String> listDescription = Arrays.asList( description );
		return java.util.Collections.unmodifiableList( listDescription );
	}

}
