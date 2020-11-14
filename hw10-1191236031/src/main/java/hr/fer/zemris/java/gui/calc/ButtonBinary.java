package hr.fer.zemris.java.gui.calc;

import java.awt.Color;
import java.util.function.DoubleBinaryOperator;

import javax.swing.JButton;

/**
 * Button in the calculator that represents some binary operator.
 * 
 * @author Margarita Tolja
 *
 */
public class ButtonBinary extends JButton {
	

	/**
	 * Defualt serial ID.
	 */
	private static final long serialVersionUID = 1L;
	
	private DoubleBinaryOperator operator;
	
	/**
	 * Initialises the button.
	 * @param label
	 * 			The label shown on the button.
	 * @param operator
	 * 			The operator that the button represents.
	 */
	public ButtonBinary( String label, DoubleBinaryOperator operator ) {
		
		super();
		this.setText( label );
		this.setBackground( Color.lightGray );
		this.operator = operator;
		
	}

	/**
	 * @return the operator
	 */
	public DoubleBinaryOperator getOperator() {
		return operator;
	}
	
}

