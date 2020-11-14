package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;

/**
 * A pop command.
 * 
 * @author Margarita Tolja
 *
 */
public class PopCommand implements Command{

	/**
	 * Sets the state of a context to the previous one.
	 */
	@Override
	public void execute( Context ctx, Painter painter ) {
		ctx.popState();		
	}
	
}
