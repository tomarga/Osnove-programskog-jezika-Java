package hr.fer.zemris.java.gui.calc;

import java.awt.Color;
import java.awt.Container;
import java.awt.event.ItemEvent;
import java.util.function.DoubleBinaryOperator;
import java.util.function.DoubleUnaryOperator;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import hr.fer.zemris.java.gui.calc.model.CalcModelImpl;
import hr.fer.zemris.java.gui.calc.model.CalcValueListenerImpl;
import hr.fer.zemris.java.gui.calc.model.CalculatorInputException;
import hr.fer.zemris.java.gui.layouts.CalcLayout;
import hr.fer.zemris.java.gui.layouts.RCPosition;

/**
 * A simple old-school calculator.
 * 
 * @author Margarita Tolja
 *
 */
public class Calculator extends JFrame{
	
	private static final long serialVersionUID = 1L;
	
	private CalcModelImpl model;
	
	/**
	 * Constructor. Initialises GUI components.
	 */
	public Calculator() {
		model = new CalcModelImpl();
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setSize(700, 500);
		initGUI();
	}
	
	/**
	 * Sets the GUI buttons and a label.
	 */
	private void initGUI() {
		
		Container cp = getContentPane();
		cp.setLayout( new CalcLayout( 5 ) );
		
		//display
		JLabel display = new JLabel();
		display.setBackground( Color.YELLOW );
		display.setOpaque( true );
		display.setFont( display.getFont().deriveFont( 30f ) );
		display.setHorizontalAlignment( SwingConstants.RIGHT );;
		model.addCalcValueListener( new CalcValueListenerImpl( display ) );
		cp.add( display, new RCPosition( 1, 1 ) );
	
		//numbers
		for ( byte i = 0; i <= 9; i++ ) {
			
			ButtonNumber buttonNumber = new ButtonNumber( model, i );
			
			int row = (int) ( 5 - Math.ceil( (double) i / 3 ) );
			int column = ( i == 0 ) ? 3 : 5 - i % 3;
			cp.add( buttonNumber, new RCPosition( row, column ) );
		}
		
		//binary
		setupBinary( cp, "/", (x, y) -> x / y, new RCPosition( 2, 6 )  );
		setupBinary( cp, "*", (x, y) -> x * y, new RCPosition( 3, 6 )  );
		setupBinary( cp, "-", (x, y) -> x - y, new RCPosition( 4, 6 )  );
		setupBinary( cp, "+", (x, y) -> x + y, new RCPosition( 5, 6 )  );
		
		//equals
		JButton equality = new JButton( "=" );
		equality.setBackground( Color.lightGray );
		equality.addActionListener( e -> processResult() );
		
		cp.add( equality, new RCPosition( 1, 6 ) );
		
		//checkbox
		JCheckBox box = new JCheckBox( "Inv", false );
		cp.add( box, new RCPosition( 5, 7 ) );
		
		//unary
		setupInvertible( cp, "sin", "arcsin", (DoubleUnaryOperator)( Math::sin ), Math::asin, box, new RCPosition( 2, 2 ) );
		setupInvertible( cp, "cos", "arccos", (DoubleUnaryOperator)Math::cos, Math::acos, box, new RCPosition( 3, 2 ) );
		setupInvertible( cp, "tg", "arctg", (DoubleUnaryOperator)Math::tan, Math::atan, box, new RCPosition( 4, 2 ) );
		setupInvertible( cp, "ctg", "arcctg", (DoubleUnaryOperator)( x -> 1/Math.tan(x) ), x -> Math.PI / 2 - Math.atan(x), box, new RCPosition( 5, 2 ) );
		setupInvertible( cp, "1/x", "1/x", (DoubleUnaryOperator)( x -> 1/x ), x -> 1/x, box, new RCPosition( 2, 1 ) );
		setupInvertible( cp, "log", "10^x", (DoubleUnaryOperator)( Math::log10 ), x -> Math.pow( 10, x ), box, new RCPosition( 3, 1 ) );
		setupInvertible( cp, "ln", "e^x", (DoubleUnaryOperator)( Math::log ), x -> Math.pow( Math.E, x ), box, new RCPosition( 4, 1 ) );
		setupInvertible( cp, "x^n", "x^(1/n)", (DoubleBinaryOperator)( (x,n) -> Math.pow(x,n) ), (x,n) -> Math.pow(x,1/n), box, new RCPosition( 5, 1 ) );
		
		//switch sign
		JButton switchSign = new JButton( "+/-" );
		switchSign.setBackground( Color.lightGray );
		switchSign.addActionListener( e -> model.swapSign() );
		cp.add( switchSign, new RCPosition( 5, 4 ) );
		
		//decimal point
		JButton point = new JButton( "." );
		point.setBackground( Color.lightGray );
		point.addActionListener( e -> model.insertDecimalPoint() );
		cp.add( point, new RCPosition( 5, 5 ) );
		
		//clear
		JButton clear = new JButton( "clr" );
		clear.setBackground( Color.lightGray );
		clear.addActionListener( e -> model.clear() );
		cp.add( clear, new RCPosition( 1, 7 ) );
		
		//restart
		JButton restart = new JButton( "res" );
		restart.setBackground( Color.lightGray );
		restart.addActionListener( e -> model.clearAll() );
		cp.add( restart, new RCPosition( 2, 7 ) );
		
	}
	
