package hr.fer.zemris.java.gui.calc;

import java.awt.Color;

import javax.swing.JButton;

import hr.fer.zemris.java.gui.calc.model.CalcModel;

/**
 * A button in the calculator used for input of digits.
 * 
 * @author Margarita Tolja
 *
 */
public class ButtonNumber extends JButton {

	/**
	 * Default serial ID.
	 */
	private static final long serialVersionUID = 1L;
	
	private int number;

	/**
	 * Initialises the button and its listener.
	 * @param model
	 * 			The calculator's model.
	 * @param number
	 * 			The number that the button represents.
	 */
	public ButtonNumber( CalcModel model, int number ) {
		
		this.number = number;
		this.setName( String.valueOf( number ) );
		this.setBackground( Color.lightGray );
		this.setFont( this.getFont().deriveFont( 30f ) );
		this.setText( this.getName() );
		
		this.addActionListener( e -> model.insertDigit( number ) );
	}

	/**
	 * @return the number
	 */
	public int getNumber() {
		return number;
	}
}
