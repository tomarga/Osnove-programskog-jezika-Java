package hr.fer.zemris.java.gui.layouts;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * An implementation of layout manager primarily used for
 * calculator GUI. The layout manages up to 31 components
 * that form some version of a grid. The grid consists of 
 * 5 rows and 7 columns. All cells in the grid have equal 
 * dimensions, except for the one in upper left corner that
 * has the width of 5 'regular' cells. 
 * 
 * @author Margarita Tolja
 *
 */
public class CalcLayout implements java.awt.LayoutManager2 {

	private int gap;
    private Map<RCPosition, Component> components;
    
    /**
     * Default constructor.
     * Sets the gap between the components to 0.
     */
    public CalcLayout() {
    	this( 0 );
    }
    
    /**
     * Initialises a calculator layout whose components, if there are any,
     * are distanced from one another for specified gap.
     * @param gap
     */
    public CalcLayout( int gap ) {
    	this.gap = gap;
    	components = new HashMap<>();
    }
    
    /**
     * Parses the components coordinates ("row,column") from given string.
     * @param text
     * 			String to parse.
     * @return
     * 			Appropriate RCPosition object.
     * @throws IllegalArgumentException
     * 			If the argument's form is not as expected.
     */
    public static RCPosition parse( String text ) {
    	
    	String[] positionString = text.split( "," );
    	
    	//no comma or more than 2 values
    	if ( positionString.length != 2 ) {
    		throw new IllegalArgumentException( "Position of a component must contain only row and column number.");
    	}
    	
    	int row = 0;
    	int column = 0;
    	try {
    		row = Integer.parseInt( positionString[0] );
    		column = Integer.parseInt( positionString[1] );
    	} catch ( NumberFormatException e ) {
    		throw new IllegalArgumentException( "Row and column of a component must be positive integers" );
    	}
    	
    	return new RCPosition(row, column);
    }
    
    /**
     * @throws UnsupportedOperationException
     * 			If called.
     */
	@Override
	public void addLayoutComponent( String name, Component comp ) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Removes the component given as an argument from components in layout.
	 */
	@Override
	public void removeLayoutComponent( Component comp ) {

		for ( Map.Entry<RCPosition, Component> component : components.entrySet() ) {
			if ( component.getValue().equals( comp ) ) {
				components.remove( component.getKey() );
			}
		}
	}

	/**
	 * Returns the preferred layout size based on the preferred size
	 * of consisting components.
	 */
	@Override
	public Dimension preferredLayoutSize( Container parent ) {
	
		return layoutSize( new Function<Component, Dimension>() {

			@Override
			public Dimension apply( Component component ) {
				return component.getPreferredSize();
			}
		} );
	}

	/**
	 * Returns the minimum layout size based on the minimum size
	 * of consisting components.
	 */
	@Override
	public Dimension minimumLayoutSize( Container parent ) {
		
		return layoutSize( new Function<Component, Dimension>() {

			@Override
			public Dimension apply( Component component ) {
				return component.getMinimumSize();
			}
		} );
	}
	
	
	/**
	 * Returns the maximum layout size based on the maximum size
	 * of consisting components.
	 */
	@Override
	public Dimension maximumLayoutSize(Container target) {
		
		return layoutSize( new Function<Component, Dimension>() {

			@Override
			public Dimension apply( Component component ) {
				return component.getMaximumSize();
			}
		} );
	}
	
	/**
	 * Finds the maximum width and height (gotten with the size getter in argument)
	 * of all components of the layout. Then it calculates the appropriate
	 * width and height of the layout.
	 * @param sizeGetter
	 * 			Instance of Function interface.
	 * 			The 'apply' method gets the dimension (preferred, minimum or maximum) of a component.
	 * @return
	 * 			The suggested dimension of a layout.
	 * 			
	 */
	private Dimension layoutSize( Function<Component, Dimension> sizeGetter ) {
		
		int maxCellWidth = 0;
		int maxCellHeight = 0;

		for ( Map.Entry<RCPosition, Component> component : components.entrySet() ) {
			
			int componentWidth;
			int componentHeight;
			
			//special case (1,1)
			if ( component.getKey().equals( new RCPosition( 1, 1 ) ) ) {
				
				componentWidth = (int) Math.round( (double) ( sizeGetter.apply( component.getValue() ).getWidth() - 4 * gap ) / 5 );
				componentHeight = (int) sizeGetter.apply( component.getValue() ).getHeight();
			}
			//normal case
			else {
				componentWidth = sizeGetter.apply( component.getValue() ).width;
				componentHeight = sizeGetter.apply( component.getValue() ).height;
			}
			
			if ( componentWidth > maxCellWidth ) {
				maxCellWidth = componentWidth;
			}
			if ( componentHeight > maxCellHeight ) {
				maxCellHeight = componentHeight;
			}
		}
		
		int width = ( maxCellWidth * 7 ) + ( gap * 6 );
		int height = ( maxCellHeight * 5 ) + ( gap * 4 );
	
		return new Dimension( width, height ); 
	}
	

