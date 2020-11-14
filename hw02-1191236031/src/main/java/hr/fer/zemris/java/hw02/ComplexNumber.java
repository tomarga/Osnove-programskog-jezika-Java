package hr.fer.zemris.java.hw02;

/**
 * A class that represents unmodifiable complex number.
 * The class provides many methods often needed when 
 * working with complex numbers.
 * 
 * @author Margarita Tolja
 *
 */
public class ComplexNumber {
	
	private final double real;
	private final double imaginary;

	/**
	 * Creates an complex number instance with given real
	 * and imaginary values.
	 * @param real
	 * 		Real value of complex number.
	 * @param imaginary
	 * 		Imaginary value of complex number.
	 */
	public ComplexNumber( double real, double imaginary ) {
		this.real = real;
		this.imaginary = imaginary;
	}
	
	/**
	 * Creates a representation of a complex number with given real, 
	 * and no imaginary value.
	 * @param real
	 * 		Real number.
	 * @return
	 * 		ComplexNumber instance with given real value.
	 */
	public static ComplexNumber fromReal( double real ) {
		return new ComplexNumber( real, 0 );
	}
	/**
	 * Creates a representation of a complex number with given imaginary, 
	 * and no real value.	 
	 * @param imaginary
	 * 		Imaginary value of complex number.
	 * @return
	 * 		ComplexNumber instance with given imaginary value.
	 */
	public static ComplexNumber fromImaginary( double imaginary ) {
		return new ComplexNumber( 0, imaginary );
	}
	/**
	 * Creates a representation of a complex number with given magnitude
	 * and angle. Angle is supposed to be given in radians.
	 * @param magnitude
	 * 		Magnitude of complex number.
	 * @param angle
	 * 		Angel of complex number in polar form.
	 * @return
	 * 		Appropriate ComplexNumber instance.
	 */
	public static ComplexNumber fromMagnitudeAndAngle( double magnitude, double angle ) {
		//assuming that angle is in radians
		return new ComplexNumber( magnitude * Math.cos( angle ), magnitude * Math.sin( angle ) );
	}
	/**
	 * Creates a representation of a complex value extracted from given String.
	 * @param s
	 * 		Complex number in String form.
	 * @return
	 * 		Appropriate ComplexNumber instance.	
	 */
	public static ComplexNumber parse( String s ) {
		
		if ( s.isBlank() ) {
			throw new NumberFormatException( "Empty string cannot be parsed into complex number." );
		}
		
		double real = 0;
		double imaginary = 0;
		
		int i = 0;

		//skip leading plus/minus signs
		while( s.charAt( i ) == '+' || s.charAt( i ) == '-' ) {
			i++;
		}
		//find first substring with double value
		while( i < s.length() && s.charAt( i ) != '+' && s.charAt( i ) != '-' && s.charAt( i ) != 'i' ) {
			i++;
		}
		//if number has only imaginary part
		if ( i == ( s.length() - 1 ) && s.charAt( i ) == 'i' ) {
			imaginary = parseSubstring( s, 0, i );
		}
		//if number has only real part
		else if ( i == s.length() ) {
			real = parseSubstring( s, 0, i );
		}
		//if number has both real and imaginary part
		else {
			real = parseSubstring( s, 0, i );
			
			//checks if imaginary part ends with 'i'
			if ( s.charAt( s.length() - 1 ) != 'i' ) {
				throw new NumberFormatException();
			}
			
			imaginary = parseSubstring( s, i, s.length() - 1 );
		}
		
		return new ComplexNumber( real, imaginary );
	}
	/**
	 * Utility method.
	 * Parses real numbers from substring specified through passed parameters. 
	 * @param s
	 * 		String used for finding substring.
	 * @param beginIndex
	 * 		Begin index of substring.
	 * @param endIndex
	 * 		End index of substring.
	 * @return
	 * 		Double value of number in substring.
	 */
	private static double parseSubstring( String s, int beginIndex, int endIndex ) {
		
		String substring = s.substring( beginIndex, endIndex );

		if ( substring.equals( s ) ) {
			return Double.parseDouble( substring );
		}
		else {	
			switch( s.charAt( endIndex ) ) {
				case '+':
					return Double.parseDouble( substring );
				case '-':
					return Double.parseDouble( substring );
				case 'i':
					if ( substring.equals( "-" ) ) {
						return -1;
					}
					if ( substring.equals( "+" ) || substring.isBlank() ) {
						return 1;
					}
					return Double.parseDouble ( substring );
			}
		}
		return 0;
	}
	/**
	 * Gets real part of complex number.
	 * @return
	 * 		Re(complex number).
	 */
	public double getReal() {
		return real;
	}
	/**
	 * Gets imaginary part of complex number.
	 * @return
	 * 		Im(complex number).
	 */
	public double getImaginary() {
		return imaginary;
	}
	/**
	 * Gets magnitude of complex number.
	 * @return
	 * 		Magnitude(r) of complex number.
	 */
	public double getMagnitude() {
		return Math.sqrt( real * real + imaginary * imaginary );
	}
	/**
	 * Gets angle of complex number in polar coordinates.
	 * @return
	 * 		Angle in radians.
	 */
	public double getAngle() {
		return imaginary >= 0 ? Math.atan2(imaginary, real) : 2 * Math.PI + Math.atan2(imaginary, real);
	}
	
