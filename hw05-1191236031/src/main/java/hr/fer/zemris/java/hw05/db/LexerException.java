package hr.fer.zemris.java.hw05.db;

/**
 * A class represents the exception that occurs when there is
 * an issue with turning queries to appropriate tokens.
 * 
 * @author Margarita Tolja
 *
 */
public class LexerException extends RuntimeException {
	
	private static final long serialVersionUID = 5L;
		
	/**
	 * Creates a lexer exception.
	 */
	public LexerException() {
	}
	
	/**
	 * Creates a lexer exception with specific message.
	 * @param message
	 * 		Message for the user.
	 */
	public LexerException( String message ) {
		super( message );
	}
	/**
	 * Creates a lexer exception with specific cause.
	 * @param cause
	 * 		Cause of exception.
	 */
	public LexerException( Throwable cause ) {
		super( cause );
	}
	
	/**
	 * Creates a lexer exception with specific message and cause.
	 * @param message
	 * 		Message for the user.
	 * @param cause
	 * 		Cause of exception.
	 */
	public LexerException( String message, Throwable cause ) {
		super( message, cause );
	}

}
