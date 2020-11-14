package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;
import hr.fer.zemris.math.Vector2D;

/**
 * A draw command.
 * 
 * @author Margarita Tolja
 *
 */
public class DrawCommand implements Command {
	
	private double numberOfSteps;
	
	/**
	 * Creates a draw command that can draw specified number of steps at once.
	 * @param steps
	 * 		Number of steps.
	 */
	public DrawCommand( double steps ) {
		numberOfSteps = steps;
	}

	/**
	 * Makes a certain number of steps in the process of fractal drawing.
	 * Updates the context state. 
	 */
	@Override
	public void execute( Context ctx, Painter painter ) {
	
		for ( int i = 0; i < numberOfSteps; i++ ) {
			
			TurtleState state = ctx.getCurrentState();
			
			Vector2D oldPosition = state.getPosition().copy();
			double step = state.getStep();
			Vector2D newPosition = state.getPosition()
					.translated( ctx.getCurrentState().getDirection().scaled( step ) );
			
			state.setPosition( newPosition );
			
			painter.drawLine( oldPosition.getX(), oldPosition.getY(), state.getPosition().getX(),
					state.getPosition().getY(), state.getColor(), state.getLineWidth() );
		}
	}

}
