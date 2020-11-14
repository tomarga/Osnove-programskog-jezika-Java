package hr.fer.zemris.java.hw05.db;

import java.util.List;

/**
 * A class represents an object that filters the student records
 * based on various conditional expressions in one query.
 * 
 * @author Margarita Tolja
 *
 */
public class QueryFilter implements IFilter {

	private List<ConditionalExpression> conditions;
	
	public QueryFilter( List<ConditionalExpression> expressions ) {
		conditions = expressions;		
	}
	
	/**
	 * Checks if the record satisfies all conditions of some query.
	 */
	@Override
	public boolean accepts( StudentRecord record ) {
		for ( ConditionalExpression condition : conditions ) {
			IFieldValueGetter getter = condition.getFieldGetter();
			IComparisonOperator operator = condition.getComparisonOperator();
			String literal = condition.getStringLiteral();
			
			if ( !operator.satisfied( getter.get( record ), literal ) ) {
				return false;
			}
		}
		return true;
	}
	
}
