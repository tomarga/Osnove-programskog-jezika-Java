package hr.fer.zemris.java.hw05.db;

/**
 * Class that represents tokens provided by query lexer.
 * 
 * @author Margarita Tolja
 *
 */
public class Token {
	
	private TokenType type;
	private String value;
	
	/**
	 * Creates token with specific type and value.
	 * @param type
	 * 		Token type( field, literal, operator, and or eof )
	 * @param value
	 * 		Exact content of a token.
	 */
	public Token(TokenType type, String value) {
		this.type = type;
		this.value = value;			
	}
	
	/**
	 * Value getter.
	 * @return
	 * 		The token value.
	 */
	public String getValue() {
		return value;
	}
	
	/**
	 * TokenType getter.
	 * @return
	 * 		The type of a token.
	 */
	public TokenType getType() {
		return type;
	}		
}

