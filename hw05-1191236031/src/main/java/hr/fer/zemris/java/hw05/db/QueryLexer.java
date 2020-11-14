package hr.fer.zemris.java.hw05.db;

/**
 * A simple database query lexer.
 * 
 * @author Margarita Tolja
 *
 */
public class QueryLexer {
	
	// input text
	private String data;
	// current token
	private Token token;
	// index of first unprocessed symbol
	private int currentIndex; 
	
	/**
	 * Constructor. Gets the text to be converted to tokens.
	 */
	public QueryLexer( String text ) { 
		if ( text == null ) {
			throw new NullPointerException( "Query lexer received null input." );
		}
		data = text;
		token = null;
		currentIndex = 0;
	}
	
	/**
	 * Returns the last generated token. 
	 * @return
	 * 		The last token. 		
	 */
	public Token getToken() {
		return token;
	}
	
	/**
	 * Generates and returns the next token.
	 * @throws LexerException 
	 * 		if there is an error.
	 * @return
	 * 		Next token.
	 */
	public Token nextToken() { 
		
		//if eof has been already reached
		if ( token != null && token.getType() == TokenType.EOF ) {
			throw new LexerException( "No more tokens available." );
		}
		
		skipBlanks();
		
		//if eof is now reached
		if ( currentIndex >= data.length() ) {
			token = new Token( TokenType.EOF, null );
			return token;
		}
		
		//field name
		Token newToken = nextField();
		if ( newToken != null ) {
			token = newToken;
			return token;
		}
		
		//string literal
		if ( data.charAt( currentIndex ) == '"' ) {
			
			int startIndex = ++currentIndex;
			while( currentIndex < data.length() && data.charAt(currentIndex) != '"' ) {
				currentIndex++;
			}
			if ( currentIndex == data.length() ) {
				throw new LexerException( "String literal must be in the following format \"literal\"." );
			}
			String literal = data.substring( startIndex, currentIndex );
			currentIndex++;
			token = new Token( TokenType.LITERAL, literal );
			return token;		
		}
		
		//and
		if ( currentIndex + "and".length() <= data.length() 
				&& data.substring( currentIndex, currentIndex + 3 ).equalsIgnoreCase( "and" ) ){
			
			currentIndex += 3;
			token = new Token( TokenType.AND, null );
			return token;
		}
		
		//LIKE
		if ( currentIndex + "LIKE".length() <= data.length() 
				&& data.substring( currentIndex, currentIndex + 4 ).equalsIgnoreCase( "LIKE" ) ){
			
			currentIndex += 4;
			token = new Token( TokenType.OPERATOR, "LIKE" );
			return token;
		}
		
		//other operators
		char current = data.charAt( currentIndex );
		
		if ( "<>".indexOf( current ) > -1 ) {
			currentIndex++;
			char next = currentIndex < data.length() ? data.charAt( currentIndex ) : '-';
			if ( next != '-' && next == '=' ) {
				currentIndex++;
				String value = String.valueOf( current ) + String.valueOf( next );
				token = new Token( TokenType.OPERATOR, value );
				return token;
			}
			token = new Token( TokenType.OPERATOR, String.valueOf( current ) );
			return token;
		}
		
		if ( current == '!' ) {
			currentIndex++;
			char next = currentIndex < data.length() ? data.charAt( currentIndex ) : '-';
			if ( next != '-' && next == '=' ) {
				currentIndex++;
				token = new Token( TokenType.OPERATOR, "!=" );
				return token;
			}
		}
		
		if ( current == '=' ) {
			currentIndex++;
			token = new Token( TokenType.OPERATOR, "=" );
			return token;		
		}
		
		throw new LexerException( "Invalid query." );

	}

	
	/**
	 * Utility method. Skips blank spaces.
	 */
	private void skipBlanks() {		
		
		while( currentIndex < data.length() ) {			
			char c = data.charAt( currentIndex );
			if( c==' ' || c=='\t' || c=='\r' || c=='\n' ) {
				currentIndex++;
				continue;
			}
			break;
		}
	}
	
	/**
	 * Generates and returns next field token if possible.
	 * Valid field token values are 'firstName', 'lastName' and 'jmbag'.
	 * @return
	 * 		Next token or null if next token is not a field token.
	 */
	private Token nextField() {
		
		Token fieldToken = null;
		String[] fieldNames = new String[] { "firstName", "lastName", "jmbag" };
		
		for ( String fieldName : fieldNames ) {	 
			if ( currentIndex + fieldName.length() <= data.length() 
					&& fieldName.equals( data.substring( currentIndex, currentIndex + fieldName.length() ) ) ) {
				fieldToken = new Token( TokenType.FIELD, fieldName );
				currentIndex += fieldName.length();
				break;
			}
		}	
		return fieldToken;
	}
	
}

