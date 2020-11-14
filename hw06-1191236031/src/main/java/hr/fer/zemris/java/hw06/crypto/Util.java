package hr.fer.zemris.java.hw06.crypto;

import java.util.Map;
import java.util.HashMap;

/**
 * Utility class used for converting between hex-encoded string 
 * and appropriate array of bytes.
 * 
 * @author Margarita Tolja
 *
 */
public class Util {
	
	private static final Map<Character, Integer> letterToNumber;
	private static final Map<Integer, Character> numberToLetter;
	
	static {
		letterToNumber = new HashMap<>();
		letterToNumber.put( 'a', 10 );
		letterToNumber.put( 'b', 11 );
		letterToNumber.put( 'c', 12 );
		letterToNumber.put( 'd', 13 );
		letterToNumber.put( 'e', 14 );
		letterToNumber.put( 'f', 15 );
	}	
	static {
		numberToLetter = new HashMap<>();
		for ( Map.Entry<Character, Integer> tuple : letterToNumber.entrySet() ) {
			numberToLetter.put( tuple.getValue(), tuple.getKey() );
		}
	}
	
	/**
	 * Returns the appropriate array of bytes represented by the given hex-encoded string in argument.
	 * @throws IllegalArgumentException if the odd sized string is passed or if the string contains 
	 * values other than numbers 0-9 or letters a-f.
	 * @param hex Hex-encoded string.
	 * @return Array of bytes.
	 */
	public static byte[] hextobyte( String hex ) {
		
		//odd size
		if ( hex.length() % 2 == 1 ) {
			throw new IllegalArgumentException( 
					"Only even sized hex-encoded strings can be converted to array of bytes." );
		}
		byte[] byteArray = new byte[hex.length() / 2];
		
		for (int i = 0; i < hex.length(); i += 2) {
			String pair = hex.substring( i, i+2 ).toLowerCase();
			//extract values
			int firstValue = isValid( pair.charAt( 0 ) );
			int secondValue = isValid( pair.charAt( 1 ) );		
			byte resultValue = (byte) (firstValue * 16 + secondValue);
			
			byteArray[i/2] = resultValue;		
		}
		return byteArray;
	}
	
	/**
	 * Returns the hex-encoded string representation of the given array of bytes.
	 * @param byteArray Array of byte values.
	 * @return String.
	 */
	public static String bytetohex( byte[] byteArray ) {
		
		StringBuilder sb = new StringBuilder(0);
		
		for ( byte oneByte : byteArray ) {
			int positiveValue = oneByte < 0 ? ( 256 + oneByte )  : oneByte;
			
			//first character
			int first =  positiveValue / 16;
			String firstString = numberToLetter.containsKey( first ) ? 
					String.valueOf( numberToLetter.get( first ) ) : String.valueOf( first );
			sb.append( firstString );
			
			//second character
			int second = positiveValue % 16;
			String secondString = numberToLetter.containsKey( second ) ? 
					String.valueOf( numberToLetter.get( second ) ) : String.valueOf( second ); 
			sb.append( secondString );
		}
		
		return sb.toString();
	}
	
	/**
	 * Utility method. Checks if the passed character is valid hex character( 0-9 or a-f ).
	 * @throws IllegalArgumentException if it is not.
	 * @param hexChar Character to be checked.
	 * @return The integer that the character value represents.
	 */
	private static int isValid( char hexChar ) {
		
		//(a-f)?
		if ( letterToNumber.containsKey( hexChar ) ) {
			return letterToNumber.get( hexChar );
		}
		//(0-9)?
		Integer value;
		try {
			value = Integer.parseInt( String.valueOf( hexChar ) );
			if ( value >= 0 && value <= 9 ) {
				return value;
			}
		} catch ( NumberFormatException e ) {
		}		
		throw new IllegalArgumentException( 
				"Hex-encoded string must only be consisted of numbers(0-9) and letters(a-f)." );
	}
}
