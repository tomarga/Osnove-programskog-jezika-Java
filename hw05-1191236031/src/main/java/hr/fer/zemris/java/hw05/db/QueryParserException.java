package hr.fer.zemris.java.hw05.db;

/**
 * A class represents the exception that occurs when there is
 * an issue while parsing a query on student database emulator.
 * 
 * @author Margarita Tolja
 *
 */
public class QueryParserException extends RuntimeException {
			
	private static final long serialVersionUID = 3L;
		
	/**
	 * Creates a parser exception.
	 */
	public QueryParserException() {
	}
	
	/**
	 * Creates a parser exception with specific message.
	 * @param message
	 * 		Message for the user.
	 */
	public QueryParserException( String message ) {
		super( message );
	}
	/**
	 * Creates a parser exception with specific cause.
	 * @param cause
	 * 		Cause of exception.
	 */
	public QueryParserException( Throwable cause ) {
		super( cause );
	}
	
	/**
	 * Creates a parser exception with specific message and cause.
	 * @param message
	 * 		Message for the user.
	 * @param cause
	 * 		Cause of exception.
	 */
	public QueryParserException( String message, Throwable cause ) {
		super( message, cause );
	}

}
