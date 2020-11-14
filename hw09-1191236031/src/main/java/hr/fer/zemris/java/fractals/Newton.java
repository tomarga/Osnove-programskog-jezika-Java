package hr.fer.zemris.java.fractals;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import hr.fer.zemris.java.fractals.viewer.FractalViewer;
import hr.fer.zemris.math.Complex;
import hr.fer.zemris.math.ComplexRootedPolynomial;

/**
 * Program asks user to enter roots of polynomial, 
 * and then it display the appropriate Newton-Raphson fractal.
 * 
 * @author Margarita Tolja
 *
 */
public class Newton {

	/**
	 * Manages keyboard input and the fractal's display.
	 * @param args		No argument values expected.
	 */
	public static void main( String[] args ) {
		
		Scanner sc = new Scanner( System.in );
	
		//input
		System.out.println( "Welcome to Newton-Raphson iteration-based fractal viewer." );
		System.out.println( "Please enter at least two roots, one root per line. Enter 'done' when done." );
		
		List<Complex> rootsList = new ArrayList<Complex>();
		while( true ) {
			
			System.out.print( "Root " + (rootsList.size()+1) + "> " );
			String line = new String();
			line = sc.nextLine();
			if ( line.equals( "done" ) ) {
				break;
			}
			try {
				rootsList.add( parseComplex( line ) );
			} catch( NumberFormatException e ) {
				System.out.println( "Invalid complex number format." );
			}
		}
		
		Complex[] roots = new Complex[rootsList.size()];
		int i = 0;
		for ( Complex root : rootsList ) {
			roots[i++] = root;
		}
		
		System.out.println( "Image of fractal will appear shortly. Thank you." );
		FractalViewer.show( new NewtonProducer( new ComplexRootedPolynomial( Complex.ONE, roots ) ) );
		
		sc.close();
	}
	
	/**
	 * Parses the complex number contained in the passed string.
	 * @param input		String.
	 * @return			The appropriate complex number.
	 * @throws NumberFormatException 	If the format of the input string is invalid.
	 */
	private static Complex parseComplex( String input ) {
		
		if ( input.isBlank() ) {
			throw new NumberFormatException();
		}
		
		double real = 0;
		double imaginary = 0;
		double sign = 1;
		
		int i = 0;

		//skip minus
		if ( input.charAt( i ) == '-' ) {
			sign = -1;
			i++;
		}
		//only imaginary part
		if ( input.charAt( i ) == 'i' ) {
			i++;
			if ( input.substring( i ).isEmpty() ) {
				imaginary = 1 * sign;
			} else {
				imaginary = Double.parseDouble( input.substring( i ) ) * sign;
			}
			return new Complex( real, imaginary );
		}
		//first real part
		while( i < input.length() && input.charAt( i ) != '+' && input.charAt( i ) != '-' ) {
			i++;
		}
		if ( i != input.length() ) {
			sign = input.charAt( i ) == '-' ? -1 : 1;
		}
		//parse real
		real = Double.parseDouble( input.substring( 0, i ) );
		
		//only real part
		if ( i == input.length() ) {
			return new Complex( real, 0 );
		}
		//check for i
		if  ( ++i >= input.length() || input.charAt( i ) != 'i' ) {
			throw new NumberFormatException();
		}
		//parse imaginary
		i++;
		if ( input.substring( i ).isEmpty() ) {
			imaginary = 1 * sign;
		} else {
			imaginary = Double.parseDouble( input.substring( i ) ) * sign;
		}
		
		return new Complex( real, imaginary );
	}

}
