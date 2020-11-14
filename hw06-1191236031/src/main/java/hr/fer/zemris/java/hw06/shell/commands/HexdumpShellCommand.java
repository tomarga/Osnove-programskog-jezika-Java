package hr.fer.zemris.java.hw06.shell.commands;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.*;

/**
 * Class that represents 'hexdump' shell command.
 * 
 * @author Margarita Tolja
 *
 */
public class HexdumpShellCommand implements ShellCommand {

	/**
	 * The argument should contain only one file path.
	 * Method outputs the file's hexdump.
	 * If the passed argument is not a file path, method outputs the appropriate message.
	 * Method returns continue status.
	 */
	@Override
	public ShellStatus executeCommand( Environment env, String arguments ) {
		
		//check if there are any arguments
		if ( arguments.isBlank() ) {
			env.writeln( "'hexdump' command expects a file path for an argument." );
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
		//check for unwanted arguments
		if ( !args.toString().isBlank() ) {
			env.writeln( "'mkdir' command expects only a directory path as an argument." );
			return ShellStatus.CONTINUE;
		}
		
		//output hexdump
		printHexdump( env, path );
		
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return new String( "hexdump" );
	}

	@Override
	public List<String> getCommandDescription() {
		String[] description = new String[] {
				"'hexdump' command produces hex-output of a given file.",
				"The command takes a single argument – file path.",
				"Each line of hexdump output has the following format: ",
				"\"hex-index of the first byte in the line: first eight bytes|next eight bytes | characters subset\"."
		};
		List<String> listDescription = Arrays.asList( description );
		return java.util.Collections.unmodifiableList( listDescription );
	}
	
	/**
	 * Utility method. 
	 * Outputs the hexdump of the given file.
	 * Each line of hexdump output has the following format: 
	 * "hex-index of the first byte in the line: first eight bytes|next eight bytes | characters subset"
	 * @param env Shell environment.
	 * @param file Path to file of interest.
	 */
	public void printHexdump( Environment env, Path file ) {
		
		try ( InputStream is = Files.newInputStream( file ) ) {
			
			byte[] buffer = new byte[16];
			int lineIndex = 0;
			StringBuilder sb = new StringBuilder();
			
			while( true ) {
				
				sb.delete( 0, sb.length() );
				int read = is.read( buffer );
				
				//address
				sb.append( String.format( "%08X", lineIndex * 16 ) ).append( ":");
				
				//first 8 bytes
				int i = 0;
				while( i < buffer.length && i < 8 ) {
					String next = ( i < read ) ? String.format( " %02X", buffer[i] ) : "   ";
					sb.append( next );
					i++;
				}
				sb.append( "|" );
				//next 8 bytes
				while( i < buffer.length ) {
					String next = ( i < read ) ? String.format( "%02X ", buffer[i] ) : "   ";
					sb.append( next );
					i++;
				}
				sb.append( "| " );
				
				//standard subset of characters
				for ( int j = 0; j < read; j++ ) {
					char character = ( buffer[j] < 32 || buffer[j] > 127 ) ? '.' : (char)buffer[j];
					sb.append( character );
				}
				
				env.writeln( sb.toString() );
				
				if ( read < 16 ) {
					break;
				}
				lineIndex ++;
			}
			
		} catch(IOException ex) {
			env.writeln( ex.getMessage() );
		}
	}

}
