package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;

/**
 * A rotate command.
 * 
 * @author Margarita Tolja
 *
 */
public class RotateCommand implements Command {
	
	private double angle;
	
	/**
	 * Creates a new rotate command that can rotate the direction
	 * of a "turtle" by a given angle.
	 * 
	 * @param angle
	 * 		An angle in degrees.
	 */
	public RotateCommand( double angle ) {
		this.angle = angle;
	}

	/**
	 * Rotates the current direction counter-clockwise for a specific angle 
	 * passed in constructor. 
	 */
	@Override
	public void execute(Context ctx, Painter painter) {
		ctx.getCurrentState().getDirection().rotate( Math.toRadians( angle ) );
	}
	
}
