package hr.fer.zemris.math;

/**
 * A class that represents a two-dimensional vector with real coordinates.
 * 
 * @author Margarita Tolja
 *
 */
public class Vector2D {
	
	private double x;
	private double y;
	
	/**
	 * Creates a two-dimensional vector with passed coordinates' values.
	 * @param x
	 * 		Value of x-axis coordinate.
	 * @param y
	 * 		Value of y-axis coordinate.
	 */
	public Vector2D( double x, double y ) {	
		this.x = x;
		this.y = y;
	}
	
	/**
	 * X getter.
	 * @return
	 * 		Value of vector's x-axis coordinate.
	 */
	public double getX() {
		return x;
	}
	
	/**
	 * Y getter.
	 * @return
	 * 		Value of vector's y-axis coordinate.
	 */
	public double getY() {
		return y;
	}
	
	/**
	 * Translates the vector for a given offset.
	 * @param offset
	 * 		Two dimensional vector.
	 */
	public void translate( Vector2D offset ) {
		x += offset.x;
		y += offset.y;
	}
	
	/**
	 * Creates a new vector that is the result of translating this
	 * vector for a given offset.
	 * @param offset
	 * 		Two dimensional vector.
	 * @return
	 * 		New vector.
	 */
	public Vector2D translated( Vector2D offset ) {
		return new Vector2D( x + offset.x, y + offset.y );
	}
	
	/**
	 * Rotates the vector counter-clockwise by angle given in radians.
	 * @param angle
	 * 		Angle in radians.
	 */
	public void rotate( double angle ) {
		double rotatedX = (this.x * Math.cos( angle )) - (this.y * Math.sin( angle ));
	    double rotatedY = (this.x * Math.sin( angle )) + (this.y * Math.cos( angle ));
	    x = rotatedX;
	    y = rotatedY;
	}
	
	/**
	 * Creates a new vector that is the result of rotating this vector
	 * counter-clockwise by angle given in radians.
	 * @param angle
	 * 		Angle in radians.
	 * @return
	 * 		New vector.
	 */
	public Vector2D rotated( double angle ) {
		double rotatedX = (this.x * Math.cos( angle )) - (this.y * Math.sin( angle ));
	    double rotatedY = (this.x * Math.sin( angle )) + (this.y * Math.cos( angle ));
	    
	    return new Vector2D( rotatedX, rotatedY );
	}
	
	/**
	 * Scales the vector with given the factor.
	 * @param scaler
	 * 		Scale factor.
	 */
	public void scale( double scaler ) {
		x *= scaler;
		y *= scaler;
	}
	
	/**
	 * Creates a new vector that is the result of scaling this vector by a given factor.
	 * @param scaler
	 * 		Scale factor.
	 */
	public Vector2D scaled( double scaler ) {
		return new Vector2D( x * scaler, y * scaler );
	}
	
	/**
	 * Returns the copy of a vector.
	 * @return
	 * 		Vector's copy.
	 */
	public Vector2D copy() {
		return new Vector2D( x, y );
	}

}
