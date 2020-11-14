package hr.fer.zemris.java.hw06.shell.commands;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;


import hr.fer.zemris.java.hw06.shell.*;

/**
 * Class that represents the ls command.
 * 
 * @author Margarita Tolja
 *
 */
public class LsShellCommand implements ShellCommand{

	/**
	 * Lists the direct subfiles' information( drwx, file size, date-time, file name).
	 * Hidden directories are not shown.
	 * Outputs the appropriate message to console if the string argument doesn't contain path to directory.
	 * Method returns continue status.
	 */
	@Override
	public ShellStatus executeCommand( Environment env, String arguments ) {
		
		//check if there are any arguments
		if ( arguments.isBlank() ) {
			env.writeln( "'ls' command expects a directory path as an argument." );
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
			env.writeln( "'ls' command expects only a directory path as an argument." );
			return ShellStatus.CONTINUE;
		}
		//output
		try ( DirectoryStream<Path> stream = Files.newDirectoryStream( path ) ){
			
			for ( Path file : stream ) {
				//skip hidden files
				if ( Files.isHidden( file ) ) {
					continue;
				}
				env.writeln( fileInfo( file ) );
			}
			
		} catch (IOException e) {
			env.writeln( "Cannot open directory." );
		}

		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return new String( "ls" );
	}

	@Override
	public List<String> getCommandDescription() {
		String[] description = new String[] {
				"'ls' command writes a directory listing (not recursive).",
				"The command takes a single argument – directory.",
				"Hidden subfiles are not shown in the list."
		};
		List<String> listDescription = Arrays.asList( description );
		return java.util.Collections.unmodifiableList( listDescription );
	}
	
	/**
	 * Utility method. Gets the information about file given as an argument.
	 * Info format is as follows: "drwx file-size-in-bytes date-time file-name".
	 * @param file Path to file of interest.
	 * @return Information as a string made of four columns.
	 */
	private String fileInfo( Path file ) {
		
		StringBuilder sb = new StringBuilder();
		
		//first column (drwx)
		//directory?
		if ( Files.isDirectory( file, LinkOption.NOFOLLOW_LINKS ) ) {
			sb.append( 'd' );
		} else {
			sb.append( '-' );
		}
		//readable?
		if ( Files.isReadable( file ) ) {
			sb.append( 'r' );
		} else {
			sb.append( '-' );
		}
		//writable?
		if ( Files.isWritable( file ) ) {
			sb.append( 'w' );
		} else {
			sb.append( '-' );
		}
		//executable?
		if ( Files.isExecutable( file ) ) {
			sb.append( "x " );
		} else {
			sb.append( "- " );
		}
		
		//second column (file size)
		try {
			sb.append( String.format( "%10s ", Files.size( file ) ) );
		} catch ( IOException e ) {
			sb.append( String.format( "%10s ", "?" ) );
		}
		
		//third column (date-time)
		SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
		
		BasicFileAttributeView faView = Files.getFileAttributeView(
				file, BasicFileAttributeView.class, LinkOption.NOFOLLOW_LINKS
				);
		BasicFileAttributes attributes = null;
		try {
			attributes = faView.readAttributes();
		} catch (IOException e) {
			sb.append( String.format( "%19s ", "?" ) );
			sb.append( file.getFileName() );
			
			return sb.toString();
		}
		FileTime fileTime = attributes.creationTime();
		String formattedDateTime = sdf.format(new Date(fileTime.toMillis()));
		sb.append( formattedDateTime ).append( " " );
		
		//fourth column (file name)
		sb.append( file.getFileName() );
		
		return sb.toString();
	}

}
