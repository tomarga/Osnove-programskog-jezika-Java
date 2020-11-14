package hr.fer.zemris.java.hw06.crypto;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Scanner;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * The program allows the user to encrypt/decrypt given file using the AES crypto-algorithm 
 * and the 128-bit encryption key or calculate and check the SHA-256 file digest.
 * 
 * @author Margarita Tolja
 *
 */
public class Crypto {

	/**
	 * Main method manages and checks the input. 
	 * The expected file digest and password and initialisation vector are given through keyboard input.
	 * @param args The valid arguments are "checksha filename" or "encrypt/decrypt originalfilename newfilename".
	 * @throws NoSuchAlgorithmException AES algorithm exception.
	 * @throws NoSuchPaddingException AES padding exception.
	 * @throws InvalidKeyException Invalid AES password.
	 * @throws InvalidAlgorithmParameterException Invalid initialisation vector.
	 * @throws IllegalBlockSizeException Illegal AES block size.
	 * @throws BadPaddingException AES padding exception.
	 * @throws IllegalArgumentException if the input is invalid.
	 */
	public static void main( String[] args ) throws NoSuchAlgorithmException, NoSuchPaddingException, 
	InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
			
		if ( args.length == 0 ) {
			throw new IllegalArgumentException( "Missing comand." );
		}
		//digest
		if ( args[0].equals( "checksha" ) ) {
			
			if ( args.length != 2 ) {
				throw new IllegalArgumentException( "Expected one file name after 'ckechsha' keyword." );
			}
			//reading path
			Path path = null;
			try {
				path = Paths.get( args[1] );
				if ( !Files.exists( path, LinkOption.NOFOLLOW_LINKS ) ) {
					throw new IllegalArgumentException( "The passed file doesn't exist." );
				}
			} catch( InvalidPathException e ) {
				throw new IllegalArgumentException( "Invalid file name." );
			}
			//read expected
			System.out.println( "Please provide expected sha-256 digest for " + args[1] + "." );
			Scanner sc = new Scanner( System.in );
			String digest = sc.next();
			//check digest
			try {
				checkDigest( path, digest );
			} catch (IOException e) {
				System.out.println( "Error while checking digest." );
				return;
			} finally {
				sc.close();
			}
		}
		//encrypt/decrypt
		else if ( args[0].equals( "encrypt" ) || args[0].equals( "decrypt" ) ) {
			
			if ( args.length != 3 ) {
				throw new IllegalArgumentException( "Expected two file names to follow the command." );
			}
			//read password
			System.out.println( "Please provide password as hex-encoded text (16 bytes, i.e. 32 hex-digits):" );
			Scanner sc = new Scanner( System.in );
			String keyText = sc.next();
			//read initialisation vector
			System.out.println( "Please provide initialization vector as hex-encoded text (32 hex-digits):" );
			String ivText = sc.next();
			sc.close();
			
			boolean encrypt = args[0].equals( "encrypt" );
			
			//initialising cipher object
			SecretKeySpec keySpec = new SecretKeySpec( Util.hextobyte( keyText ), "AES" );
			AlgorithmParameterSpec paramSpec = new IvParameterSpec( Util.hextobyte( ivText ) );
			Cipher cipher = Cipher.getInstance( "AES/CBC/PKCS5Padding" );
			cipher.init( encrypt ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE, keySpec, paramSpec );
			//process encrypt/decrypt
			try {
			processCrypt( args[1], args[2], cipher );
			} catch ( IOException e ) {
				System.out.println( "Error while processing crypt.");
				return;
			}
		}
		else {
			throw new IllegalArgumentException( "Invalid command keyword." );
		}
		
	}
	
	/**
	 * Encrypts/decrypts the original file using AES crypto algorithm and saves the result as a new file.
	 * @param originalFile Name of the file to encrypt/decrypt.
	 * @param newFile Name of the result file.
	 * @param cipher AES cipher object.
	 * @throws IOException if stream read/write goes wrong.
	 * @throws IllegalBlockSizeException Illegal AES block size.
	 * @throws BadPaddingException AES padding exception.
	 */
	private static void processCrypt( String originalFile, String newFile, Cipher cipher ) throws 
	IllegalBlockSizeException, BadPaddingException, IOException {
		
		Path pathOriginal = null;
		Path pathNew = null;
		try {
			pathOriginal = Paths.get( originalFile );
			pathNew = Paths.get( newFile );
			if ( !Files.exists( pathOriginal, LinkOption.NOFOLLOW_LINKS ) ) {
				throw new IllegalArgumentException( "The passed original file doesn't exist." );
			}
		} catch( InvalidPathException e ) {
			throw new IllegalArgumentException( "Invalid file name." );
		}
		
		InputStream inputStream = Files.newInputStream( pathOriginal );
		OutputStream outputStream = Files.newOutputStream( pathNew );
		
		byte[] buffer = new byte[4*1024];
		int read = 0;
		
		while( true ) {
			read = inputStream.read( buffer );
			if ( read < buffer.length ) {
				break;
			}
			buffer = cipher.update( buffer );
			outputStream.write( buffer );
		}
		
		byte[] last = cipher.doFinal( buffer, 0, read );	
		outputStream.write( last );
		outputStream.flush();
		outputStream.close();
		inputStream.close();
		
	}

	/**
	 * Checks if file digest of the file with path as passed in the argument is the same as expected.
	 * Outputs the appropriate message.
	 * @param path Path of the file whose digest is computed.
	 * @param expected Expected digest value.
	 * @throws IOException If there's an error with reading from stream.
	 */
	private static void checkDigest( Path path, String expected ) throws IOException {
		
		InputStream inputStream = Files.newInputStream( path );
	
		MessageDigest sha = null;
		try {
			sha = MessageDigest.getInstance("SHA-256");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
		byte[] buffer = new byte[4*1024];
		
		while( true ) {
			int read = inputStream.read( buffer );
			if ( read == -1 ) {
				break;
			}
			sha.update( buffer, 0, read );
		}
		
		byte[] hash = sha.digest();				
		String hashString = Util.bytetohex( hash );
		
		if ( hashString.equals( expected ) ) {
			System.out.println( "Digesting completed. Digest of " + path.getFileName() + 
					" matches expected digest." );			
		} else {
			System.out.println( "Digesting completed. Digest of " + path.getFileName() + 
					"hw06test.bin does not match the expected digest." );
			System.out.println( "Digest was: " + hashString );
		}
	}

}
