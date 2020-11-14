package hr.fer.zemris.java.custom.scripting.elems;

/**
 * A class that represents constant integers.
 * 
 * @author Margarita Tolja
 *
 */
public class ElementConstantInteger extends Element {
	
	private int value;
	
	
	/**
	 * Sets the integer value.
	 * @param value
	 * 		Value of the integer.
	 */
	public ElementConstantInteger( int value ) {
		this.value = value;
	}
	
	/**
	 * Returns string representation of constant's value.
	 */
	@Override
	public String asText() {
		return String.valueOf( value );
	}
	
	/**
	 * Value getter.
	 * @return
	 * 		Value of integer constant.
	 */
	public int getValue() {
		return value;
	}

}
