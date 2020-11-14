package hr.fer.zemris.java.custom.scripting.lexer;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class SmartScriptLexerTest {
	
//TEXT STATE
	
//	@Disabled
	@Test
	public void testNotNull() {
		SmartScriptLexer SmartScriptLexer = new SmartScriptLexer("");
		
		assertNotNull(SmartScriptLexer.nextToken(), "Token was expected but null was returned.");
	}

//	@Disabled
	@Test
	public void testNullInput() {
		// must throw!
		assertThrows(NullPointerException.class, () -> new SmartScriptLexer(null));
	}

//	@Disabled
	@Test
	public void testEmpty() {
		SmartScriptLexer SmartScriptLexer = new SmartScriptLexer("");
		
		assertEquals(TokenType.EOF, SmartScriptLexer.nextToken().getType(), "Empty input must generate only EOF token.");
	}

//	@Disabled
	@Test
	public void testGetReturnsLastNext() {
		// Calling getToken once or several times after calling nextToken must return each time what nextToken returned...
		SmartScriptLexer SmartScriptLexer = new SmartScriptLexer("");
		
		Token token = SmartScriptLexer.nextToken();
		assertEquals(token, SmartScriptLexer.getToken(), "getToken returned different token than nextToken.");
		assertEquals(token, SmartScriptLexer.getToken(), "getToken returned different token than nextToken.");
	}

//	@Disabled
	@Test
	public void testRadAfterEOF() {
		SmartScriptLexer SmartScriptLexer = new SmartScriptLexer("");

		// will obtain EOF
		SmartScriptLexer.nextToken();
		// will throw!
		assertThrows(LexerException.class, () -> SmartScriptLexer.nextToken());
	}
	
//	@Disabled
	@Test
	public void testBlankContent() {
		// When input is only of spaces, tabs, newlines, etc...
		SmartScriptLexer SmartScriptLexer = new SmartScriptLexer("   \r\n\t    ");
		
		assertEquals( TokenType.TEXT, SmartScriptLexer.nextToken().getType() );
		assertEquals( "   \r\n\t    ", SmartScriptLexer.getToken().getValue() );
		assertEquals( TokenType.EOF, SmartScriptLexer.nextToken().getType() );
	}
	
//	@Disabled
	@Test
	public void testRandomContent() {
		SmartScriptLexer SmartScriptLexer = new SmartScriptLexer("   ABC abc .,- \r\n\t    ");
		
		assertEquals( TokenType.TEXT, SmartScriptLexer.nextToken().getType() );
		assertEquals( "   ABC abc .,- \r\n\t    ", SmartScriptLexer.getToken().getValue() );
		assertEquals( TokenType.EOF, SmartScriptLexer.nextToken().getType() );
	}
	
//	@Disabled
	@Test
	public void testEscapeEscape() {
		SmartScriptLexer SmartScriptLexer = new SmartScriptLexer("\\\\");
		
		assertEquals( TokenType.TEXT, SmartScriptLexer.nextToken().getType() );
		assertEquals( "\\", SmartScriptLexer.getToken().getValue() );
		assertEquals( TokenType.EOF, SmartScriptLexer.nextToken().getType() );
	}
	
//	@Disabled
	@Test
	public void testEscapeCurly() {
		SmartScriptLexer SmartScriptLexer = new SmartScriptLexer("\\{");
		
		assertEquals( TokenType.TEXT, SmartScriptLexer.nextToken().getType() );
		assertEquals( "{", SmartScriptLexer.getToken().getValue() );
		assertEquals( TokenType.EOF, SmartScriptLexer.nextToken().getType() );
	}
	
//	@Disabled
	@Test
	public void testMultipleEscape() {
		SmartScriptLexer SmartScriptLexer = new SmartScriptLexer("  mm \\\\\\{\\\\ ");
		assertEquals( TokenType.TEXT, SmartScriptLexer.nextToken().getType() );
		assertEquals( "  mm \\{\\ ", SmartScriptLexer.getToken().getValue() );
		assertEquals( TokenType.EOF, SmartScriptLexer.nextToken().getType() );
	}
	
//	@Disabled
	@Test
	public void testEscapeOther() {
		SmartScriptLexer SmartScriptLexer = new SmartScriptLexer("  \\m ddd ");
		
		assertThrows(LexerException.class, () -> SmartScriptLexer.nextToken());
	}
	
//	@Disabled
	@Test
	public void testStartTag() {
		SmartScriptLexer SmartScriptLexer = new SmartScriptLexer(" abs ABS .,- {$ mg ") ;
		
		assertEquals( TokenType.TEXT, SmartScriptLexer.nextToken().getType() );
		assertEquals( " abs ABS .,- ", SmartScriptLexer.getToken().getValue() );
		assertEquals( TokenType.STARTTAG, SmartScriptLexer.nextToken().getType() );

	}
	
//TAG
	
	//string
	
	@Test
	public void String() {
		SmartScriptLexer SmartScriptLexer = new SmartScriptLexer(" \"abc\" ") ;
		SmartScriptLexer.setState( LexerState.TAG );
		
		assertEquals( TokenType.STRING, SmartScriptLexer.nextToken().getType() );
		assertEquals( "abc", SmartScriptLexer.getToken().getValue() );

	}
	
	@Test
	public void StringEscapeEscape() {
		SmartScriptLexer SmartScriptLexer = new SmartScriptLexer(" \"\\\\abc\" ") ;
		SmartScriptLexer.setState( LexerState.TAG );
		
		assertEquals( TokenType.STRING, SmartScriptLexer.nextToken().getType() );
		assertEquals( "\\abc", SmartScriptLexer.getToken().getValue() );

	}
	
	@Test
	public void StringEscapeApostrophe() {
		SmartScriptLexer SmartScriptLexer = new SmartScriptLexer(" \"\\\"abc\" ") ;
		SmartScriptLexer.setState( LexerState.TAG );
		
		assertEquals( TokenType.STRING, SmartScriptLexer.nextToken().getType() );
		assertEquals( "\"abc", SmartScriptLexer.getToken().getValue() );

	}
	
	@Test
	public void StringEscapeMultiple() {
		SmartScriptLexer SmartScriptLexer = new SmartScriptLexer(" \"  \\\\\\\"abc \\\\\" ") ;
		SmartScriptLexer.setState( LexerState.TAG );
		
		assertEquals( TokenType.STRING, SmartScriptLexer.nextToken().getType() );
		assertEquals( "  \\\"abc \\", SmartScriptLexer.getToken().getValue() );

	}
	
	@Test
	public void StringEscapeBlanks() {
		SmartScriptLexer SmartScriptLexer = new SmartScriptLexer(" \"  \nabc \r\t\" ") ;
		SmartScriptLexer.setState( LexerState.TAG );
		
		assertEquals( TokenType.STRING, SmartScriptLexer.nextToken().getType() );
		assertEquals( "  \nabc \r\t", SmartScriptLexer.getToken().getValue() );

	}
	
	@Test
	public void StringEscapeOther() {
		SmartScriptLexer SmartScriptLexer = new SmartScriptLexer(" \"  \\abc \" ") ;
		SmartScriptLexer.setState( LexerState.TAG );		
		assertThrows(LexerException.class, () -> SmartScriptLexer.nextToken());
	}
	
	@Test
	public void StringNmber() {
		SmartScriptLexer SmartScriptLexer = new SmartScriptLexer("\"1.111\"") ;
		SmartScriptLexer.setState( LexerState.TAG );
		
		assertEquals( TokenType.STRING, SmartScriptLexer.nextToken().getType() );
		assertEquals( "1.111", SmartScriptLexer.getToken().getValue() );

	}
	
	//name
	@Test
	public void Name() {
		SmartScriptLexer SmartScriptLexer = new SmartScriptLexer(" abc ") ;
		SmartScriptLexer.setState( LexerState.TAG );
		
		assertEquals( TokenType.NAME, SmartScriptLexer.nextToken().getType() );
		assertEquals( "abc", SmartScriptLexer.getToken().getValue() );
	}
	
	@Test
	public void NameUnderscoreNumber() {
		SmartScriptLexer SmartScriptLexer = new SmartScriptLexer(" ab__21c ") ;
		SmartScriptLexer.setState( LexerState.TAG );
		
		assertEquals( TokenType.NAME, SmartScriptLexer.nextToken().getType() );
		assertEquals( "ab__21c", SmartScriptLexer.getToken().getValue() );
	}
		
	@Test
//	@Disabled
	public void NameFirstNotLetter() {
		SmartScriptLexer SmartScriptLexer = new SmartScriptLexer(" _ab__21c ") ;
		SmartScriptLexer.setState( LexerState.TAG );
		
		assertThrows(LexerException.class, () -> SmartScriptLexer.nextToken());
	}
	
	//numbers
	@Test
	public void Integer() {		
		SmartScriptLexer SmartScriptLexer = new SmartScriptLexer(" 123 ") ;
		SmartScriptLexer.setState( LexerState.TAG );
		
		assertEquals( TokenType.INTEGER, SmartScriptLexer.nextToken().getType() );
		assertEquals( 123, SmartScriptLexer.getToken().getValue() );	
	}
	
	@Test
	public void NegativeInteger() {		
		SmartScriptLexer SmartScriptLexer = new SmartScriptLexer(" -123 ") ;
		SmartScriptLexer.setState( LexerState.TAG );
		
		assertEquals( TokenType.INTEGER, SmartScriptLexer.nextToken().getType() );
		assertEquals( -123, SmartScriptLexer.getToken().getValue() );
		
	}
	
	@Test
	public void Double() {
		SmartScriptLexer SmartScriptLexer = new SmartScriptLexer(" 123.123 ") ;
		SmartScriptLexer.setState( LexerState.TAG );
		
		assertEquals( TokenType.DOUBLE, SmartScriptLexer.nextToken().getType() );
		assertEquals( 123.123, SmartScriptLexer.getToken().getValue() );
	}
	
	@Test
	public void NegativeDouble() {
		SmartScriptLexer SmartScriptLexer = new SmartScriptLexer(" -123.123 ") ;
		SmartScriptLexer.setState( LexerState.TAG );
		
		assertEquals( TokenType.DOUBLE, SmartScriptLexer.nextToken().getType() );
		assertEquals( -123.123, SmartScriptLexer.getToken().getValue() );
	}
	
	//operator
	
	@Test
	public void Operator() {
		SmartScriptLexer SmartScriptLexer = new SmartScriptLexer(" + ") ;
		SmartScriptLexer.setState( LexerState.TAG );
		
		assertEquals( TokenType.OPERATOR, SmartScriptLexer.nextToken().getType() );
		assertEquals( "+", SmartScriptLexer.getToken().getValue() );
	}

	//at
	@Test
	public void At() {
		SmartScriptLexer SmartScriptLexer = new SmartScriptLexer(" @ ") ;
		SmartScriptLexer.setState( LexerState.TAG );
		
		assertEquals( TokenType.AT, SmartScriptLexer.nextToken().getType() );
	}
	
	//equality
	@Test
	public void Equality() {
		SmartScriptLexer SmartScriptLexer = new SmartScriptLexer(" = ") ;
		SmartScriptLexer.setState( LexerState.TAG );
		
		assertEquals( TokenType.EQUALITY, SmartScriptLexer.nextToken().getType() );
	}
	
	//tag expressions
	@Test
	public void Expression1() {
		SmartScriptLexer SmartScriptLexer = new SmartScriptLexer(" = i i * @sin\n \"0.000\" @decfmt $}");
		SmartScriptLexer.setState( LexerState.TAG );
		
		Token correctData[] = {
				new Token(TokenType.EQUALITY, null ),
				new Token(TokenType.NAME, "i"),
				new Token(TokenType.NAME, "i"),
				new Token(TokenType.OPERATOR, "*"),
				new Token(TokenType.AT, null ),
				new Token(TokenType.NAME, "sin"),
				new Token(TokenType.STRING, "0.000"),
				new Token(TokenType.AT, null ),
				new Token(TokenType.NAME, "decfmt"),
				new Token(TokenType.ENDTAG, null ),
				new Token(TokenType.EOF, null)
			};
		
		checkTokenStream(SmartScriptLexer, correctData);
	}
	
//BOTH STATES
	
	@Test
	public void Both1() {
		SmartScriptLexer SmartScriptLexer = new SmartScriptLexer(" tra la {$\n" + 
				"FOR\n" + 
				"sco_re\n" + 
				"\"- 1 \" 10 \" 1 \" $}");
		
		
		Token correctData[] = new Token[2];
		
		int i = 0;
		correctData[i++] = new Token( TokenType.TEXT, " tra la ");
		correctData[i++] = new Token( TokenType.STARTTAG, null );
		checkTokenStream(SmartScriptLexer, correctData);
		SmartScriptLexer.setState( LexerState.TAG );
		i = 0;
		correctData = new Token[7];
		correctData[i++] = new Token(TokenType.NAME, "FOR" );
		correctData[i++] = new Token(TokenType.NAME, "sco_re");
		correctData[i++] = new Token(TokenType.STRING, "- 1 ");
		correctData[i++] = new Token(TokenType.INTEGER, 10);
		correctData[i++] = new Token(TokenType.STRING, " 1 " );
		correctData[i++] = new Token(TokenType.ENDTAG, null );
		correctData[i++] = new Token(TokenType.EOF, null);
			
		
		checkTokenStream(SmartScriptLexer, correctData);
	}
	
	@Test
	public void Both2() {
		SmartScriptLexer SmartScriptLexer = new SmartScriptLexer(" tra la {$\n" + 
				"FOR\n" + 
				"sco_re\n" + 
				"\"- 1 \" 10 \" 1 \" $}");
		
		
		Token correctData[] = new Token[2];
		
		int i = 0;
		correctData[i++] = new Token( TokenType.TEXT, " tra la ");
		correctData[i++] = new Token( TokenType.STARTTAG, null );
		checkTokenStream(SmartScriptLexer, correctData);
		SmartScriptLexer.setState( LexerState.TAG );
		i = 0;
		correctData = new Token[7];
		correctData[i++] = new Token(TokenType.NAME, "FOR" );
		correctData[i++] = new Token(TokenType.NAME, "sco_re");
		correctData[i++] = new Token(TokenType.STRING, "- 1 ");
		correctData[i++] = new Token(TokenType.INTEGER, 10);
		correctData[i++] = new Token(TokenType.STRING, " 1 " );
		correctData[i++] = new Token(TokenType.ENDTAG, null );
		correctData[i++] = new Token(TokenType.EOF, null);
			
		
		checkTokenStream(SmartScriptLexer, correctData);
	}
	
	@Test
	public void Both3() {
		SmartScriptLexer SmartScriptLexer = new SmartScriptLexer("{$ FOR ye_2ar @sin 10 $} re\\\\st{$END$}");
			
		Token correctData[] = new Token[1];
		
		int i = 0;
		correctData[i++] = new Token( TokenType.STARTTAG, null );
		checkTokenStream(SmartScriptLexer, correctData);
		
		SmartScriptLexer.setState( LexerState.TAG );
		i = 0;
		correctData = new Token[6];
		correctData[i++] = new Token(TokenType.NAME, "FOR" );
		correctData[i++] = new Token(TokenType.NAME, "ye_2ar");
		correctData[i++] = new Token(TokenType.AT, null );
		correctData[i++] = new Token(TokenType.NAME, "sin");
		correctData[i++] = new Token(TokenType.INTEGER, 10 );
		correctData[i++] = new Token(TokenType.ENDTAG, null );
		checkTokenStream(SmartScriptLexer, correctData);
		
		SmartScriptLexer.setState( LexerState.TEXT );
		correctData = new Token[2];
		i = 0;
		correctData[i++] = new Token(TokenType.TEXT, " re\\st" );
		correctData[i++] = new Token( TokenType.STARTTAG, null );
		checkTokenStream(SmartScriptLexer, correctData);
		
		SmartScriptLexer.setState( LexerState.TAG );
		correctData = new Token[3];
		i = 0;
		correctData[i++] = new Token(TokenType.NAME, "END" );
		correctData[i++] = new Token(TokenType.ENDTAG, null );
		correctData[i++] = new Token(TokenType.EOF, null);
		checkTokenStream(SmartScriptLexer, correctData);
	}
	
	
	// Helper method for checking if SmartScriptLexer generates the same stream of tokens
	// as the given stream.
	private void checkTokenStream(SmartScriptLexer SmartScriptLexer, Token[] correctData) {
		int counter = 0;
		for(Token expected : correctData) {
			Token actual = SmartScriptLexer.nextToken();
			String msg = "Checked token "+counter + ":";
			
			assertEquals(expected.getType(), actual.getType(), msg);
			assertEquals(expected.getValue(), actual.getValue(), msg);
//			System.out.println(msg);
			counter++;
		}
	}

}
