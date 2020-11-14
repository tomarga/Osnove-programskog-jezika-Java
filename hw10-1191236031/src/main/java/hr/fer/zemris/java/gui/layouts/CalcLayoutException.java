package hr.fer.zemris.java.gui.layouts;

/**
 * A class represents the exception that occurs if the layout
 * rules of calculator are not obeyed.
 * 
 * @author Margarita Tolja
 *
 */
public class CalcLayoutException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	/**
	 * Creates an exception.
	 */
	public CalcLayoutException() {
	}
	/**
	 * Creates an exception with specific message.
	 * @param message
	 * 		Message for the user.
	 */
	public CalcLayoutException( String message ) {
		super( message );
	}
	/**
	 * Creates an exception with specific cause.
	 * @param cause
	 * 		Cause of exception.
	 */
	public CalcLayoutException( Throwable cause ) {
		super( cause );
	}
	/**
	 * Creates an exception with specific message and cause.
	 * @param message
	 * 		Message for the user.
	 * @param cause
	 * 		Cause of exception.
	 */
	public CalcLayoutException( String message, Throwable cause ) {
		super( message, cause );
	}
	
}

