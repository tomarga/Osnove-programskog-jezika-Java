package hr.fer.zemris.lsystems.impl.commands;

import java.awt.Color;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;

/**
 * A colour command.
 * 
 * @author Margarita Tolja
 *
 */
public class ColorCommand implements Command { 
	
	private Color color;
	
	/**
	 * Creates a colour command that can change the current line colour.
	 * @param color
	 */
	public ColorCommand( Color color ) {
		this.color = color;
	}

	/**
	 * Changes the current colour.
	 */
	@Override
	public void execute(Context ctx, Painter painter) {
		ctx.getCurrentState().setColor(color);
	}

}
