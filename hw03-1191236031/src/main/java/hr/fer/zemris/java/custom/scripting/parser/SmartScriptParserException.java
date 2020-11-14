package hr.fer.zemris.java.custom.scripting.parser;

/**
 * A class represents the exception that occurs when there is
 * an issue while parsing a script.
 * 
 * @author Margarita Tolja
 *
 */
public class SmartScriptParserException extends RuntimeException {
			
	private static final long serialVersionUID = 3L;
		
	/**
	 * Creates a parser exception.
	 */
	public SmartScriptParserException() {
	}
	
	/**
	 * Creates a parser exception with specific message.
	 * @param message
	 * 		Message for the user.
	 */
	public SmartScriptParserException( String message ) {
		super( message );
	}
	/**
	 * Creates a parser exception with specific cause.
	 * @param cause
	 * 		Cause of exception.
	 */
	public SmartScriptParserException( Throwable cause ) {
		super( cause );
	}
	
	/**
	 * Creates a parser exception with specific message and cause.
	 * @param message
	 * 		Message for the user.
	 * @param cause
	 * 		Cause of exception.
	 */
	public SmartScriptParserException( String message, Throwable cause ) {
		super( message, cause );
	}

}
