package hr.fer.zemris.java.hw05.db;

/**
 * Interface of comparison operators.
 * 
 * @author Margarita Tolja
 *
 */
public interface IComparisonOperator {

	/**
	 * Checks if the values are related as suggested by the operator.
	 * @param value1
	 * 		First comparison operand.
	 * @param value2
	 * 		Second comparison operand.
	 * @return 
	 * 		<code>true</code> if the comparison is valid;
	 * 		<code>false</code> otherwise.
	 */
	public boolean satisfied( String value1, String value2 );
}
