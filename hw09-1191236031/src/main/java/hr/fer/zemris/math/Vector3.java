package hr.fer.zemris.math;

/**
 * A class that represents unchangeable vector made of three
 * real valued components (x, y, z).
 * 
 * @author Margarita Tolja
 *
 */
public class Vector3 {

	private final double x;
	private final double y;
	private final double z;
	
	/**
	 * Initialises vector's components to the ones passed as arguments.
	 * @param x 	Value of the first component.
	 * @param y		Value of the second component.
	 * @param z		Value of the third component.
	 */
	public Vector3( double x, double y, double z ) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	/**
	 * Calculates the second norm of a vector.
	 * @return		Root of the sum of the component's squares.
	 */
	public double norm() {
		return Math.sqrt( x*x + y*y + z*z );
	}
	
	/**
	 * Creates the normalised version of this vector.
	 * @return 		The normalised vector.
	 */
	public Vector3 normalized() {
		double norm = norm();
		return new Vector3( x / norm , y / norm, z / norm );
	}
	
	/**
	 * Calculates the sum of this vector and the one passed as an argument.
	 * @param other		Second operand.
	 * @return			The sum of vectors.
	 */
	public Vector3 add( Vector3 other ) {
		return new Vector3( x + other.x, y + other.y, z + other.z );
	}
	
	/**
	 * Subtracts the passed vector from this vector and return the result.
	 * @param other		Second operand.
	 * @return			The result vector.
	 */
	public Vector3 sub( Vector3 other ) {
		return new Vector3( x - other.x, y - other.y, z - other.z );
	}
	
	/**
	 * Calculates the scalar product of this vector and the one passed as an argument.
	 * @param other		Second operand.
	 * @return			The scalar product.
	 */
	public double dot( Vector3 other ) {
		return x*other.x + y*other.y + z*other.z;
	}
	
	/**
	 * Calculates the cross product of this vector and the one passed as an argument.
	 * @param other		Second operand.
	 * @return			The cross product of vectors.
	 */
	public Vector3 cross( Vector3 other ) {
		
		double newX = y * other.z - z * other.y;
		double newY = z * other.x - x * other.z;
		double newZ = x * other.y - y * other.x;
		
		return new Vector3( newX, newY, newZ );
	}
	
	/**
	 * Calculates the result of scaling this vector by the given factor.
	 * @param s		Scale factor.
	 * @return		The scaled vector.
	 */
	public Vector3 scale( double s ) {
		return new Vector3( s * x, s * y, s * z );
	}
	
	/**
	 * Calculates the cosines of the angle between this vector and the one given as an argument.
	 * @param other		Other vector.
	 * @return			cos(angle).
	 */
	public double cosAngle( Vector3 other ) {
		return dot( other ) / ( norm() * other.norm() );
	}
	
	/**
	 * Makes array out of vector's components.
	 * @return		The array {x, y, z}.
	 */	
	public double[] toArray() {
		return new double[] { x, y, z };
	}
	
	/**
	 * Returns the String representation of vector: "(x, y, z)".
	 */
	@Override
	public String toString() {
		
		StringBuilder sb = new StringBuilder( "(" );
		sb.append( String.format( "%.6f", x ) ).append( ", ");
		sb.append( String.format( "%.6f", y ) ).append( ", ");
		sb.append( String.format( "%.6f", z ) ).append( ")");
		
		return sb.toString();
	}

	/**
	 * First component getter.
	 * @return 		x value.
	 */
	public double getX() {
		return x;
	}

	/**
	 * Second component getter.
	 * @return 		y value.
	 */
	public double getY() {
		return y;
	}

	/**
	 * Third component getter.
	 * @return 		z value.
	 */
	public double getZ() {
		return z;
	}
	
}
