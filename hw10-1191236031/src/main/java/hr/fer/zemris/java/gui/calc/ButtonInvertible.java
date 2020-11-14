package hr.fer.zemris.java.gui.calc;

import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JCheckBox;

/**
 * A button that represent some kind of invertible operator of
 * a calculator.
 * 
 * @author Margarita Tolja
 *
 * @param <E>
 * 			The type of operator.
 */
public class ButtonInvertible<E> extends JButton{
	
	/**
	 * Defualt serial ID.
	 */
	private static final long serialVersionUID = 1L;

	private E operator;
	private E inverseOperator;
	private String label;
	private String inverseLabel;
	private boolean state; //true for operator, false for inverse
	
	/**
	 * Initialises the button.
	 * @param label
	 * 			The label shown on the button.
	 * @param invLabel
	 * 			The label shown when the button is in 'inverse' state.
	 * @param operator
	 * 			The regular operator of the button.
	 * @param inverse
	 * 			The inverse operator of the button.
	 * @param box
	 * 			The checkbox on which depends the button's state.
	 */
	public ButtonInvertible( String label, String invLabel, E operator, E inverse, JCheckBox box ) {
		
		super();
		this.operator = operator;
		this.inverseOperator = inverse;
		this.label = label;
		this.inverseLabel = invLabel;
		
		this.setText( label );
		this.setBackground( Color.lightGray );
		state = true;
		
	}

	/**
	 * @return the current operator
	 */
	public E getOperator() {
		return state ? operator : inverseOperator;
	}

	/**
	 * @param state the state to set
	 */
	public void setState( boolean state ) {
		this.state = state;
		String currentLabel = state ? label : inverseLabel;
		this.setText( currentLabel );
	}
}
