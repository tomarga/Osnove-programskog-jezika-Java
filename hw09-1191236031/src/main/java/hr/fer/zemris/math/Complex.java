package hr.fer.zemris.math;

import java.util.ArrayList;
import java.util.List;

/**
 * A class that represents an unchangeable complex number.
 * 
 * @author Margarita Tolja
 *
 */
public class Complex {
	
	private final double real; 
	private final double imaginary; 
	
	public static final Complex ZERO = new Complex(0,0);
	public static final Complex ONE = new Complex(1,0);
	public static final Complex ONE_NEG = new Complex(-1,0);
	public static final Complex IM = new Complex(0,1);
	public static final Complex IM_NEG = new Complex(0,-1);

	/**
	 * Default constructor.
	 * Creates the complex number with both real and imaginary part set to zero.
	 */
	public Complex() {
		real = 0;
		imaginary = 0;
	}
	
	/**
	 * Initialises the complex number.
	 * @param x		Value of the real part.
	 * @param y		Value of the imaginary part.
	 */
	public Complex( double re, double im ) {
		real = re;
		imaginary = im;
	}
	
	/**
	 * Calculates the module of the complex number.
	 * @return		The module of a number.
	 */
	public double module() {
		return Math.sqrt( real*real + imaginary*imaginary );
	}
	
	/**
	 * Multiplicates this number by the one passed as an argument.
	 * @param c		Second operand.
	 * @return		this * c.
	 */
	public Complex multiply( Complex c ) {
		
		double realPart = real * c.real - imaginary * c.imaginary;
		double imaginaryPart = real * c.imaginary + imaginary * c.real;
		return new Complex( realPart, imaginaryPart );
	}
	
	/**
	 * Divides this number by the by the one passed as an argument.
	 * @param c		Second operand.
	 * @return		this / c.
	 */
	public Complex divide( Complex c ) {
		
		if ( c.real == 0 && c.imaginary == 0 ) {
			throw new IllegalArgumentException( "Attempted division by zero." );
		}
		double denominator = c.real * c.real + c.imaginary * c.imaginary;
		double realNumerator = real * c.real + imaginary * c.imaginary;
		double imaginaryNumerator = imaginary * c.real - real * c.imaginary;
		
		return new Complex( realNumerator / denominator, imaginaryNumerator / denominator );
	}
	
	/**
	 * Adds the passed number to this one and returns the result.
	 * @param c		Second operand.
	 * @return		this + c.
	 */
	public Complex add( Complex c ) {
		return new Complex( real + c.real, imaginary + c.imaginary );
	}
	
	/**
	 * Subtracts the passed number from this one.
	 * @param c		Second operand.
	 * @return		this - c.
	 */
	public Complex sub( Complex c ) {
		return new Complex( real - c.real, imaginary - c.imaginary );
	}
	
	/**
	 * Gets the negation of this number.
	 * @return		-this.
	 */
	public Complex negate() {
		return new Complex( -real, -imaginary );
	}
	
	/**
	 * Calculates this vector to the power of the passed argument.
	 * @param n		Exponent.
	 * @return		this ^ n.
	 * @throws IllegalArgumentException		If the exponent is less than zero.
	 */
	public Complex power( int n ) {
		
		if ( n < 0 ) {
			throw new IllegalArgumentException( "Power has to be greater than or equal to 0." );
		}
		double magnitude = Math.pow( getMagnitude(), n );
		double angle = n * getAngle();
		
		return Complex.fromMagnitudeAndAngle( magnitude, angle );
	}
	
	/**
	 * Calculates nth root of the complex number and returns the result.
	 * @param n		Root.
	 * @return		List of n-th roots.
	 * @throws IllegalArgumentException 	If the argument is less than or equal to zero.
	 */
	public List<Complex> root( int n ) {
		
		if ( n <= 0 ) {
			throw new IllegalArgumentException( "Root has to be greater than 0." );
		}
		List<Complex> roots = new ArrayList<>(n);
		double magnitude = Math.pow( getMagnitude(), 1.0 / n );
		double oldAngle = getAngle();
		
		for ( int i = 0; i < n; i++ ) {
			double angle = ( oldAngle + 2 * i * Math.PI ) / n;
			roots.add(i, Complex.fromMagnitudeAndAngle( magnitude, angle ) );
		}
		return roots;
	}
	
	/**
	 * Creates and returns string representation of the complex number "Re(z) + Im(z)i".
	 */
	@Override
	public String toString() {
		
		StringBuilder sb = new StringBuilder();
		
		sb.append( real );
		
		if( imaginary >= 0 ) {
			sb.append( "+" ).append( imaginary ).append( "i" );
		} else if ( imaginary < 0 ){		
			sb.append( "-" ).append( -imaginary ).append( "i" );	
		}
		
		return sb.toString();
	}
	
	/**
	 * Gets magnitude of complex number.
	 * @return		Magnitude(r) of complex number.
	 */
	private double getMagnitude() {
		return Math.sqrt( real * real + imaginary * imaginary );
	}
	
	/**
	 * Gets angle of complex number in polar coordinates.
	 * @return		Angle in radians.
	 */
	private double getAngle() {
		return imaginary >= 0 ? Math.atan2(imaginary, real) : 2 * Math.PI + Math.atan2(imaginary, real);
	}
	
	/**
	 * Creates a representation of a complex number with given magnitude
	 * and angle. Angle is supposed to be given in radians.
	 * @param magnitude		Magnitude of complex number.
	 * @param angle			Angle of complex number in polar form.
	 * @return		 		Appropriate complex number.
	 */
	private static Complex fromMagnitudeAndAngle( double magnitude, double angle ) {
		//assuming that angle is in radians
		return new Complex( magnitude * Math.cos( angle ), magnitude * Math.sin( angle ) );
	}
	
	/**
	 * Calculates the distance of this number and the number passed as an argument.
	 * @param other		Other complex number.
	 * @return			The distance ot two complex numbers.
	 */
	public double distance( Complex other ) {
		double reals = ( real - other.real ) * ( real - other.real );
		double imaginaries = ( imaginary - other.imaginary ) * ( imaginary - other.imaginary );
		double distance = Math.sqrt( reals + imaginaries );
		
		return distance;
	}
	
}
