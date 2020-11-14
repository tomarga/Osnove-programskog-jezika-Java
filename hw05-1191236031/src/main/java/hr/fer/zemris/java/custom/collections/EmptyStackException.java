package hr.fer.zemris.java.custom.collections;

/**
 * A class represents the exception that occurs if it comes
 * to the attempt of reaching for elements of an empty stack.
 * 
 * @author Margarita Tolja
 *
 */
public class EmptyStackException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * Creates an exception.
	 */
	public EmptyStackException() {
	}
	/**
	 * Creates an exception with specific message.
	 * @param message
	 * 		Message for the user.
	 */
	public EmptyStackException( String message ) {
		super( message );
	}
	/**
	 * Creates an exception with specific cause.
	 * @param cause
	 * 		Cause of exception.
	 */
	public EmptyStackException( Throwable cause ) {
		super( cause );
	}
	/**
	 * Creates an exception with specific message and cause.
	 * @param message
	 * 		Message for the user.
	 * @param cause
	 * 		Cause of exception.
	 */
	public EmptyStackException( String message, Throwable cause ) {
		super( message, cause );
	}
	
}