	/**
	 * Utility function for setting up the buttons representing binary operators.
	 * @param cp
	 * 			Container.
	 * @param label
	 * 			The label of the button.
	 * @param operator
	 * 			The appropriate binary operator.
	 * @param position
	 * 			Position of the button in the calculator's layout.
	 */
	private void setupBinary( Container cp, String label, DoubleBinaryOperator operator, RCPosition position ) {
		
		ButtonBinary button = new ButtonBinary( label, operator );
		button.addActionListener( e -> processBinary( operator ) );
		
		cp.add( button, position );
		
	}
	
	/**
	 * Utility function for setting up the buttons representing invertible operators.
	 * @param <E>
	 * 			Type of operator - binary or unary.
	 * @param cp
	 * 			Container.
	 * @param label
	 * 			Button's label.
	 * @param invLabel
	 * 			Button's inverse' label.
	 * @param operator
	 * 			Regular operator.
	 * @param inverseOperator
	 * 			Inverted operator.
	 * @param box
	 * 			The check-box that determines the current operator of the button.
	 * @param position
	 * 			Grid position of the button.
	 */
	private <E> void setupInvertible( Container cp, String label, String invLabel, E operator, E inverseOperator, JCheckBox box, RCPosition position ) {
		
		ButtonInvertible<E> button = new ButtonInvertible<E>( label, invLabel, operator, inverseOperator, box );
		
		if ( operator instanceof DoubleUnaryOperator ) {
			button.addActionListener( e -> processUnary( (DoubleUnaryOperator) button.getOperator() ) );
		} else {
			button.addActionListener( e -> processBinary( (DoubleBinaryOperator) button.getOperator() ) );
		}
		
		box.addItemListener( e -> {
			switch ( e.getStateChange() ) {
			case ItemEvent.SELECTED:
				button.setState( false );
				break;
			case ItemEvent.DESELECTED:
				button.setState( true );
				break;
			}
		});
		
		cp.add( button, position );
 		
	}
	
	/**
	 * Calculates and displays the result of some calculation.
	 */
	private void processResult() {
		
		DoubleBinaryOperator operator = model.getPendingBinaryOperation();
		if ( operator != null && model.isActiveOperandSet() ) {
			double operand = model.getActiveOperand();
			double result = operator.applyAsDouble( operand, model.getValue() );
			
			model.setValue( result ) ;
			
			model.clearActiveOperand();
			model.clearPendingBinaryOperation();
		}
		
	}
	
	/**
	 * Processes binary operation and displays the result.
	 * @param operator
	 * 			Binary operator.
	 */
	private void processBinary( DoubleBinaryOperator operator ) {
		
		if ( model.freezeValue() != null ) {
			throw new CalculatorInputException();
		}
		
		if ( model.isActiveOperandSet() ) {
			processResult();
		} 
		model.setActiveOperand( model.getValue() );
		model.setPendingBinaryOperation( operator );
		model.clear();
		
	}
	
	/**
	 * Processes unary operation and displays the result.
	 * @param operator
	 * 			Unary operator.
	 */
	private void processUnary( DoubleUnaryOperator operator ) {
		
		if ( model.freezeValue() != null ) {
			throw new CalculatorInputException();
		}
		
		double result = operator.applyAsDouble( model.getValue() );
		model.setValue( result );
		
	}
	
	/**
	 * Creates a calculator instance.
	 * @param args
	 * 			NO command line arguments expected.
	 */
	public static void main(String[] args) {
		
		SwingUtilities.invokeLater(()->{new Calculator().setVisible(true);
		});
	}
}
