package hr.fer.zemris.java.hw05.db;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class ComparisonOperatorsTest {

	private IComparisonOperator operator;
	
	
	//LESS
	@Test
	public void lessString() {
		operator = ComparisonOperators.LESS;
		
		assertTrue( operator.satisfied( "Ana", "Blanka" ) );
		assertFalse( operator.satisfied( "Ana", "Ana" ) );
		assertFalse( operator.satisfied( "Blanka", "Ana" ) );
	}
	@Test
	public void lessNumber() {
		operator = ComparisonOperators.LESS;
		
		assertTrue( operator.satisfied( "0000000001", "0000000005" ) );
		assertFalse( operator.satisfied( "2", "1" ) );
		assertFalse( operator.satisfied( "2", "2" ) );
	}
	
	
	//LESS_OR_EQUAL
	@Test
	public void lessOrEqualString() {
		operator = ComparisonOperators.LESS_OR_EQUALS;
		
		assertTrue( operator.satisfied( "Ana", "Blanka" ) );
		assertTrue( operator.satisfied( "Ana", "Ana" ) );
		assertFalse( operator.satisfied( "Blanka", "Ana" ) );
	}
	@Test
	public void lessOrEqualNumber() {
		operator = ComparisonOperators.LESS_OR_EQUALS;
		
		assertTrue( operator.satisfied( "0000000001", "0000000005" ) );
		assertFalse( operator.satisfied( "2", "1" ) );
		assertTrue( operator.satisfied( "2", "2" ) );
	}
	
	
	//GREATER
	@Test
	public void greaterString() {
		operator = ComparisonOperators.GREATER;
		
		assertFalse( operator.satisfied( "Ana", "Blanka" ) );
		assertFalse( operator.satisfied( "Ana", "Ana" ) );
		assertTrue( operator.satisfied( "Blanka", "Ana" ) );
	}
	@Test
	public void greaterNumber() {
		operator = ComparisonOperators.GREATER;
		
		assertFalse( operator.satisfied( "0000000001", "0000000005" ) );
		assertTrue( operator.satisfied( "2", "1" ) );
		assertFalse( operator.satisfied( "2", "2" ) );
	}
	
	
	//GREATER_OR_EQUAL
	@Test
	public void greaterOrEqualString() {
		operator = ComparisonOperators.GREATER_OR_EQUALS;
		
		assertFalse( operator.satisfied( "Ana", "Blanka" ) );
		assertTrue( operator.satisfied( "Ana", "Ana" ) );
		assertTrue( operator.satisfied( "Blanka", "Ana" ) );
	}
	@Test
	public void greaterOrEqualNumber() {
		operator = ComparisonOperators.GREATER_OR_EQUALS;
		
		assertFalse( operator.satisfied( "0000000001", "0000000015" ) );
		assertTrue( operator.satisfied( "2", "1" ) );
		assertTrue( operator.satisfied( "2", "2" ) );
	}
	
	
	//EQUALS
	@Test
	public void equalsString() {
		operator = ComparisonOperators.EQUALS;
		
		assertTrue( operator.satisfied( "Ana", "Ana" ) );
		assertFalse( operator.satisfied( "Ana", "Blanka" ) );
	}
	@Test
	public void equalsNumber() {
		operator = ComparisonOperators.EQUALS;
		
		assertTrue( operator.satisfied( "5", "5" ) );
		assertFalse( operator.satisfied( "0000000001", "0000000002" ) );
	}
	
	
	//NOT_EQUALS
	@Test
	public void notEqualsString() {
		operator = ComparisonOperators.NOT_EQUALS;
		
		assertFalse( operator.satisfied( "Ana", "Ana" ) );
		assertTrue( operator.satisfied( "Ana", "Blanka" ) );
	}
	@Test
	public void notEqualsNumber() {
		operator = ComparisonOperators.NOT_EQUALS;
		
		assertFalse( operator.satisfied( "5", "5" ) );
		assertTrue( operator.satisfied( "0000000001", "0000000002" ) );
	}
	
	
	//LIKE
	@Test
	public void likeMoreThanOneWildcard() {
		operator = ComparisonOperators.LIKE;
		
		assertThrows( IllegalArgumentException.class, () -> {
			operator.satisfied( "Ana", "An*a*" );
		});

	}
	@Test
	public void likeNoWildcird() {
		operator = ComparisonOperators.LIKE;
		
		assertTrue( operator.satisfied( "Ana", "Ana" ) );
		assertFalse( operator.satisfied( "Ana", "Blanka" ) );
	}
	@Test
	public void likeJustWildcard() {
		operator = ComparisonOperators.LIKE;
		
		assertTrue( operator.satisfied( "Ana", "*" ) );
		assertTrue( operator.satisfied( "", "*" ) );
	}
	@Test
	public void likeWildcardBeginning() {
		operator = ComparisonOperators.LIKE;
		
		assertTrue( operator.satisfied( "Blanka", "*a" ) );
		assertTrue( operator.satisfied( "Blanka", "*ka" ) );
		assertTrue( operator.satisfied( "Blanka", "*nka" ) );
		assertTrue( operator.satisfied( "Blanka", "*anka" ) );
		assertTrue( operator.satisfied( "Blanka", "*lanka" ) );
		assertTrue( operator.satisfied( "Blanka", "*Blanka" ) );
		
		assertFalse( operator.satisfied( "Blanka", "*k" ) );
		assertFalse( operator.satisfied( "Blanka", "*aa" ) );
		assertFalse( operator.satisfied( "Blanka", "*kaa" ) );
	}
	@Test
	public void likeWildcardEnding() {
		operator = ComparisonOperators.LIKE;
		
		assertTrue( operator.satisfied( "Blanka", "B*" ) );
		assertTrue( operator.satisfied( "Blanka", "Bl*" ) );
		assertTrue( operator.satisfied( "Blanka", "Bla*" ) );
		assertTrue( operator.satisfied( "Blanka", "Blan*" ) );
		assertTrue( operator.satisfied( "Blanka", "Blank*" ) );
		assertTrue( operator.satisfied( "Blanka", "Blanka*" ) );
		
		assertFalse( operator.satisfied( "Blanka", "BB*" ) );
		assertFalse( operator.satisfied( "Blanka", "Blaa*" ) );
		assertFalse( operator.satisfied( "Blanka", "b*" ) );
	}
	@Test
	public void likeWildcardMiddle() {
		operator = ComparisonOperators.LIKE;
		
		assertTrue( operator.satisfied( "Blanka", "B*a" ) );
		assertTrue( operator.satisfied( "Blanka", "Bl*ka" ) );
		assertTrue( operator.satisfied( "Blanka", "Bla*nka" ) );
		assertTrue( operator.satisfied( "Blanka", "B*lanka" ) );
		assertTrue( operator.satisfied( "Blanka", "Bla*a" ) );
		assertTrue( operator.satisfied( "Blanka", "B*ka" ) );
		assertTrue( operator.satisfied( "AAAA", "AA*AA" ) );
		
		assertFalse( operator.satisfied( "Blanka", "B*Blanka" ) );
		assertFalse( operator.satisfied( "Blanka", "Blanka*a" ) );
		assertFalse( operator.satisfied( "Blanka", "B*lankaa" ) );
		assertFalse( operator.satisfied( "AAA", "AA*AA" ) );
	}
	
}
