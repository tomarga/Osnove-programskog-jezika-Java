package hr.fer.zemris.lsystems.impl;

import hr.fer.zemris.java.custom.collections.ObjectStack;

/**
 * A class that represents the context of a fractal drawing.
 * 
 * @author Margarita Tolja
 *
 */
public class Context {

	private ObjectStack<TurtleState> states;

	/**
	 * Default constructor. 
	 * Creates an empty stack of states.
	 */
	public Context() {
		states = new ObjectStack<>();
	}
	
	/**
	 * Gets the current state of a turtle.
	 * @return
	 * 		Current state of a turtle.
	 */
	public TurtleState getCurrentState() {
		return states.peek();
	}
	
	/**
	 * Adds a new state as a current state.
	 * @param state
	 * 		New current state.
	 */
	public void pushState( TurtleState state ) {
		states.push( state );
	}
	
	/**
	 * Sets the previous state as current state.
	 */
	public void popState() {
		states.pop();
	}
}
