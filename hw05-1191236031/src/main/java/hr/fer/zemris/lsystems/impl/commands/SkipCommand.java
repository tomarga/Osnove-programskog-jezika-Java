package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;

/**
 * A skip command.
 * 
 * @author Margarita Tolja
 *
 */
public class SkipCommand implements Command { 
	
	private double step;
	
	/**
	 * Creates a skip command with step length as passed in an argument.
	 * @param step
	 * 		Step length.
	 */
	public SkipCommand( double step ) {
		this.step = step;
	}

	/**
	 * Updates the turtle's position, but the movement leaves no trace.
	 */
	@Override
	public void execute(Context ctx, Painter painter) {
		TurtleState state = ctx.getCurrentState();
		state.getPosition().translate( ctx.getCurrentState().getDirection().scaled( step ) );		
	}
	
}
