package hr.fer.zemris.lsystems.impl.demo;

import hr.fer.zemris.lsystems.LSystem;
import hr.fer.zemris.lsystems.LSystemBuilderProvider;
import hr.fer.zemris.lsystems.gui.LSystemViewer;
import hr.fer.zemris.lsystems.impl.LSystemBuilderImpl;

/**
 * A LSystems usage example. 
 * Draws Koch's curve with specified depth(1-6).
 * The LSystem is configured from text.
 * 
 * @author Margarita Tolja
 *
 */
public class Glavni2 {

	/**
	 * Creates a GUI for displaying the curve with depths varying from 0 to 6. 
	 * @param args
	 */
	public static void main(String[] args) {
		
		LSystemViewer.showLSystem(createKochCurve2(LSystemBuilderImpl::new));

	}
	
	/**
	 * Creates a LSystem of a Koch's curve using the provider in argument.
	 * The LSystem is configured from text.
	 * @param provider
	 * 		Object that provides LSystemBuilder.
	 * @return
	 * 		LSystem that can draw the Koch's curve.
	 */
	private static LSystem createKochCurve2(LSystemBuilderProvider provider) {
		String[] data = new String[] {
		"origin 0.05 0.4",
		"angle	0",
		"unitLength	0.9",
		"unitLengthDegreeScaler 1.0 / 3.0",
		"",
		"command F draw 1",
		"command + rotate 60",
		"command - rotate -60",
		"",
		"axiom F",
		"",
		"production F F+F--F+F"
		};
		return provider.createLSystemBuilder().configureFromText(data).build();
		}

}
