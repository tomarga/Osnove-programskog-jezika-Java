package hr.fer.zemris.java.custom.scripting.lexer;

/**
 * A smart script lexer.
 * 
 * @author Margarita Tolja
 *
 */
public class SmartScriptLexer {
		
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
	public SmartScriptLexer(String text) { 
		data = text.toCharArray();
		token = null;
		currentIndex = 0;
		state = LexerState.TEXT;
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
		
//		skipBlanks();
		
		//if eof is now reached
		if ( currentIndex >= data.length ) {
			token = new Token( TokenType.EOF, null );
			return token;
		}
		
		switch( state ) {
			case TEXT:

				//text
				Token textToken = nextText();
				if ( textToken != null ) {
					token = textToken;
					return token;
				}				
				//starttag
				if ( currentIndex + 1 < data.length && data[currentIndex] == '{' 
						&& data[currentIndex + 1] == '$' ) {
					currentIndex += 2;
					token = new Token( TokenType.STARTTAG, null );
					return token;
				}	
				
				break;
			
			case TAG:

				skipBlanks();
				
				//if eof is now reached
				if ( currentIndex >= data.length ) {
					token = new Token( TokenType.EOF, null );
					return token;
				}
				//string
				Token stringToken = nextString();
				if ( stringToken != null ) {
					token = stringToken;
					return token;
				}
				//name
				Token nameToken = nextName();
				if ( nameToken != null ) {
					token = nameToken;
					return token;
				}
				//numbers
				Token numberToken = nextNumber();
				if ( numberToken != null ) {
					token = numberToken;
					return token;
				}			
				//other symbols
				//endtag
				if ( currentIndex + 1 < data.length && data[currentIndex] == '$' 
						&& data[currentIndex + 1] == '}' ) {
					currentIndex += 2;
					token = new Token( TokenType.ENDTAG, null );
					return token;
				}	
				//at
				if ( data[currentIndex] == '@' ) {
					currentIndex++;
					token = new Token( TokenType.AT, null );
					return token;
				}
				//operators
				String validOperators = new String( "+-*//^" );
				if ( validOperators.indexOf( data[currentIndex] ) > -1 ) {
					token = new Token( TokenType.OPERATOR, String.valueOf( data[currentIndex++] ) );
					return token;
				}
				//equality symbol
				if ( data[currentIndex] == '=' ) {
					currentIndex++;
					token = new Token( TokenType.EQUALITY, null );
					return token;
				}
				//if none of above
				throw new LexerException( "Invalid expression." );			
				
			default:
				throw new NullPointerException( "Ilegall state" );
		}
	
		return null;
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
	 * 		if the symbol after escape sign is not contained in permitted String.
	 * @param permitted
	 * 		String containing characters permitted to go after the escape sign.
	 * @return
	 * 		String with the value of sign after escape sign;
	 * 		Empty string if the current character was not escape sign. 		
	 */
	private String processEscape( String permitted ) {
		
		if ( data[currentIndex] == '\\' ) {
			currentIndex++;
			if ( currentIndex < data.length && ( permitted.indexOf( data[currentIndex] ) > -1 ) ){
				if ( data[currentIndex] == 'n' ) {
					currentIndex++;
					return Character.toString( (char) 10 );
				}
				if ( data[currentIndex] == 'r' ) {
					currentIndex++;
					return Character.toString( (char) 13 );
				}
				if ( data[currentIndex] == 't' ) {
					currentIndex++;
					return Character.toString( (char) 9 );
				}
				return String.valueOf( data[currentIndex++] );
			}
			throw new LexerException( "Invalid use of escape symbol." );
		}
		//if there wasn't any escape sign
		return new String("");
		
	}
	
	/**
	 * Generates and returns the next text token 
	 * by the rules of smart lexer in text state:
	 * Text is considered any array of symbols.
	 * After escape sign, only another escape sign or open curly bracket is permitted.
	 * @throws LexerException 
	 * 		if there is an error.
	 * @return
	 * 		Next token;
	 * 		null if it is not text.
	 */
	private Token nextText() {
			
		StringBuilder word = new StringBuilder();	
		int startIndex = currentIndex;
		
		String permittedEscape = new String( "\\{" );
		
		while( currentIndex < data.length ) {
			
			//if starttag
			if ( currentIndex + 1 < data.length && data[currentIndex] == '{' 
					&& data[currentIndex+1] == '$') {
				break;				
			}
			
			String afterEscape = processEscape( permittedEscape );
			
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
			
			token = new Token( TokenType.TEXT, value );
			return token;
		}			
		return null;
	}
	
	/**
	 * Generates and returns the next string token 
	 * by the rules of smart lexer in tag state:
	 * String is considered any array of symbols underneath "".
	 * Characters valid after escape sign: /, ", n, r and t.
	 * @throws LexerException 
	 * 		if there is an error.
	 * @return
	 * 		Next token;
	 * 		null if it is not a string.
	 */
	private Token nextString() {
		
		if ( data[currentIndex] == '"') {
			
			currentIndex++;		
			StringBuilder string = new StringBuilder();	
			int startIndex = currentIndex;
			
			String permittedEscape = new String( "\\\"ntr" );
			
			while( currentIndex < data.length && data[currentIndex] != '"' ) {
								
				String afterEscape = processEscape( permittedEscape );
				
				//if escape sign
				if ( !afterEscape.isEmpty() ) {	
					
					//first add accumulated part of the word
					int endIndex = currentIndex - afterEscape.length() - 1;
					String wordPart = new String( data, startIndex, endIndex - startIndex );
					string.append( wordPart );
					
					//then add the value after escape
					string.append( afterEscape );
					
					//update startIndex to the start of next part of the word
					startIndex = currentIndex;
	
				}
				else {
					currentIndex++;
				}
			}
			
			//if string ended correctly
			if ( currentIndex < data.length && data[currentIndex] == '"' ) {
				//add the last part
				String wordPart = new String( data, startIndex, currentIndex - startIndex );
				currentIndex++;
				
				string.append( wordPart );
	
				String value = string.toString();
				
				token = new Token( TokenType.STRING, value );
				return token;
			}
			else {
				throw new LexerException( "Iregular string expression" );
			}
		}
		return null;
	}
	
	/**
	 * Generates and returns the next name token 
	 * by the rules of smart lexer in tag state:
	 * A name is considered any array whose first symbol is a letter
	 * followed by zero or more letters, digits or underscores.
	 * @return
	 * 		Next token;
	 * 		null if it is not a name.
	 */
	private Token nextName() {
		
		if ( Character.isLetter( data[currentIndex] ) ) {
			int startIndex = currentIndex++;	
			
			while( currentIndex < data.length && ( Character.isLetter( data[currentIndex] )  ||
					Character.isDigit( data[currentIndex] ) || data[currentIndex] == '_' ) ) {
				currentIndex++;
			}
			
			String value = new String( data, startIndex, currentIndex - startIndex );
			
			token = new Token( TokenType.NAME, value );
			return token;
			
		}
		return null;
	}
	
	/**
	 * Generates and returns the next number( integer or double ) token 
	 * by the rules of smart lexer in tag state.
	 * @throws LexerException
	 * 		if the number is in invalid form.
	 * @return
	 * 		Next token;
	 * 		null if it is not a number.
	 */
	private Token nextNumber() {
		
		int current = currentIndex;
		
		//negative
		if ( data[currentIndex] == '-' ) {
			current++;
		}
		
		if ( Character.isDigit( data[current] ) ) {		
			int startIndex = currentIndex;
			currentIndex = current;
			currentIndex++;
			
			while( currentIndex < data.length && Character.isDigit( data[currentIndex] ) ) {
				currentIndex++;
			}
			//decimal point -> double
			if ( data[currentIndex] == '.' ) {
				currentIndex++;
				while( currentIndex < data.length && Character.isDigit( data[currentIndex] ) ) {
					currentIndex++;
				}
				
				String doubleString = new String( data, startIndex, currentIndex - startIndex );
				
				try {
					Double value = Double.valueOf( doubleString );
					token = new Token( TokenType.DOUBLE, value );
					return token;
				}
				catch( NumberFormatException e ) {
					throw new LexerException( "Invalid double number." );
				}
			}
			
			//no decimal point -> integer
			String integerString = new String( data, startIndex, currentIndex - startIndex );
			
			try {
				Integer value = Integer.valueOf( integerString );
				token = new Token( TokenType.INTEGER, value );
				return token;
			}
			catch( NumberFormatException e ) {
				throw new LexerException( "Invalid integer number." );
			}						
		}	
		return null;
	}
		
}
