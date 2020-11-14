package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;

/**
 * A push command.
 * 
 * @author Margarita Tolja
 *
 */
public class PushCommand implements Command {

	/**
	 * Copies the current state and pushes it on the state stack.
	 */
	@Override
	public void execute( Context ctx, Painter painter ) {
		ctx.pushState( ctx.getCurrentState().copy() );		
	}
	
}
