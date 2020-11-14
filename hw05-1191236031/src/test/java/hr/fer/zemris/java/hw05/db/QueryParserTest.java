package hr.fer.zemris.java.hw05.db;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import java.util.List;

public class QueryParserTest {

	private QueryParser parser;
	
	//parse
	@Test
	public void parseExample1() {
		
		parser = new QueryParser( "jmbag=\"0000000003\"" );
		ConditionalExpression[] expected = {
				new ConditionalExpression( FieldValueGetters.JMBAG, "0000000003", ComparisonOperators.EQUALS ),
		};
		
		assertExpression( expected, parser );	
	}
	@Test
	public void parseExample2() {
		
		parser = new QueryParser( "firstName>\"A\" and lastName LIKE \"B*\"" );
		ConditionalExpression[] expected = {
				new ConditionalExpression( FieldValueGetters.FIRST_NAME, "A", ComparisonOperators.GREATER ),
				new ConditionalExpression( FieldValueGetters.LAST_NAME, "B*", ComparisonOperators.LIKE ),
		};
		
		assertExpression( expected, parser );	
	}
	@Test
	public void parseExample3() {
		
		parser = new QueryParser( "firstName>\"A\" and firstName<\"C\" and "
				+ "lastName LIKE \"B*ć\" and jmbag>\"0000000002\"");
		ConditionalExpression[] expected = {
				new ConditionalExpression( FieldValueGetters.FIRST_NAME, "A", ComparisonOperators.GREATER ),
				new ConditionalExpression( FieldValueGetters.FIRST_NAME, "C", ComparisonOperators.LESS ),
				new ConditionalExpression( FieldValueGetters.LAST_NAME, "B*ć", ComparisonOperators.LIKE ),
				new ConditionalExpression( FieldValueGetters.JMBAG, "0000000002", ComparisonOperators.GREATER ),
		};
		
		assertExpression( expected, parser );	
	}

	
	//isDirect
	@Test
	public void isDirectTrue() {
		parser = new QueryParser( "jmbag=\"0000000003\"" );
		assertTrue( parser.isDirectQuery() );
	}
	@Test
	public void isDirectFalse() {
		parser = new QueryParser( "jmbag>\"0000000003\"" );
		assertFalse( parser.isDirectQuery() );
	}
	@Test
	public void isDirectFalse2() {
		parser = new QueryParser( "jmbag=\"0000000003\" and firstName>\"A\" " );
		assertFalse( parser.isDirectQuery() );
	}
	
	
	//getQueriedJmbag
	@Test
	public void getQueriedJmbag() {
		parser = new QueryParser( "jmbag=\"0000000003\"" );
		assertEquals( "0000000003", parser.getQueriedJMBAG() );
	}
	@Test
	public void getQueriedJmbagNotDirect() {
		parser = new QueryParser( "jmbag=\"0000000003\" and firstName>\"A\" ");
		assertThrows( IllegalStateException.class, () -> parser.getQueriedJMBAG() );
	}
	
	
	@Test 
	public void parseExample4() {
		parser = new QueryParser( " jmbag		=\"0123456789\"		" );
		
		assertTrue( parser.isDirectQuery() );
		assertEquals( "0123456789", parser.getQueriedJMBAG() );
		assertEquals( 1, parser.getQuery().size() );
	}
	
	
	
	//Utility method.
	public static void assertExpression( ConditionalExpression[] expected, QueryParser parser ) {
		
		List<ConditionalExpression> query = parser.getQuery();
		
		assertEquals( expected.length, query.size() );
		
		for ( int i = 0; i < expected.length; i++ ) {
			assertEquals( expected[i].getFieldGetter(), query.get( i ).getFieldGetter() );
			assertEquals( expected[i].getStringLiteral(), query.get( i ).getStringLiteral() );
			assertEquals( expected[i].getComparisonOperator(), query.get( i ).getComparisonOperator() );
		}	
	}
}