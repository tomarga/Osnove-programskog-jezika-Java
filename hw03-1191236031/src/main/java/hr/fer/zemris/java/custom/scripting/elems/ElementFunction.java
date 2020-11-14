package hr.fer.zemris.java.custom.scripting.elems;

/**
 * A class that represents function elements.
 * 
 * @author Margarita Tolja
 *
 */
public class ElementFunction extends Element {
	
	private String name;
	
	/**
	 * Sets the name of function.
	 * @param name
	 * 		Name of the function.
	 */
	public ElementFunction( String name ) {
		this.name = name;
	}
	
	/**
	 * Returns the name of function.
	 */
	@Override
	public String asText() {
		return name;
	}
	
	/**
	 * Name getter.
	 * @return
	 * 		The name of function.
	 */
	public String getName() {
		return name;
	}

}
