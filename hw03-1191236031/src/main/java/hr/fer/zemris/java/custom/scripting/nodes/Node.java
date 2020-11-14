package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.collections.ArrayIndexedCollection;

/**
 * A class used for representation of structured documents.
 * The base class for all graph nodes.
 * 
 * @author Margarita Tolja
 *
 */
public class Node {
	
	private ArrayIndexedCollection children;
	
	/**
	 * Constructor. 
	 */
	public Node() {
		children = null;
	}
	
	/**
	 * Adds given child to an internally managed collection of children.
	 * When called for the first time, creates the collection of children. 
	 * @param child
	 * 		Node added as a child.
	 */
	public void addChildNode( Node child ) {
		
		//first child
		if ( children == null ) {
			children = new ArrayIndexedCollection();
		}
		children.add( child );
		
	}
	
	/**
	 * Returns the number of direct children.
	 * @return
	 * 		The number of direct children.
	 */
	public int numberOfChildren() {
		//no children
		if ( children == null )
			return 0;
		return children.size();
	}
	
	/**
	 * Gets selected child.
	 * @throws IndexOutOfBoundsException
	 * 		If child with specified index doesn't exist.
	 * @param index
	 * 		Index of returned child in collection of children.
	 * @return
	 * 		Child node. 		
	 */
	public Node getChild( int index ) {
		if ( numberOfChildren() == 0 ) {
			throw new IndexOutOfBoundsException( "The node doesn't have any subnodes." );
		}
		return (Node)children.get( index );
	}
	
	/**
	 * Checks if nodes are structurally equal.
	 * @param other
	 * 		Some other node.
	 * @return
	 * 		true if they are equal;
	 * 		false otherwise.
	 */
	public boolean equals( Node other ) {
		if ( this.getClass().equals( other.getClass() )) {
			return true;
		}
		return false;
	}

}
