package hr.fer.zemris.java.custom.scripting.nodes;

/**
 * A node representing a piece of textual data.
 * 
 * @author Margarita Tolja
 *
 */
public class TextNode extends Node {
	
	private String text;
	
	/**
	 * Sets the text value of a node.
	 * @param text
	 * 		Value of the node.
	 */
	public TextNode( String text ) {
		this.text = text;
	}
	
	/**
	 * Text getter.
	 * @return
	 * 		Text of a node.
	 */
	public String getText() {
		return text;
	}
	
	@Override
	public String toString() {
		return text;
	}

}
