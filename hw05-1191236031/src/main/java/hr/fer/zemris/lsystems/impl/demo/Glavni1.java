package hr.fer.zemris.lsystems.impl.demo;

import hr.fer.zemris.lsystems.LSystem;
import hr.fer.zemris.lsystems.LSystemBuilderProvider;
import hr.fer.zemris.lsystems.gui.LSystemViewer;
import hr.fer.zemris.lsystems.impl.LSystemBuilderImpl;

/**
 * A LSystems usage example. 
 * Draws Koch's curve with specified depth(1-6).
 * The LSystem is configured through specified methods.
 * 
 * @author Margarita Tolja
 *
 */
public class Glavni1 {

	/**
	 * Creates a GUI for displaying the curve with depths varying from 0 to 6. 
	 * @param args
	 */
	public static void main(String[] args) {
		
		LSystemViewer.showLSystem(createKochCurve(LSystemBuilderImpl::new));

	}

	/**
	 * Creates a LSystem of a Koch's curve using the provider in argument.
	 * The LSystem is configured through specified methods.
	 * @param provider
	 * 		Object that provides LSystemBuilder.
	 * @return
	 * 		LSystem that can draw the Koch's curve.
	 */
	private static LSystem createKochCurve(LSystemBuilderProvider provider) {
		
		return provider.createLSystemBuilder()
		.registerCommand('F', "draw 1")
		.registerCommand('+', "rotate 60")
		.registerCommand('-', "rotate -60")
		.setOrigin(0.05, 0.4)
		.setAngle(0)
		.setUnitLength(0.9)
		.setUnitLengthDegreeScaler(1.0/3.0)
		.registerProduction('F', "F+F--F+F")
		.setAxiom("F")
		.build();
	}
}
