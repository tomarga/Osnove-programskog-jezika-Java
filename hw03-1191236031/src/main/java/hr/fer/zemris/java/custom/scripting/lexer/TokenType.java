package hr.fer.zemris.java.custom.scripting.lexer;

/**
 * Enumeration of smart script token types.
 * 
 * @author Margarita Tolja
 *
 */
public enum TokenType {
	
	TEXT, // permits escaping - \\, \{
	EOF,
	
	STARTTAG, //{$
	ENDTAG, //$}
	
	EQUALITY, //=
	AT, //@ 
	OPERATOR, //+-*/^	
	INTEGER,
	DOUBLE, //digits-dot-digits format
	
	NAME, //starts by letter and after follows zero or more letters, digits or underscores
	STRING, //"string content" - permits escaping - \\, \", \n, \r, \t

}