package hr.fer.zemris.java.hw05.db;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class ConditionalExpressionTest {

	private ConditionalExpression ex;
	
	@Test
	public void conditionalExpression1() {
		ex = new ConditionalExpression( FieldValueGetters.FIRST_NAME, "Ana", ComparisonOperators.EQUALS );
		StudentRecord record = new StudentRecord( "0000000000", "Anić", "Ana", 5 );
		
		assertTrue( ex.getComparisonOperator()
				.satisfied( ex.getFieldGetter().get( record ), ex.getStringLiteral() ) );
	}
}
