package hr.fer.zemris.java.custom.scripting.lexer;

/**
 * Enumeration that specifies the current state of smartscript lexer
 * and thus the rules of tokenizing given input.
 * 
 * @author Margarita Tolja
 *
 */
public enum LexerState {
	TEXT,
	TAG
}