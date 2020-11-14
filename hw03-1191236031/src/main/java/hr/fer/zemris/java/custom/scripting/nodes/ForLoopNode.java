package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementVariable;

/**
 * A node representing a single for-loop construct.
 * 
 * @author Margarita Tolja
 *
 */
public class ForLoopNode extends Node {
	
	private ElementVariable variable;
	private Element startExpression;
	private Element endExpression;
	private Element stepExpression;
	
	/**
	 * Initialises elements of for loop. 
	 * @param variable
	 * 		For loop variable.
	 * @param startElement
	 * 		For loop start expression.
	 * @param endElement
	 * 		For loop end expression.
	 * @param stepElement
	 * 		For loop step.
	 */
	public  ForLoopNode( ElementVariable variable, Element startElement, Element endElement, Element stepElement ) {
		this.variable = variable;
		startExpression = startElement;
		endExpression = endElement;
		stepExpression = stepElement;
	}
	
	/**
	 * Variable getter.
	 * @return
	 * 		The variable.
	 */
	public ElementVariable getVariable() {
		return variable;
	}
	
	/**
	 * StartExpression getter.
	 * @return
	 * 		The startExpression.
	 */
	public Element getStartExpression() {
		return startExpression;
	}
	
	/**
	 * EndExpression getter.
	 * @return
	 * 		The endExpression.
	 */
	public Element getEndExpression() {
		return endExpression;
	}
	
	/**
	 * StepExpression getter.
	 * @return
	 * 		The stepExpression.
	 */
	public Element getStepExpression() {
		return stepExpression;
	}

	@Override
	public String toString() {
		
		StringBuilder sb = new StringBuilder();
		sb.append( "{$ FOR " );
		
		sb.append( variable.asText() + " " );
		sb.append( startExpression.asText() + " ");
		sb.append( endExpression.asText() + " " );
		if ( stepExpression != null ) {
			sb.append( stepExpression.asText() + " ");
		}
		sb.append( "$}" );
		
		for ( int i = 0; i < numberOfChildren(); i++ ) {
			Node child = getChild( i );
			sb.append( child.toString() );
		}
		
		sb.append( "{$ END $}" );
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