	/**
	 * Calculates the appropriate calculator cell's dimensions and sets
	 * the components' bounds accordingly.
	 */
	@Override
	public void layoutContainer( Container parent ) {
		
		Insets inset = parent.getInsets();
		
		int startX = parent.getBounds().x + inset.left;
		int startY = parent.getBounds().y + inset.right;
		double width = parent.getWidth() - inset.left - inset.right;
		double height = parent.getHeight() - inset.bottom - inset.top;
		
		//width and height without the gaps
		width -= 6 * gap;
		height -= 4 * gap;
		
		int cellWidth = (int) Math.round( width / 7 );
		int cellHeight = (int) Math.round( height / 5 );
		
		int[] cellWidths = new int[7];
		int[] cellHeights = new int[5];
		Arrays.fill( cellHeights, cellHeight );
		Arrays.fill( cellWidths, cellWidth );
		
		//check if overall dimension is within container
		if ( cellWidth * 7 > width ) {
			int difference = (int) ( 7 * cellWidth - width );
			for ( int i = 0; i < 7; i += 7 - difference ) {
				cellWidths[i]--;
			}
			if ( difference % 2 != 0 ) {
				cellWidths[6]--;
			}
		}
		if ( cellHeight * 5 > height ) {
			int difference = (int) ( 5 * cellHeight - height );
			for ( int i = 0; i < 5; i += 5 - difference ) {
				cellHeights[i]--;
			}
			if ( difference % 2 != 0 ) {
				cellHeights[4]--;
			}
		}
		
		//set bounds
		for ( Map.Entry<RCPosition, Component> component : components.entrySet() ) {
			
			int row = component.getKey().getRow() - 1;
			int column = component.getKey().getColumn() - 1;
			//special case (1,1)
			if ( row == 0 && column == 0 ) {
				int firstWidth = cellWidths[0] + cellWidths[1] + cellWidths[2] + cellWidths[3] + cellWidths[4] + gap * 4;
				component.getValue().setBounds( startX, startY, firstWidth, cellHeights[0] );
			}
			//normal case
			else {
				int cellX = startX;
				for ( int i = 0; i < column; i++ ) {
					cellX += gap + cellWidths[i];
				}
				int cellY = startY;
				for ( int i = 0; i < row; i++ ) {
					cellY += gap + cellHeights[i];
				}
				component.getValue().setBounds(cellX, cellY, cellWidths[column], cellHeights[row] );
			}
		}
		
	}

	/**
	 * Adds a new component to the (row,column) position specified in second argument.
	 * @throws NullPointerException
	 * 			If passed component or constraint is null.
	 * @throws IllegalArgumentException
	 * 			If passed constrained is not of type RCPosition or String.
	 * @throws CalcLayoutException
	 * 			If constraint is out of permitted interval. 
	 * 			If constraint is (r,s) where r<1 || r>5 or s<1 || s>7.
	 * 			If constraint is (1,s) where s>1 && s<6.
	 */
	@Override
	public void addLayoutComponent( Component comp, Object constraints ) {
		
		if ( comp == null || constraints == null ) {
			throw new NullPointerException( "New layout component or constraint cannot be null." );
		}
		if ( !( constraints instanceof String ) && !( constraints instanceof RCPosition ) ) {
			throw new IllegalArgumentException( "Constraints must be of type RCPosition or String." ); 
		}
		
		RCPosition newPosition = constraints instanceof String ? 
				parse( (String) constraints ) : (RCPosition) constraints;
		
		int row = newPosition.getRow();
		int column = newPosition.getColumn();
		
		//check if constraints are in valid intervals
		if ( row < 1 || row > 5 || column < 1 || column > 7 ) {
			throw new CalcLayoutException( "Valid rows are 1-5, and valid columns are 1-7." );
		}
		//check first row
		if ( row == 1 && ( column > 1 && column < 6 ) ) {
			throw new CalcLayoutException( "Valid columns in first row are: 1, 6 and 7." );
		}
		
		//check for component on the same position
		for ( RCPosition position : components.keySet() ) {
			if ( position.equals( newPosition ) ) {
				throw new CalcLayoutException( "The component at (" + row + "," + column + ") already exists." );
			}
		}
		
		components.put( newPosition, comp );
	}


	/**
	 * Returns 0.
	 */
	@Override
	public float getLayoutAlignmentX( Container target ) {
		return 0;
	}

	/**
	 * Returns 0.
	 */
	@Override 
	public float getLayoutAlignmentY( Container target ) {
		return 0;
	}

	/**
	 * Does nothing.
	 */
	@Override
	public void invalidateLayout(Container target) {
	}

}
