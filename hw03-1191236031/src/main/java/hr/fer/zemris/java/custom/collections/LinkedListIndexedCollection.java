package hr.fer.zemris.java.custom.collections;

import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;

/**
 * A class that represents some general collection of objects 
 * stored in double linked list with indexes.
 * 
 * @author Margarita Tolja
 *
 */
public class LinkedListIndexedCollection implements List {
	
	private ListNode first;
	private ListNode last;
	private int size;
	private long modificationCount;
	
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
	
	/**
	 * Creates an empty list collection.
	 */
	public LinkedListIndexedCollection() {
		first = null;
		last = null;
		size = 0;
		modificationCount = 0;
	}
	/**
	 * Creates a list with elements copied from another collection.
	 * @param other
	 * 		Other collection.
	 */
	public LinkedListIndexedCollection( Collection other ) {
		this();
		addAll( other );
	}

	/**
	 * Inner class that represents iterator for indexed linked list collection.
	 */
	private class ListElementsGetter implements ElementsGetter {
	//nestatička verzija
		
		private ListNode nextNode;
		private long savedModificationCount;
		
		/**
		 * Initialises new iterator that points to the first object in collection.
		 */
		public ListElementsGetter() {
			nextNode = first;
			savedModificationCount = modificationCount;
		}
		
		@Override
		public boolean hasNextElement() {
			if ( savedModificationCount != modificationCount ) {
				throw new ConcurrentModificationException( 
						"The list collection was modified while iterating." );
			}
			return nextNode != null;
		}
		@Override
		public Object getNextElement() {
			if ( savedModificationCount != modificationCount ) {
				throw new ConcurrentModificationException( 
						"The list collection was modified while iterating." );
			}
			if ( !hasNextElement() ) {
				throw new NoSuchElementException( "There are no elements left in list." );
			}
			ListNode previousNode = nextNode;
			nextNode = nextNode.next;
			return previousNode.value;
		}
	}
	
	@Override
	public ListElementsGetter createElementsGetter() {
		return new ListElementsGetter();
	}
	
	@Override
	public int size() {
		return size;
	}
	/**
	 * Adds the given object into this collection at the end of collection.
	 */
	@Override
	public void add( Object value ) {
		last = new ListNode( last, value, null );
		if ( first == null ) {
			first = last;
		}
		size++;
		modificationCount++;
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
	public void clear() {
		first = null;
		//last = null;
		size = 0;	
		modificationCount++;
	}
	@Override
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
	@Override
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
		modificationCount++;
	}
	@Override
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
	@Override
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
		modificationCount++;
	}
}
