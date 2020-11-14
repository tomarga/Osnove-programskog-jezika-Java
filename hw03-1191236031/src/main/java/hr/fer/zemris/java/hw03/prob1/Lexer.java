package hr.fer.zemris.java.hw03.prob1;

/**
 * A simple code lexer.
 * 
 * @author Margarita Tolja
 *
 */
public class Lexer {
	
	// input text
	private char[] data;
	// current token
	private Token token;
	// index of first unprocessed symbol
	private int currentIndex; 
	//current state
	private LexerState state;
	
	/**
	 * Constructor. Gets the text to be converted to tokens.
	 */
	public Lexer(String text) { 
		data = text.toCharArray();
		token = null;
		currentIndex = 0;
		state = LexerState.BASIC;
	}
	
	/**
	 * Generates and returns the next token.
	 * @throws LexerException 
	 * 		if there is an error.
	 * @throws NullPointerException 
	 * 		if lexer's state is compromised.
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
		if ( currentIndex >= data.length ) {
			token = new Token( TokenType.EOF, null );
			return token;
		}
		
		switch( state ) {
			case BASIC:
				
				Token wordToken = nextWordBasic();
				if ( wordToken != null ) {
					return wordToken;
				}
				Token numberToken = nextNumberBasic();
				if ( numberToken != null ) {
					return numberToken;
				}				
				break;
			
			case EXTENDED:
				
				wordToken = nextWordExtended();
				if ( wordToken != null ) {
					return wordToken;
				}
				break;
				
			default:
				throw new NullPointerException( "Ilegall state" );
		}
			
		//symbol
		Character value = Character.valueOf( data[currentIndex++] );
		
		token = new Token( TokenType.SYMBOL, value );
		return token;
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
	 * Sets lexer's state to the one specified in the passed argument.
	 * @throws NullPointerException
	 * 		if null is passed instead of a new state.
	 * @param state
	 * 		New state.
	 */
	public void setState( LexerState state ) {
		if ( state == null ) {
			throw new NullPointerException( "Ilegall state" );
		}
		this.state = state;
	}
	
	/**
	 * Utility method. Skips blank spaces.
	 */
	private void skipBlanks() {		
		
		while( currentIndex < data.length ) {			
			char c = data[currentIndex];
			if( c==' ' || c=='\t' || c=='\r' || c=='\n' ) {
				currentIndex++;
				continue;
			}
			break;
		}
	}
	
	/**
	 * Utility method. Skips the escape sign and returns the value after it.
	 * @throws LexerException 
	 * 		if the symbol after escape sign is something 
	 * 		other than number or another escape sign.
	 * @return
	 * 		String with the value of sign after escape sign;
	 * 		Empty string if the current character was not escape sign. 		
	 */
	private String processEscape() {
		
		if ( data[currentIndex] == '\\' ) {
			currentIndex++;
			if ( currentIndex < data.length && ( Character.isDigit( data[currentIndex] ) 
					|| data[currentIndex] == '\\' ) ){
				return String.valueOf( data[currentIndex++] );
			}
			throw new LexerException( "Invalid use of escape symbol." );
		}
		//if there wasn't any escape sign
		return new String("");
	}
	
	/**
	 * Generates and returns the next word token 
	 * by the rules of basic lexer:
	 * Word is considered any array of characters or numbers/escape signs
	 * with previous escape sign.
	 * @throws LexerException 
	 * 		if there is an error.
	 * @return
	 * 		Next token;
	 * 		null if it is not a word.
	 */
	private Token nextWordBasic() {
			
		StringBuilder word = new StringBuilder();	
		int startIndex = currentIndex;
		
		while( currentIndex < data.length && ( Character.isLetter( data[currentIndex] ) 
				|| data[currentIndex] == '\\' ) ) {
			
			String afterEscape = processEscape();
			
			//if escape sign
			if ( !afterEscape.isEmpty() ) {	
				
				//first add accumulated part of the word
				int endIndex = currentIndex - afterEscape.length() - 1;
				String wordPart = new String( data, startIndex, endIndex - startIndex );
				word.append( wordPart );
				
				//then add the value after escape
				word.append( afterEscape );
				
				//update startIndex to the start of next part of the word
				startIndex = currentIndex;

			}
			else {
				currentIndex++;
			}
		}
		//if word is not empty
		if ( startIndex != currentIndex || !word.toString().isEmpty() ) {
			//add the last part
			String wordPart = new String( data, startIndex, currentIndex - startIndex );
			word.append( wordPart );

			String value = word.toString();
			
			token = new Token( TokenType.WORD, value );
			return token;
		}			
		return null;
	}
	
	/**
	 * Generates and returns the next number token 
	 * by the rules of basic lexer:
	 * Number is any array of digits that represent long number.
	 * @throws LexerException 
	 * 		if there is an error.
	 * @return
	 * 		Next token;
	 * 		null if it is not a number.
	 */
	private Token nextNumberBasic() {
		
		if ( Character.isDigit( data[currentIndex] ) ) {
			int startIndex = currentIndex;
			currentIndex++;
			
			while( currentIndex < data.length && Character.isDigit( data[currentIndex] ) ) {
				currentIndex++;
			}			
			String numberString = new String( data, startIndex, currentIndex - startIndex );
			
			try {
				Long value = Long.decode( numberString );
				token = new Token( TokenType.NUMBER, value );
				return token;
			}
			catch( NumberFormatException e ) {
				throw new LexerException( "Invalid long number." );
			}						
		}	
		return null;
	}
	
	/**
	 * Generates and returns the next word token 
	 * by the rules of extended lexer:
	 * Word is considered any array of characters, numbers or symbols other than #.
	 * @return
	 * 		Next token;
	 * 		null if it is not a word.
	 */
	private Token nextWordExtended() {
		
		int startIndex = currentIndex;
		while( currentIndex < data.length && data[currentIndex] != '#' && 
				data[currentIndex] != ' ' && data[currentIndex] != '\n' &&
				data[currentIndex] != '\t' && data[currentIndex] != '\r' ) {
			currentIndex++;
		}
		//if it is not a word
		if ( startIndex == currentIndex ) {
			return null;
		}
		String value = new String( data, startIndex, currentIndex - startIndex );
		
		token = new Token( TokenType.WORD, value );
		return token;
	}
	
}

