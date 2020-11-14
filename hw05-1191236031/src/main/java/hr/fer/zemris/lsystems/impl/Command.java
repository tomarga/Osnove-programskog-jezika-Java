package hr.fer.zemris.lsystems.impl;

import hr.fer.zemris.lsystems.Painter;

/**
 * An interface of the objects able to execute a command. 
 * 
 * @author Margarita Tolja
 *
 */
public interface Command {
	
	/**
	 * Executes a certain command using the painter passed as an argument in
	 * a certain context.
	 * @param ctx
	 * 		Context defining the state of a fractal drawing process.
	 * @param painter
	 * 		Painter used for drawing on a window.
	 */
	void execute( Context ctx, Painter painter );
}
