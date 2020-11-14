package hr.fer.zemris.java.custom.scripting.elems;

/**
 * A class that represents double constants.
 * 
 * @author Margarita Tolja
 *
 */
public class ElementConstantDouble extends Element {
	
	private double value;
	
	/**
	 * Sets the double value.
	 * @param value
	 * 		Value of the double.
	 */
	public ElementConstantDouble( double value ) {
		this.value = value;
	}
	
	/**
	 * Returns String representation of double value.
	 */
	@Override
	public String asText() {
		return String.valueOf( value );
	}
	
	/**
	 * Value getter.
	 * @return
	 * 		The value of  double constant.
	 */
	public double getValue() {
		return value;
	}

}
