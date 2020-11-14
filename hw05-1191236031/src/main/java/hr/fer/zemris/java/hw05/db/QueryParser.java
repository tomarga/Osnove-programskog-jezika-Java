package hr.fer.zemris.java.hw05.db;

import java.util.ArrayList;
import java.util.List;

/**
 * A class that represents a parser for queries on student
 * database emulator. 
 * 
 * @author Margarita Tolja
 *
 */
public class QueryParser {
	
	private List<ConditionalExpression> expressions;
	private QueryLexer lexer;
	
	/**
	 * Initialises the query parser with input query.
	 * @param queryString
	 * 		The query string should contain everything after the 'query' keyword.
	 */
	public QueryParser( String queryString ) {
		expressions = new ArrayList<>();
		lexer = new QueryLexer( queryString );
		parse();		
	}
	
	/**
	 * Parses tokens retrieved from query lexer into appropriate conditional
	 * expressions and adds the expressions to the list.
	 * @throws QueryParserException If query is not in appropriate form.
	 */
	private void parse() {
		
		Token token = null;
		try {
			token = lexer.nextToken();
		} catch ( LexerException e ) {
			throw new QueryParserException( e.getMessage() );
		}
		
		while( token.getType() != TokenType.EOF ) {
			
			//field
			if ( token.getType() != TokenType.FIELD ) {
				throw new QueryParserException( "Conditional expression should start with a field name." );
			}
			IFieldValueGetter getter = null;
			switch ( token.getValue() ) {
			case "firstName" :
				getter = FieldValueGetters.FIRST_NAME;
				break;
			case "lastName" :
				getter = FieldValueGetters.LAST_NAME;
				break;
			case "jmbag" :
				getter = FieldValueGetters.JMBAG;
				break;
			default:
				throw new QueryParserException( "Invalid field token." );
			}
			try {
				token = lexer.nextToken();
			} catch ( LexerException e ) {
				throw new QueryParserException( e.getMessage() );
			}
			//operator
			if ( token.getType() != TokenType.OPERATOR ) {
				throw new QueryParserException( "Conditional expression should contain an operator"
						+ "between the field name and string literal. " );
			}
			IComparisonOperator operator = null;
			switch ( token.getValue() ) {
			case "<" : 
				operator = ComparisonOperators.LESS;
				break;
			case "<=" :
				operator = ComparisonOperators.LESS_OR_EQUALS;
				break;
			case ">" :
				operator = ComparisonOperators.GREATER;
				break;
			case ">=" :
				operator = ComparisonOperators.GREATER_OR_EQUALS;
				break;
			case "=" :
				operator = ComparisonOperators.EQUALS;
				break;
			case "!=" :
				operator = ComparisonOperators.NOT_EQUALS;
				break;
			case "LIKE" :
				operator = ComparisonOperators.LIKE;
				break;
			default:
				throw new QueryParserException( "Invalid operator token." );
			}
			try {
				token = lexer.nextToken();
			} catch ( LexerException e ) {
				throw new QueryParserException( e.getMessage() );
			}
			//literal
			if ( token.getType() != TokenType.LITERAL ) {
				throw new QueryParserException( "Conditional expression should end with a string literal." );
			}
			
			ConditionalExpression expression = new ConditionalExpression( getter, token.getValue(), operator );
			expressions.add( expression );
			
			try {
				token = lexer.nextToken();
			} catch ( LexerException e ) {
				throw new QueryParserException( e.getMessage() );
			}
			if ( token.getType() == TokenType.AND ) {
				try {
					token = lexer.nextToken();
				} catch ( LexerException e ) {
					throw new QueryParserException( e.getMessage() );
				}
			}
		}
	}
	
	/**
	 * Returns the list of all conditional expressions in a query.
	 * @return
	 * 		List of the conditional expressions.
	 */
	public List<ConditionalExpression> getQuery() {
		return expressions;
	}
	
	/**
	 * Checks if query is direct.
	 * Direct queries are considered all queries with one condition whose 
	 * field name is 'jmbag' and whose operator is the equality operator.
	 * @return
	 */
	public boolean isDirectQuery() {
		if ( expressions.size() != 1 ) {
			return false;
		}
		ConditionalExpression first = expressions.get( 0 );
		return first.getFieldGetter().equals( FieldValueGetters.JMBAG )
				&& first.getComparisonOperator().equals( ComparisonOperators.EQUALS );
	}
	
	/**
	 * Returns the jmbag from the direct query.
	 * @throws IllegalStateException if the query isn't direct.
	 * @return
	 * 		Jmbag value.
	 */
	public String getQueriedJMBAG() {
		if ( !isDirectQuery() ) {
			throw new IllegalStateException( "Not a direct query." );
		}
		return expressions.get( 0 ).getStringLiteral();
	}

}
