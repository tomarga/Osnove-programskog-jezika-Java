package hr.fer.zemris.java.custom.collections;

import java.util.Arrays;

/**
 * A class that represents some general collection of objects 
 * stored in double linked list with indexes.
 * 
 * @author Margarita Tolja
 *
 */
public class LinkedListIndexedCollection extends Collection {
	
	/**
	 * Inner class that represents nodes of which the list is constructed.
	 */
	private static class ListNode {
		
		private ListNode previous;
		private ListNode next;
		private Object value;
		
		/**
		 * Creates a list node with given value and connects it 
		 * to previous and next list node.
		 * @param previous
		 * 		Previous list node.
		 * @param value
		 * 		Value of list node.
		 * @param next
		 * 		Next list node.
		 */
		public ListNode( ListNode previous, Object value, ListNode next ) {
			this.value = value;
			this.previous = previous;
			this.next = next;
			if ( previous != null ) {
				previous.next = this;
			}
			if ( next != null ) {
				next.previous = this;
			}			
		}
	}
	
	private ListNode first;
	private ListNode last;
	private int size;
	
	/**
	 * Creates an empty list collection.
	 */
	public LinkedListIndexedCollection() {
		first = null;
		last = null;
		size = 0;
	}
	/**
	 * Creates a list with elements copied form another collection.
	 * @param other
	 * 		Other collection.
	 */
	public LinkedListIndexedCollection( Collection other ) {
		this();
		addAll( other );
	}
	
	@Override
	public int size() {
		return size;
	}
	/**
	 * Adds the given object into this collection at the end of collection.
	 * It is illegal to add null element.
	 */
	@Override
	public void add( Object value ) {
		last = new ListNode( last, value, null );
		if ( first == null ) {
			first = last;
		}
		size++;
	}
	@Override
	public boolean contains( Object value ) {
		return indexOf( value ) != -1;
	}
	@Override
	public boolean remove( Object value ) {
		if ( !contains( value ) ) {
			return false;
		}
		remove( indexOf( value ) );
		return true;
	}
	@Override
	public Object[] toArray() {
		
		Object[] array = new Object[size];
		for ( int i = 0; i < size; i++ ) {
			array[i] = get( i );
		}
		return Arrays.copyOf( array, size );
	}
	@Override
	public void forEach( Processor processor ) {
		for ( int i = 0; i < size; i++ ) {
			processor.process( get(i) );
		}
	}
	@Override
	public void clear() {
		first = null;
		//last = null;
		size = 0;		
	}
	/**
	 * Method returns the object at specific index in list collection.
	 * Given index has to be contained in interval [0, size-1].
	 * @param index
	 * 		Index of an object.
	 * @return
	 * 		Object at that index.
	 */
	public Object get( int index ) {
		if ( index < 0 || index > size - 1 ) {
			throw new IndexOutOfBoundsException();
		}
		
		if ( index <= size / 2 ) {
			int i = 0;
			ListNode iterator = first;
			while( i != index ) {
				iterator = iterator.next;
				i++;
			}
			return iterator.value;
		}
		else {
			int i = size - 1;
			ListNode iterator = last;
			while( i != index ) {
				iterator = iterator.previous;
				i--;
			}
			return iterator.value;
		}
	}
	/**
	 * Inserts the given object at the given position in list. 
	 * All objects at indexes greater than or equal to given index
	 * are shifted one place to their 'right'.
	 * The legal positions are 0 to size.
	 *  
	 * @param value
	 * 		Object to be added.
	 * @param position
	 * 		Position of the added object.
	 */
	public void insert( Object value, int index ) {
		if ( value == null ) {
			throw new NullPointerException();
		}
		if ( index < 0 || index > size ) {
			throw new IndexOutOfBoundsException();
		}

		int i = 0;
		ListNode iterator = first;
		while( i < index - 1 ) {
			iterator = iterator.next;
			i++;
		}
		ListNode newPrevious = index == 0 ? null : iterator;
		ListNode newNext = index == 0 ? first : iterator.next;
		ListNode newNode = new ListNode( newPrevious, value, newNext );
		
		if ( index == 0 ) {
			first = newNode;
		}
		if ( index == size ) {
			last = newNode;
		}
		size++;
	}
	/**
	 * Method finds the position of given object in list.
	 * @param value
	 * 		Object whose position is searched.
	 * @return
	 * 		Index if the object is found;
	 * 		-1 otherwise.
	 */
	public int indexOf( Object value ) {
		int i = 0;
		ListNode iterator = first;
		while( i < size ) {
			if ( iterator.value.equals( value ) ) {
				return i;
			}
			iterator = iterator.next;
			i++;			
		}
		return -1;
	}
	/**
	 * Removes the object at specific position in list. 
	 * Objects that were previously on indexes greater than the
	 * removed element are shifted one place to their 'left'.
	 * Legal indexes are 0 to size-1 .
	 * @param index
	 * 		index of Object to be removed.
	 */
	public void remove( int index ) {
		if ( index < 0 || index > size - 1 ) {
			throw new IndexOutOfBoundsException( "Tried to remove list node whose " 
					+ "index " + index + " is not in [0,size]. Size = " + size + "." );
		}
	
		int i = 0;
		ListNode iterator = first;
		
		if ( index == 0 ) {
			first = first.next;
		}
		if ( index == size - 1 ) {
			last = last.previous;
		}
		if ( index > 0 && index < size - 1 ) {
			while( i < index ) {
				iterator = iterator.next;
				i++;
			}
			iterator.previous.next = iterator.next;
			iterator.next.previous = iterator.previous;
		}
		size--;
	}
}
