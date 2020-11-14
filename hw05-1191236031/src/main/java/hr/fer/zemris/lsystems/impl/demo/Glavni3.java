package hr.fer.zemris.lsystems.impl.demo;

import hr.fer.zemris.lsystems.gui.LSystemViewer;
import hr.fer.zemris.lsystems.impl.LSystemBuilderImpl;

/**
 * A LSystems usage example. 
 * Draws curves with specified depth(1-6).
 * The LSystem is configured from textual files.
 * 
 * @author Margarita Tolja
 *
 */
public class Glavni3 {

	/**
	 * Creates a GUI for displaying the curve with depths varying from 0 to 6. 
	 * User chooses the file from which the LSystem information is gathered.
	 * @param args
	 */
	public static void main(String[] args) {
		
		LSystemViewer.showLSystem(LSystemBuilderImpl::new);

	}

}
