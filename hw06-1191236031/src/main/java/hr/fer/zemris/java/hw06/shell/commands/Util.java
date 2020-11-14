package hr.fer.zemris.java.hw06.shell.commands;

import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Utility class that offers methods used to check if the string input 
 * really contains an appropriate file path where expected.
 * 
 * @author Margarita Tolja
 *
 */
public class Util {
	
	/**
	 * Checks if the string passed as argument contains existing file path at the beginning.
	 * The part of parsed path in input string builder is then deleted.
	 * @throws IllegalArgumentException if path is invalid.
	 * @param input String builder containing input.
	 * @return Path to file.
	 */
	public static Path pathFile( StringBuilder input ) {
		
		Path path = validPath( input );
		
		if ( !Files.exists( path, LinkOption.NOFOLLOW_LINKS ) ) {
			throw new IllegalArgumentException( "No such file or directory." );
		}
		return path;
	}

	/**
	 * Checks if the string passed as argument contains valid file path at the beginning.
	 * The part of parsed path in input string builder is then deleted.
	 * @throws IllegalArgumentException if path is invalid.
	 * @param input String builder containing input.
	 * @return Path to file.
	 */
	public static Path validPath( StringBuilder input ) {
		
		String inputPath = parsePath( input );	
		
		Path path = null;
		try {
			path = Paths.get( inputPath );
		} catch ( InvalidPathException e ) {
			throw new IllegalArgumentException( "Invalid path to file." );
		}		
		return path;
	}
	
	/**
	 * Parses next file/directory path. Deletes the parsed part from the passed string builder. 
	 * If the path is given as a quoted sequence, the following escaping is permitted:
	 *  '\\' for '\' and '\"' for quotes that are considered part of the file name. 
	 * @param input String that contains file/directory path at the beginning.
	 * @return Unchanged path if the path is not given in quotes.
	 * 		   Path without quotes and escaping symbols, otherwise.
	 * 		   Null value if the closing quote is missing.
	 */
	private static String parsePath( StringBuilder input ) {
		
		String inputString = input.toString();
		if ( inputString.isEmpty() ) {
			return inputString;
		}
		
		//path without quotes
		if ( inputString.charAt( 0 ) != '"' ) {
			String path = inputString.split( "\\s+" )[0];
			input.delete( 0, path.length() );
			return path;
		}
		
		//path with quotes
		StringBuilder sb = new StringBuilder();
		
		int i = 1;
		while ( i < inputString.length() ) {	
			
			char currentChar = inputString.charAt( i );
			//check for end quotes
			if ( currentChar == '"' ) {
				input.delete( 0, i+1 );
				return sb.toString();
			}
			//manage escaping
			if ( currentChar == '\\' && i < inputString.length() - 1 ) {
				char nextChar = inputString.charAt( i+1 );
				if ( nextChar == '\\' || nextChar == '"' ) {
					sb.append( nextChar );
					i += 2;
					continue;
				}
			}
			//add current
			sb.append( currentChar );
			i++;
		}
		return null;
	}
}
