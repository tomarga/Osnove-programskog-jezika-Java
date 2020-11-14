package hr.fer.zemris.java.custom.scripting.elems;

/**
 * A class that represents operators.
 * 
 * @author Margarita Tolja
 *
 */
public class ElementOperator extends Element {
	
	private String symbol;
	
	/**
	 * Sets the operator's symbol.
	 * @param symbol
	 * 		Symbol of the operator.
	 */
	public ElementOperator( String symbol ) {
		this.symbol = symbol;
	}
	
	/**
	 * Returns the represented symbol.
	 */
	@Override
	public String asText() {
		return symbol;
	}
	
	/**
	 * Symbol getter.
	 * @return
	 * 		The symbol.
	 */
	public String getSymbol() {
		return symbol;
	}

}
