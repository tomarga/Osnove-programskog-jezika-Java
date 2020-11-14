package hr.fer.zemris.java.custom.scripting.lexer;

/**
 * Class that represents tokens provided by lexer.
 * 
 * @author Margarita Tolja
 *
 */
public class Token {
	
	private TokenType type;
	private Object value;
	
	/**
	 * Creates token with specific type and value.
	 * @param type
	 * 		Token type( word, number, symbol or eof )
	 * @param value
	 * 		Exact content of a token.
	 */
	public Token(TokenType type, Object value) {
		this.type = type;
		this.value = value;			
	}
	
	/**
	 * Value getter.
	 * @return
	 * 		The token value.
	 */
	public Object getValue() {
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