	/**
	 * Adds the number in parameter to the complex number 
	 * and returns the result.
	 * @param c
	 * 		Complex number, second operand.
	 * @return
	 * 		New instance of ComplexNumber that represents the result.
	 */
	public ComplexNumber add( ComplexNumber c ) {
		return new ComplexNumber( real + c.real, imaginary + c.imaginary );
	}
	/**
	 * Subtracts the number in parameter from the complex number 
	 * and returns the result.
	 * @param c
	 * 		Complex number, second operand.
	 * @return
	 * 		New instance of ComplexNumber that represents the result.
	 */
	public ComplexNumber sub( ComplexNumber c ) {
		return new ComplexNumber( real - c.real, imaginary - c.imaginary );
	}
	/**
	 * Multiplicates the number in parameter by the complex number 
	 * and returns the result.
	 * @param c
	 * 		Complex number, second operand.
	 * @return
	 * 		New instance of ComplexNumber that represents the result.
	 */
	public ComplexNumber mul( ComplexNumber c ) {
		double realPart = real * c.real - imaginary * c.imaginary;
		double imaginaryPart = real * c.imaginary + imaginary * c.real;
		return new ComplexNumber( realPart, imaginaryPart );
	}
	/**
	 * Divides the complex number by the number in parameter and returns the result.
	 * @param c
	 * 		Complex number, second operand.
	 * @return
	 * 		New instance of ComplexNumber that represents the result.
	 */
	public ComplexNumber div( ComplexNumber c ) {
		if ( c.real == 0 && c.imaginary == 0 ) {
			throw new IllegalArgumentException( "Attempted division by zero." );
		}
		double denominator = c.real * c.real + c.imaginary * c.imaginary;
		double realNumerator = real * c.real + imaginary * c.imaginary;
		double imaginaryNumerator = imaginary * c.real - real * c.imaginary;
		
		return new ComplexNumber( realNumerator / denominator, imaginaryNumerator / denominator );
	}
	/**
	 * Calculates complex number to the power of n and returns the result.
	 * @param n
	 * 		Exponent.
	 * @return
	 * 		New instance of ComplexNumber that represents the result.
	 */
	public ComplexNumber power( int n ) {
		if ( n < 0 ) {
			throw new IllegalArgumentException( "Power has to be greater than or equal to 0." );
		}
		double magnitude = Math.pow( getMagnitude(), n );
		double angle = n * getAngle();
		
		return ComplexNumber.fromMagnitudeAndAngle( magnitude, angle );
	}
	/**
	 * Calculates nth root of the complex number and returns the result.
	 * @param n
	 * 		Root.
	 * @return
	 * 		New instance of ComplexNumber that represents the result.
	 */
	public ComplexNumber[] root( int n ) {
		if ( n <= 0 ) {
			throw new IllegalArgumentException( "Root has to be greater than 0." );
		}
		ComplexNumber[] roots = new ComplexNumber[n];
		double magnitude = Math.pow( getMagnitude(), 1.0 / n );
		double oldAngle = getAngle();
		
		for ( int i = 0; i < n; i++ ) {
			double angle = ( oldAngle + 2 * i * Math.PI ) / n;
			roots[i] = ComplexNumber.fromMagnitudeAndAngle( magnitude, angle );
		}
		return roots;
	}
	/**
	 * Creates and returns string representation of the complex number.
	 */
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		if ( real != 0 ) {
			sb.append( real );
		}
		if( imaginary > 0 ) {
			sb.append( "+" ).append( imaginary ).append( "i" );
		} else if ( imaginary < 0 ){		
			sb.append( "-" ).append( -imaginary ).append( "i" );	
		}
		
		return sb.toString();
	}
}
