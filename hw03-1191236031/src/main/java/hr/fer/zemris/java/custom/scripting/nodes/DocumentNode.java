package hr.fer.zemris.java.custom.scripting.nodes;

/**
 * A node representing an entire document.
 * 
 * @author Margarita Tolja
 *
 */
public class DocumentNode extends Node {

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		for ( int i = 0; i < numberOfChildren(); i++ ) {
			Node child = getChild( i );
			sb.append( child.toString() );
		}		
		return sb.toString();
	}
	@Override
	public boolean equals( Node other ) {
		if ( !this.getClass().equals( other.getClass() ) ) {
			return false;
		}
		if ( numberOfChildren() != other.numberOfChildren() ) {
			return false;
		}
		for ( int i = 0; i < numberOfChildren(); i++ ) {
			Node child1 = getChild( i );
			Node child2 = other.getChild( i );
			if ( !child1.equals( child2 ) ) {
				return false;
			}
		}
		return true;
	}
}
