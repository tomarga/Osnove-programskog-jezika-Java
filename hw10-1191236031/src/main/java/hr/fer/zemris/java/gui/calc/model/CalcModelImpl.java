package hr.fer.zemris.java.gui.calc.model;

import java.util.ArrayList;
import java.util.List;
import java.util.function.DoubleBinaryOperator;

public class CalcModelImpl implements CalcModel {

	private boolean editable;
	private boolean sign; //true for positive, false for negative
	private String currentString;
	private double currentNumber;
	private String frozen;
	private Double activeOperand;
	private DoubleBinaryOperator pendingOperation;
	private List<CalcValueListener> listeners;
	
	/**
	 * Default constructor.
	 * Initialises current number in calculator to positive zero.
	 * Sets the calculator model to 'editable' state, with no frozen
	 * values.
	 */
	public CalcModelImpl() {
		
		frozen = null;
		currentString = new String( "" );
		currentNumber = 0;
		editable = true;
		sign = true;
		activeOperand = null;
		pendingOperation = null;
		listeners = new ArrayList<>();

	}
	
	@Override
	public void addCalcValueListener( CalcValueListener l ) {
		
		//check if null
		if ( l == null ) {
			throw new NullPointerException( "Cannot add null CalcValueListener." );
		}
		listeners.add( l );
	}

	@Override
	public void removeCalcValueListener( CalcValueListener l ) {
		
		if ( listeners.contains( l ) ) {
			listeners.remove( l );
		}
	}

	@Override
	public double getValue() {
		return sign ? currentNumber : -currentNumber;
	}

	@Override
	public void setValue( double value ) {
		
		//set sign
		sign = value >= 0;
		
		currentNumber = Math.abs( value );
		currentString = Double.toString( currentNumber );

		editable = false;
		
		notifyAllListeners();
	}

	@Override
	public boolean isEditable() {
		return editable;
	}

	@Override
	public void clear() {
		
		currentNumber = 0;
		currentString = "";
		editable = true;
		sign = true;
		
	}

	@Override
	public void clearAll() {
		
		setValue(0);
		clear();
		activeOperand = null;
		pendingOperation = null;
		
		notifyAllListeners();
	}

	@Override
	public void swapSign() throws CalculatorInputException {
		
		//check if editable
		if ( !editable ) {
			throw new CalculatorInputException( "Cannot swap sing in non-editable mode." );
		}
		//change sign
		sign = ( sign ) ? false : true;
		//remove freeze value
		frozen = null;
		
		notifyAllListeners();
	}
	

	@Override
	public void insertDecimalPoint() throws CalculatorInputException {
		
		//check if editable
		if ( !editable ) {
			throw new CalculatorInputException( 
					"Cannot insert decimal point if calculator is not in editable mode." );
		}
		//check for existing decimal point
		if ( currentString.contains( "." ) ) {
			throw new CalculatorInputException( "Cannot add more than one decimal point." );
		}
		//check for digits before dot
		if ( currentString.isEmpty() ) {
			throw new CalculatorInputException( "Cannot add dot without digit(s) before." );
		}
		//add point
		currentString += ".";
		
		frozen = null;
		
		notifyAllListeners();
	}

	@Override
	public void insertDigit( int digit ) throws CalculatorInputException, IllegalArgumentException {
		
		//check if editable
		if ( !editable ) {
			throw new CalculatorInputException( 
					"Cannot add digits to current number if calculator is not in editable mode." );
		}
		//check if digit is valid
		if ( digit < 0 || digit > 9 ) {
			throw new IllegalArgumentException( "New digit has to be inside interval 0-9." );
		}
		//if 0 is added to 0
		if ( currentString.equals( "0" ) ) {
			if ( digit == 0 ) {
				return;
			} else {
				currentString = "";
			}
		}
		//update current
		currentString += String.valueOf( digit );
		try {
			currentNumber = Double.parseDouble( currentString );
			//check if the number overpasses the max double value
			if ( currentNumber > Double.MAX_VALUE ) {
				throw new CalculatorInputException( "The number is too big." );
			}
		} catch ( NumberFormatException e ) {
			
			//restore the previous value of a string
			currentString = currentString.substring( 0, currentString.length() - 1 );
			
			throw new CalculatorInputException( 
					"By adding the last digit, the current number overpasses the bounds of a double." );
		}
		
		frozen = null;
		
		notifyAllListeners();
	}

	@Override
	public boolean isActiveOperandSet() {
		return activeOperand != null;
	}

	@Override
	public double getActiveOperand() throws IllegalStateException {
	
		if ( activeOperand == null ) {
			throw new IllegalStateException( "There is no active operand to get." );
		}
		return activeOperand.doubleValue();
	}

	@Override
	public void setActiveOperand( double activeOperand ) {
		this.activeOperand = Double.valueOf( activeOperand );
	}

	@Override
	public void clearActiveOperand() {
		activeOperand = null;
	}

	@Override
	public DoubleBinaryOperator getPendingBinaryOperation() {
		return pendingOperation;
	}

	@Override
	public void setPendingBinaryOperation( DoubleBinaryOperator op ) {
		pendingOperation = op;
		//set frozen
		frozen = currentString;
	}
	
	public void clearPendingBinaryOperation() {
		pendingOperation = null;
	}
	
	/**
	 * Freeze value getter.
	 * @return
	 * 			The frozen string, or null if there is none.
	 */
	public String freezeValue() {
		return frozen;
	}
	
	/**
	 * Returns the freeze value if it is not null.
	 * Otherwise, if the current string of inserted digits is empty, method 
	 * return positive or negative zero (based on the the current numbers sign).
	 * If the current string is not empty, that value is returned along with
	 * the appropriate sign.
	 */
	@Override
	public String toString() {
		
		if ( freezeValue() != null ) {
			return freezeValue();
		}
		if ( currentString.isEmpty() ) {
			return ( sign ) ? new String( "0" ) : new String( "-0" );
		}
		return ( sign ) ? currentString : "-".concat( currentString );
	}
	
	/**
	 * Notifies all listeners of value change.
	 */
	private void notifyAllListeners() {
		
		for ( CalcValueListener listener : listeners ) {
			listener.valueChanged( this );
		}
	}

}
