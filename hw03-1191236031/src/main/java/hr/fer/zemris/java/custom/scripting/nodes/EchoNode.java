package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.scripting.elems.Element;

/**
 * A node representing a command which generates some textual output dynamically.
 * 
 * @author Margarita Tolja
 *
 */
public class EchoNode extends Node {
	
	private Element[] elements;

	/**
	 * Initialises the array of elements.
	 * @param elements
	 * 		Initialisation array.
	 */
	public EchoNode( Element[] elements ) {
		this.elements = elements;
	}
	/**
	 * Elements getter.
	 * @return
	 * 		Array of elements.
	 */
	public Element[] getElements() {
		return elements;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append( "{$= " );
		
		for ( Element element : elements ) {
			sb.append( element.asText() + " ");
		}
		
		sb.append( "$}" );
		
		return sb.toString();
	}

}
