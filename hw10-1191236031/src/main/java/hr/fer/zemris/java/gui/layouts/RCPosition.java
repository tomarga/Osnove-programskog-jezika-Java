package hr.fer.zemris.java.gui.layouts;

/**
 * Utility function representing the 'coordinates'
 * of one component in calculator layout.
 */
public class RCPosition {
	
	private int row;
	private int column;
	
	/**
	 * Initialises new position of some component.
	 * @param row	
	 * 			Ordinal number of row.
	 * @param column
	 * 			Ordinal number of column.
	 */
	public RCPosition( int row, int column ) {
		this.row = row;
		this.column = column;
	}

	/**
	 * @return the row
	 */
	public int getRow() {
		return row;
	}

	/**
	 * @return the column
	 */
	public int getColumn() {
		return column;
	}
	
	/**
	 * The positions are considered equal if they contain
	 * the same row and column.
	 */
	@Override
	public boolean equals( Object other ) {
		if ( !( other instanceof RCPosition  ) ) {
			return false;
		}
		RCPosition otherPosition = ( RCPosition )other;
		return ( row == otherPosition.row && column == otherPosition.column );
	}
	
//	@Override
//	public String toString() {
//		return new String( "(" + row + "," + column + ")");
//	}
	
}