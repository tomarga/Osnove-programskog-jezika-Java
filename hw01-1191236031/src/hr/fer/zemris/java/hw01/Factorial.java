package hr.fer.zemris.java.hw01;

import java.util.Scanner;

/**
 * Class <code>Factorial</code> offers factorial computation 
 * of numbers in interval from 3 to 20(including).
 *  
 * <p> User types number whose fuctorial will be displayed 
 * as output. </p> 
 * @author Margarita Tolja
 */

public class Factorial {
	
	/**
	 * Function checks if String given as argument
	 *  is representation of a whole number.
	 * Argument cannot be <code>null<code>. 
	 * 
	 * @param input String to analyse
	 * @return <code>true</code> if given argument is a String
	 * 		representation of a whole number;
	 * 		<code>false</code> otherwise.
	 */	
	 public static boolean isNumeric( String input ) {
		 int i = 0;
		 
		 if ( input.charAt( 0 ) == '-' ) {
			 i++;
		 }
		 
		 for ( int l = input.length(); i < l; i++ ) {
			 try {
				 Byte.parseByte( String.valueOf( input.charAt( i ) ) );
			 } catch( NumberFormatException ex ) {
				 return false;
			 }
		 }
		 return true;			
	}
	/**
	 * Function computes factorial of a number
	 * that is given in argument as a String.
	 * Argument cannot be <code>null<code>. 
	 * 
	 * @param input String representation of a number
	 * @return Factorial of number accessed from String input.
	 * @throws IllegalArgumentException if number is greater 
	 * than MaxLong or is not in interval from 3 to 20. 
	 */
	public static long factorial( String input ) {	
		long number;
		
		try {
			number = Long.parseLong( input );
		} catch( NumberFormatException ex ) {
			  throw new IllegalArgumentException(
			  "'" + input + "' nije broj u dozvoljenom rasponu." );
		}
		
		if ( number < 3 || number > 20 ) {
			throw new IllegalArgumentException(
			"'" + number + "' nije broj u dozvoljenom rasponu." );
		}
		
		long result = 2L;
		for ( int i = 3; i <= number; i++ ) {
			result *= i;
		}		
		return result;			
	}
	/**
	 * Function manages user's input and calls above functions
	 * in order to output expected result or a corresponding
	 * message. Function terminates only after user inputs "kraj". 
	 * 
	 * @param args
	 */
	public static void main( String[] args ) { 		
		Scanner sc = new Scanner( System.in );
		
		while( true ) {
			System.out.print( "Unesite broj > " );
			String input = sc.next();
			
			if ( !isNumeric( input ) ) {
				if ( input.compareTo( "kraj") == 0 ) {
					System.out.println( "Doviđenja." );
					break;
				}
				System.out.println( "'" + input + "' nije cijeli broj." );
			}
			else {			
				try {
					long result = factorial( input );
					System.out.println( input + "! = " + result );
				} catch( IllegalArgumentException exception ) {
					System.out.println( exception.getMessage() ); 
				}
			}			
		}
		sc.close();
	}
}
