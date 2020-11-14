package hr.fer.zemris.java.hw06.shell.commands;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.*;

/**
 * Class that represents 'copy' shell command.
 * 
 * @author Margarita Tolja
 *
 */
public class CopyShellCommand implements ShellCommand {

	/**
	 * Arguments should consist of two paths. The first, source path has to be an existing file's path
	 * and second, destination path can be file path or directory path.
	 * Method copies the source file to destination file.
	 * If the file with the same name already exists, the user is asked whether he wants to overwrite it.
	 * If the second path is a directory, the source file is copied there.
	 * If the passed argument are not as expected, method outputs the appropriate message.
	 * Method returns continue status.
	 */
	@Override
	public ShellStatus executeCommand( Environment env, String arguments ) {
		
		//check if there are any arguments
		if ( arguments.isBlank() ) {
			env.writeln( "'copy' command expects two file paths as arguments." );
			return ShellStatus.CONTINUE;
		}
		//get path to source file
		StringBuilder args = new StringBuilder( arguments.strip() );
		Path sourceFile = null;
		try {
			sourceFile = Util.pathFile( args );
		} catch ( IllegalArgumentException e ) {
			env.writeln( e.getMessage() );
			return ShellStatus.CONTINUE;
		}
		//check if source file is regular file
		if ( !Files.isRegularFile( sourceFile, LinkOption.NOFOLLOW_LINKS ) ) {
			env.writeln( "No such file." );
			return ShellStatus.CONTINUE;
		}
		args = new StringBuilder( args.toString().strip() );
		//get path to new file
		Path destinationFile = null;
		try {
			destinationFile = Util.validPath( args );
		} catch ( IllegalArgumentException e ) {
			env.writeln( e.getMessage() );
			return ShellStatus.CONTINUE;
		}
		//check for unwanted arguments
		if ( !args.toString().isBlank() ) {
			env.writeln( "'copy' command expects two file paths as arguments." );
			return ShellStatus.CONTINUE;
		}
		//check if destination path is directory
		if ( Files.isDirectory( destinationFile, LinkOption.NOFOLLOW_LINKS ) ) {
			destinationFile = Paths.get( destinationFile.toString(), sourceFile.getFileName().toString() );
		} 
		
		try {
			copy( env, sourceFile, destinationFile );
		} catch ( ShellIOException e ) {
			env.writeln( e.getMessage() );
		}
		
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "copy";
	}

	@Override
	public List<String> getCommandDescription() {
		String[] description = new String[] {
				"'copy' command copies the file from source path to the destination path." ,
				"The command expects two arguments: source file name and destination file name.",
				"If destination file exists, the user is asked whether the overwriting is allowed.",
				"If the second argument is directory, the source file is copied there under its original name."
		};
		List<String> listDescription = Arrays.asList( description );
		return java.util.Collections.unmodifiableList( listDescription );
	}

	/**
	 * Utility method. Copies the file from source path to the destination path.
	 * @param env Shell environment.
	 * @param source Path of the source file.
	 * @param destination Path of the destination file.
	 */
	private void copy( Environment env, Path source, Path destination ) {
		
		Path newFile = null;
		
		//overwrite?
		if ( Files.exists( destination ) ) {
			
			env.write( "File with the same name already exists. Do you want to overwrite it?(y/n) " );
			
			String answer = env.readLine();
			if ( answer.equals( "y" ) ) {
				//delete content
				try {
					newFile = destination;
					BufferedWriter writer = Files.newBufferedWriter( newFile );
					writer.write( "" );
					writer.flush();
					writer.close();
				} catch (IOException e1) {
					throw new ShellIOException( "Cannot overwrite." );
				}
			} else {
				return;
			}	
		} else {
			try {
				newFile = Files.createFile( destination );
			} catch (IOException e) {
				throw new ShellIOException( "Unable to create file." );
			}
		}
		
		//copying content
		try ( 
				InputStream in = new BufferedInputStream( new FileInputStream( source.toString() ) );
				OutputStream out = new BufferedOutputStream( new FileOutputStream( newFile.toString() ) ) ) {
		
			byte[] buffer = new byte[4*1024];
			while( true ) {
				int read = in.read( buffer );
				
				out.write( buffer, 0, read );
				out.flush();
				
				if ( read < 4*1024 ) {
					break;
				}
			}		
		} catch ( IOException e ) {
			throw new ShellIOException( "Unable to copy." );
		}
	}
}
