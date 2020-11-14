package hr.fer.zemris.java.custom.scripting.elems;

/**
 * A class that represents String elements.
 * 
 * @author Margarita Tolja
 *
 */
public class ElementString extends Element {
	
	private String value;
	
	/**
	 * Sets the value of a string element.
	 * @param value
	 * 		The string value.
	 */
	public ElementString( String value ) {
		this.value = value;
	}
	
	/**
	 * Returns represented String element.
	 */
	@Override
	public String asText() {
		return value;
	}
	
	/**
	 * Value getter.
	 * @return
	 * 		String value.
	 */
	public String getValue() {
		return value;
	}
}
