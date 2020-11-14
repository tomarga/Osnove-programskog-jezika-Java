package hr.fer.zemris.java.hw05.db;

/**
 * A class that offers 7 different comparison operators( <, <=, >, >=, =, != and LIKE )
 * The operators compare two string values lexicographically.
 * LIKE operator checks if the first value is in the same form as the second value,
 * meaning all letters have to be equal, but in the place where second value contains
 * wild card(*) the first value can have any character sequence.
 * 
 * @author Margarita Tolja
 *
 */
public class ComparisonOperators {
	
	public static final IComparisonOperator LESS = ( value1, value2 ) -> value1.compareTo( value2 ) < 0;
	public static final IComparisonOperator LESS_OR_EQUALS = ( value1, value2 ) -> value1.compareTo( value2 ) <= 0;
	public static final IComparisonOperator GREATER = ( value1, value2 ) -> value1.compareTo( value2 ) > 0;
	public static final IComparisonOperator GREATER_OR_EQUALS = ( value1, value2 ) -> value1.compareTo( value2 ) >= 0;
	public static final IComparisonOperator EQUALS = ( value1, value2 ) -> value1.compareTo( value2 ) == 0;
	public static final IComparisonOperator NOT_EQUALS = ( value1, value2 ) -> value1.compareTo( value2 ) != 0;
	public static final IComparisonOperator LIKE;
	
	static {
		LIKE = new IComparisonOperator() {
					
			@Override
			public boolean satisfied( String value1, String value2 ) {
				
				int numberOfWildCards = (int) value2.chars().filter( ch -> ch == '*' ).count();

				//if there is no wild card (*)
				if ( numberOfWildCards == 0 ) {
					return value1.compareTo( value2 ) == 0;
				}
				//if there is more than one wild card
				if ( numberOfWildCards > 1 ) {
					throw new IllegalArgumentException( "String literal after LIKE operand "
							+ "can contain at most one wildcard(*)." );
				}
				
				int index = value2.indexOf( '*' ); //wild card index
				
				//comparing substrings before '*'
				if ( index != 0 && value1.substring( 0, index ).compareTo( value2.substring( 0, index ) ) != 0 ) {
					return false;
				}
				//comparing substrings after '*'
				value1 = value1.substring( index ); //unchecked part of the first value
				int length = value2.substring( index+1 ).length(); //number of characters after the wild card
				
				if ( value1.length() < length ) {
					return false;
				}
				if ( index != value2.length()-1 && value1.substring( value1.length() - length )
						.compareTo( value2.substring( index+1 ) ) != 0 ) {
					return false;
				}
				return true;	
			}
		};
	}

}
