package hr.fer.zemris.java.custom.collections.demo;

import hr.fer.zemris.java.custom.collections.ObjectStack;
import hr.fer.zemris.java.custom.collections.EmptyStackException;

/**
 * Program is an command-line application that offers  
 * the evaluation of a basic postfix expressions with integers.
 * Valid operators are: +, -, *, / and %.
 * 
 * @author Margarita Tolja
 *
 */
public class StackDemo {
	
	/**
	 * Main method evaluated and outputs the postfix expression
	 * given as one String argument.
	 * 
	 * @param args
	 * 		Expected form: "postfix expression"
	 */
	public static void main( String args[] ) {
		
		if ( args.length != 1 ) {
			System.out.println( "Expected 1 expression as console input, " 
					+ args.length + " given." );
			return;
		}
		String rawInput = args[0];
		String[] input = rawInput.split( "\\s+" );
		
		ObjectStack stack = new ObjectStack();
		
		for ( String word : input ) {
			//if element is number, push it on stack and continue
			try {
				Integer number = Integer.valueOf( word );
				stack.push( number );
				continue;				
			}
			//else pop two elements from stack, perform operation and push result on stack
			catch ( NumberFormatException numEx ) {
			
				Integer operand1;
				Integer operand2;
				try {
					operand2 = (Integer)stack.pop();
					operand1 = (Integer)stack.pop();
				}
				catch ( EmptyStackException emex ) {
					System.out.println( "Invalid postfix expression. Too few operands." );
					return;
				}
				try {
					calculateAndPush( operand1, operand2, word, stack );
				}
				catch( IllegalArgumentException argEx ) {
					System.out.println( argEx.getMessage() );
					return;
				}
			}
		}
		if ( stack.size() != 1 ) {
			System.out.println( "Invalid postfix expression. Too many operands." );
		}
		else {
			Integer result = (Integer)stack.pop();
			System.out.println( "Expression evaluates to " + result + "." );
		}
	}
	
	/**
	 * Method calculates the corresponding operation between two given operands and 
	 * pushes the result to given stack collection.
	 * @param operand1 
	 * 		First operand.
	 * @param operand2
	 * 		Second operand.
	 * @param operator
	 * 		Operator(as a String).
	 * @param stack
	 * 		Stack collection where result is to be stored.
	 */
	public static void calculateAndPush( Integer operand1, Integer operand2, String operator, ObjectStack stack ) {
		
		switch( operator ) {
		case( "+" ):
			stack.push( Integer.valueOf( operand1 + operand2 ) );
			break;
		case( "-" ):
			stack.push( Integer.valueOf( operand1 - operand2 ) );
			break;
		case( "*" ):
			stack.push( Integer.valueOf( operand1 * operand2 ) );
			break;
		case( "%" ):
			stack.push( Integer.valueOf( operand1 % operand2 ) );
			break;
		case( "/" ):
			if ( operand2.intValue() == 0 ) {
				throw new IllegalArgumentException( "Invalid postfix expression."
						+ " Attempted division by zero." );
			}
			stack.push( Integer.valueOf( operand1 / operand2 ) );
			break;
		default: 
			throw new IllegalArgumentException( "Invalid postfix expression. "
					+ "Valid operators: +,-,*,/ and %. Valid operands are integers." );
		}
	}
}
