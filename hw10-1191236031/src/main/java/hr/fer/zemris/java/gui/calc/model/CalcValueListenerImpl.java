package hr.fer.zemris.java.gui.calc.model;

import javax.swing.JLabel;

/**
 * Implementation of calculator value listener that
 * is interested in current number in calculator's display.
 * 
 * @author Margarita Tolja
 *
 */
public class CalcValueListenerImpl implements CalcValueListener {

	private JLabel component;
	
	public CalcValueListenerImpl( JLabel component ) {
		component.setText( "0" );
		this.component = component;
	}
	
	@Override
	public void valueChanged( CalcModel model ) {
		component.setText( model.toString() );
	}

}
