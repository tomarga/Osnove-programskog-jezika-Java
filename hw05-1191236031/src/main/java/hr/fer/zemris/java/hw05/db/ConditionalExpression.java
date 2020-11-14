package hr.fer.zemris.java.hw05.db;

/**
 * A class that represents one conditional expression used while
 * doing queries on the student database emulator.
 * 
 * @author Margarita Tolja
 *
 */
public class ConditionalExpression {
	
	private IFieldValueGetter fieldGetter;
	private String stringLiteral;
	private IComparisonOperator comparisonOperator;
	
	/**
	 * Creates new conditional expression with passed getter, literal and operator.
	 * @param getter
	 * 		Getter of field value used in the condition.
	 * @param literal
	 * 		String literal that the second value of the condition.
	 * @param operator
	 * 		Comparison operator used in the condition.
	 */
	public ConditionalExpression( IFieldValueGetter getter, String literal, IComparisonOperator operator ) {
		fieldGetter = getter;
		this.stringLiteral = literal;
		this.comparisonOperator = operator;
	}

	/**
	 * @return the fieldGetter
	 */
	public IFieldValueGetter getFieldGetter() {
		return fieldGetter;
	}

	/**
	 * @return the stringLiteral
	 */
	public String getStringLiteral() {
		return stringLiteral;
	}

	/**
	 * @return the comparisonOperator
	 */
	public IComparisonOperator getComparisonOperator() {
		return comparisonOperator;
	}
	
}
