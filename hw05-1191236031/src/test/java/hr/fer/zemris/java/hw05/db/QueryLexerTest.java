package hr.fer.zemris.java.hw05.db;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;


public class QueryLexerTest {
	
	//basics
	@Test
	public void testNotNull() {
		QueryLexer QueryLexer = new QueryLexer("");
		
		assertNotNull(QueryLexer.nextToken() );
	}
	@Test
	public void testNullInput() {
		assertThrows(NullPointerException.class, () -> new QueryLexer(null));
	}
	@Test
	public void testEmpty() {
		QueryLexer QueryLexer = new QueryLexer("");
		
		assertEquals(TokenType.EOF, QueryLexer.nextToken().getType());
	}
	@Test
	public void testGetReturnsLastNext() {
		QueryLexer QueryLexer = new QueryLexer("");
		
		Token token = QueryLexer.nextToken();
		assertEquals(token, QueryLexer.getToken() );
		assertEquals(token, QueryLexer.getToken() );
	}
	@Test
	public void testRadAfterEOF() {
		QueryLexer QueryLexer = new QueryLexer("");
		QueryLexer.nextToken();
		assertThrows(LexerException.class, () -> QueryLexer.nextToken());
	}
	@Test
	public void testBlankContent() {
		QueryLexer QueryLexer = new QueryLexer("   \r\n\t    ");

		assertEquals( TokenType.EOF, QueryLexer.nextToken().getType() );
	}
	
	
	//field 
	@Test
	public void fieldName() {
		QueryLexer QueryLexer = new QueryLexer("   firstName lastName 		jmbag");
		Token correctData[] = {
				new Token(TokenType.FIELD, "firstName" ),
				new Token(TokenType.FIELD, "lastName" ),
				new Token(TokenType.FIELD, "jmbag" ),
				new Token(TokenType.EOF, null )
		};
		checkTokenStream( QueryLexer, correctData);
	}
	@Test
	public void fieldNameWrong() {
		QueryLexer QueryLexer = new QueryLexer("   firstNam");
		assertThrows(LexerException.class, () -> QueryLexer.nextToken());
	}
	
	
	//literal
	@Test
	public void literal() {
		QueryLexer QueryLexer = new QueryLexer("   \"literal\" \"*literal\" 		\"li*teral\"" );
		Token correctData[] = {
				new Token(TokenType.LITERAL, "literal" ),
				new Token(TokenType.LITERAL, "*literal" ),
				new Token(TokenType.LITERAL, "li*teral" ),
				new Token(TokenType.EOF, null )
		};
		checkTokenStream( QueryLexer, correctData);
	}
	@Test
	public void literalNoEnding() {
		QueryLexer QueryLexer = new QueryLexer("   \"literal" );
		assertThrows(LexerException.class, () -> QueryLexer.nextToken());
	}
	
	
	//and
	@Test
	public void and() {
		QueryLexer QueryLexer = new QueryLexer("   and AND And" );
		Token correctData[] = {
				new Token(TokenType.AND, null ),
				new Token(TokenType.AND, null ),
				new Token(TokenType.AND, null ),
				new Token(TokenType.EOF, null )
		};
		checkTokenStream( QueryLexer, correctData);
	}
	
	
	//operator
	@Test
	public void operators() {
		QueryLexer QueryLexer = new QueryLexer(" LIKE  < > <=  >= != = ==" );
		Token correctData[] = {
				new Token(TokenType.OPERATOR, "LIKE" ),
				new Token(TokenType.OPERATOR, "<" ),
				new Token(TokenType.OPERATOR, ">" ),
				new Token(TokenType.OPERATOR, "<=" ),
				new Token(TokenType.OPERATOR, ">=" ),
				new Token(TokenType.OPERATOR, "!=" ),
				new Token(TokenType.OPERATOR, "=" ),
				new Token(TokenType.OPERATOR, "=" ),
				new Token(TokenType.OPERATOR, "=" ),
				new Token(TokenType.EOF, null )
		};
				
		checkTokenStream( QueryLexer, correctData);	
	}
	
	
	//various token types
	@Test
	public void example1() {
		QueryLexer QueryLexer = new QueryLexer("jmbag=\"0000000003\"" );
		Token correctData[] = {
				new Token(TokenType.FIELD, "jmbag"),
				new Token(TokenType.OPERATOR, "=" ),
				new Token(TokenType.LITERAL, "0000000003" ),
				new Token(TokenType.EOF, null )
		};
		checkTokenStream( QueryLexer, correctData);	
	}
	@Test
	public void example2() {
		QueryLexer QueryLexer = new QueryLexer("lastName =\n" + 
				"\"Blažić\"" );
		Token correctData[] = {
				new Token(TokenType.FIELD, "lastName"),
				new Token(TokenType.OPERATOR, "=" ),
				new Token(TokenType.LITERAL, "Blažić" ),
				new Token(TokenType.EOF, null )
		};
		checkTokenStream( QueryLexer, correctData);	
	}
	@Test
	public void example3() {
		QueryLexer QueryLexer = new QueryLexer( "firstName>\"A\" and lastName LIKE \"B*ć\"");
		Token correctData[] = {
				new Token(TokenType.FIELD, "firstName"),
				new Token(TokenType.OPERATOR, ">" ),
				new Token(TokenType.LITERAL, "A" ),
				new Token(TokenType.AND, null ),
				new Token(TokenType.FIELD, "lastName" ),
				new Token(TokenType.OPERATOR, "LIKE" ),
				new Token(TokenType.LITERAL, "B*ć" ),
				new Token(TokenType.EOF, null )
		};
		checkTokenStream( QueryLexer, correctData);	
	}
	@Test
	public void example4() {
		QueryLexer QueryLexer = new QueryLexer( "firstName>\"A\" and firstName<\"C\" and "
				+ "lastName LIKE \"B*ć\" and jmbag>\"0000000002\"");
		Token correctData[] = {
				new Token(TokenType.FIELD, "firstName"),
				new Token(TokenType.OPERATOR, ">" ),
				new Token(TokenType.LITERAL, "A" ),
				new Token(TokenType.AND, null ),
				new Token(TokenType.FIELD, "firstName" ),
				new Token(TokenType.OPERATOR, "<" ),
				new Token(TokenType.LITERAL, "C" ),
				new Token(TokenType.AND, null ),
				new Token(TokenType.FIELD, "lastName" ),
				new Token(TokenType.OPERATOR, "LIKE" ),
				new Token(TokenType.LITERAL, "B*ć" ),
				new Token(TokenType.AND, null ),
				new Token(TokenType.FIELD, "jmbag"),
				new Token(TokenType.OPERATOR, ">" ),
				new Token(TokenType.LITERAL, "0000000002" ),
				new Token(TokenType.EOF, null )
		};
		checkTokenStream( QueryLexer, correctData);	
	}
	
	
	

	// Helper method for checking if SmartScriptLexer generates the same stream of tokens
	// as the given stream.
	private void checkTokenStream(QueryLexer lexer, Token[] correctData) {
	
		for(Token expected : correctData) {
			Token actual = lexer.nextToken();		
			assertEquals(expected.getType(), actual.getType() );
			assertEquals(expected.getValue(), actual.getValue() );
		}
	}
}
