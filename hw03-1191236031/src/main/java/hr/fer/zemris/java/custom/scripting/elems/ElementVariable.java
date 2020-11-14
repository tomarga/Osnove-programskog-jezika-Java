package hr.fer.zemris.java.custom.scripting.elems;

/**
 * A class that represents variables.
 * 
 * @author Margarita Tolja
 *
 */
public class ElementVariable extends Element {
	
	private String name;

	/**
	 * Sets the name of a variable.
	 * @param name
	 * 		Name of the variable.
	 */
	public ElementVariable( String name ) {
		this.name = name;
	}
	
	/**
	 * Returns the name of a variable.
	 */
	@Override
	public String asText() {
		return name;
	}
	
	/**
	 * Variable name getter.
	 * @return
	 * 		The name of a variable.
	 */
	public String getName() {
		return name;
	}
}
