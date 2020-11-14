package hr.fer.zemris.java.hw06.shell.commands;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.NotDirectoryException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.*;

/**
 * Class that represents 'tree' shell command.
 * 
 * @author Margarita Tolja
 *
 */
public class TreeShellCommand implements ShellCommand {

	/**
	 * Prints out the given directory's tree structure.
	 * Each next directory level is shifted two characters to the right.
	 * Skips hidden files.
	 * If the passed argument is not a directory path, method outputs the appropriate message.
	 * Method returns continue status.
	 */
	@Override
	public ShellStatus executeCommand( Environment env, String arguments ) {
		
		//check if there are any arguments
		if ( arguments.isBlank() ) {
			env.writeln( "'tree' command expects a directory path as an argument." );
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
		//check if path to directory
		if ( !Files.isDirectory( path, LinkOption.NOFOLLOW_LINKS ) ) {
			env.writeln( "No such directory." );
			return ShellStatus.CONTINUE;
		}
		//check for unwanted arguments
		if ( !args.toString().isBlank() ) {
			env.writeln( "'tree' command expects only a directory path as an argument." );
			return ShellStatus.CONTINUE;
		}
		
		printSubfiles( env, path, 0 );
		
		return ShellStatus.CONTINUE;
	}


	@Override
	public String getCommandName() {
		return new String( "tree" );
	}

	@Override
	public List<String> getCommandDescription() {
		String[] description = new String[] {
				"'tree' command prints out the tree structure of given directory.",
				"The command expects a single argument: directory name.",
				"Each next directory level is shifted two characters to the right.",
				"Hidden files are not shown in a tree."
		};
		List<String> listDescription = Arrays.asList( description );
		return java.util.Collections.unmodifiableList( listDescription );
	}

	/**
	 * Utility method.
	 * Recursively prints the given directory's tree structure.
	 * Skips hidden files.
	 * @param env Shell environment.
	 * @param path Path to directory.
	 * @param level Level of directory (root directory's level is 0).
	 */
	private void printSubfiles( Environment env, Path path, int level ) {
		
		//check if hidden
		try {
			if ( Files.isHidden( path ) ) {
				return;
			}
		} catch ( IOException e1 ) {
		}
		
		//print current
		if ( level == 0 ) {
			env.writeln( path.getFileName().toString() );
		} else {
			String current = String.format( "%" + level + "s%s", "", path.getFileName() );
			env.writeln( current );
		}
		
		//print subfiles
		try ( DirectoryStream<Path> stream = Files.newDirectoryStream( path ) ){
			
			for ( Path subfile : stream ) {			
				printSubfiles( env, subfile, level+2 );
			}
			
		} catch ( NotDirectoryException e ) {
			return;
		} catch ( IOException e ) {
			String current = String.format( "%" + ( level+2 ) + "s%s%", "?" );
			env.writeln( current );
		}
				
	}
}
