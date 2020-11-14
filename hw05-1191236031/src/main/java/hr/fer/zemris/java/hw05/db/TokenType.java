package hr.fer.zemris.java.hw05.db;

/**
 * Enumeration of query token types.
 * 
 * @author Margarita Tolja
 *
 */
public enum TokenType {
	
	EOF, 
	FIELD, //field name (firstName, lastName, jmbag)
	LITERAL, //string literal
	OPERATOR, //<,<=,>,>=,=,!= or LIKE
	AND //and, case insensitive

}