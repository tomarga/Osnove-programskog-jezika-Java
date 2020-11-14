package hr.fer.zemris.java.hw06.shell;

/**
* A class represents the exception that occurs if reading or writing
* to shell fails.
* 
* @author Margarita Tolja
*
*/
public class ShellIOException extends RuntimeException {
		
	private static final long serialVersionUID = 10L;
	
	/**
	 * Creates an exception.
	 */
	public ShellIOException() {
	}
	/**
	 * Creates an exception with specific message.
	 * @param message
	 * 		Message for the user.
	 */
	public ShellIOException( String message ) {
		super( message );
	}
	/**
	 * Creates an exception with specific cause.
	 * @param cause
	 * 		Cause of exception.
	 */
	public ShellIOException( Throwable cause ) {
		super( cause );
	}
	/**
	 * Creates an exception with specific message and cause.
	 * @param message
	 * 		Message for the user.
	 * @param cause
	 * 		Cause of exception.
	 */
	public ShellIOException( String message, Throwable cause ) {
		super( message, cause );
	}
		
}
