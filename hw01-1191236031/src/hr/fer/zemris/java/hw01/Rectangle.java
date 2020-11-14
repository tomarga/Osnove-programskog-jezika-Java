package hr.fer.zemris.java.hw01;

import java.util.Scanner;

/**
 * Class <code>Rectangle</code> offers perimeter and area
 * computing for rectangles with given width and length.
 * 
 * <p>User declares width and length of the rectangle, as 
 * arguments or through keyboard.</p>
 * 
 * @author Margarita Tolja
 */
public class Rectangle {
	
	/**
	 * Function manages user input. It terminates only after user
	 * inputs a double value.
	 * 
	 * @param sc Scanner object for reading input
	 * @param query utility variable
	 * @return double value extracted from String input
	 */
	public static double processInput( Scanner sc, String query ) {
		while( true ) {
			System.out.print( "Unesite " + query + " > ");
			
			if ( sc.hasNextLine() ) {
				String Line = sc.nextLine();
				Line.stripLeading();
				Line.stripTrailing();
				
				double value = isDouble( Line );
				if ( value >= 0 ) {
					return value;
				}
				else {
					continue;
				}
			}						
		}		
	}
	
	/**
	 * Function checks if String in argument is 
	 * text representation of number(double). Function outputs
	 * a message if this is not the case.
	 * 
	 * @param input String to analyse
	 * @return exact number(double) extracted from input if possible;
	 * 		'-1' otherwise. 
	 */
	public static double isDouble( String input ) {
		double value = -1;
		try {
			value = Double.parseDouble( input );
		}
		catch( NumberFormatException ex ) {
			System.out.println( "'" + input + "' se ne može protumačiti kao broj.");
			return value;
		}
		
		if ( value < 0 ) {
			System.out.println( "Unijeli ste negativnu vrijednost." );
		}
		return value;
	}
	
	/**
	 * Function computes perimeter of a rectangle with given width
	 * and length values. 
	 * 
	 * @param width 
	 * @param length
	 * @return Wrapper(Double) with value of perimeter.
	 */
	public static Double perimeter( double width, double length ) {
		if ( width == 0 || length == 0 ) {
			return Double.valueOf(0);
		}
		return Double.valueOf( 2 * width + 2 * length );
	}
	
	/**
	 * Function computes area of a rectangle with given width
	 * and length values. 
	 * 
	 * @param width 
	 * @param length
	 * @return Wrapper(Double) with value of area.
	 */
	public static Double area( double width, double length ) {
		return Double.valueOf( width * length );
	}
	/**
	 * Function manages argument input, displays corresponding
	 * messages and calls above functions to get results. Function 
	 * displays final output.
	 * 
	 * @param args
	 */
	public static void main( String[] args ) { 	
		Scanner sc = new Scanner( System.in );
		
		if ( args.length == 2 ) {			
			double width = isDouble( args[0] );
			double length = isDouble( args[1] );
			if ( width >= 0 && length >= 0 ) {				
				System.out.println( "Pravokutnik širine " + Double.valueOf( width ) + 
				" i visine " + Double.valueOf( length ) + " ima površinu " +  area( width, length ).toString() +
				 " te " + "opseg " + perimeter( width, length ).toString() + "." );
			}
		}
		
		else if ( args.length == 0 ) {
			Double width = Double.valueOf( processInput( sc, "širinu" ) );
			Double length = Double.valueOf( processInput( sc, "visinu" ) );

			System.out.println( "Pravokutnik širine " + width.toString() + 
			" i visine " + length.toString() + " ima površinu " +  area( width, length ).toString() +
			" te " + "opseg " + perimeter( width, length ).toString() + "." );			
		}
		else {
			System.out.println( "Predano je " + args.length + " argumenata, a potrebna su 2." );
		}		
		sc.close();		
	}
}
