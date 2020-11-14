package hr.fer.zemris.java.custom.collections;

import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;

/**
 * A class that represents some general collection of objects 
 * stored in indexed array.
 * 
 * @author Margarita Tolja
 *
 */
public class ArrayIndexedCollection implements List {
	
	private int size;
	private Object[] elements;
	private long modificationCount;
	
	/**
	 * Creates an empty collection with the capacity of 16 objects.
	 */
	public ArrayIndexedCollection() {
		elements = new Object[16];
		size = 0;
		modificationCount = 0;
	}
	/**
	 * Creates an empty collection with specified capacity.
	 * @param initialCapacity
	 * 		Capacity of the collection.
	 */
	public ArrayIndexedCollection( int initialCapacity ) {
		if ( initialCapacity < 1 ) {
			throw new IllegalArgumentException( 
					"Array capacity has to be at least 1." );
		}
		elements = new Object[initialCapacity];
		size = 0;
		modificationCount = 0;
	}
	/**
	 * Creates collection with all objects from another collection.
	 * @param other
	 * 		Other collection.
	 */
	public ArrayIndexedCollection( Collection other ) {
		this( other.size() );
		addAll( other );		
	}
	/**
	 * Creates collection with all objects from another collection
	 * and with specified capacity.
	 * If given capacity is less than other collection's size, new 
	 * collection will have the same size as the other.
	 * @param other
	 * 		Other collection.
	 * @param initialCapacity
	 * 		Initial capacity.
	 */
	public ArrayIndexedCollection( Collection other, int initialCapacity ) {		
		this( initialCapacity );
		if ( other.size() > initialCapacity ) {
			elements = new Object[initialCapacity];
			size = 0;
		}
		addAll( other );
	}


	/**
	 * Inner class that represents iterator for indexed array collection.
	 */
	private class ArrayElementsGetter implements ElementsGetter {
	//nestatička verzija	
		
		private int nextIndex;
		private long savedModificationCount;
				
		/**
		 * Initialises new iterator that points to the first object in collection.
		 */
		public ArrayElementsGetter() {
			nextIndex = 0;
			savedModificationCount = modificationCount;

		}
		@Override
		public boolean hasNextElement() {
			if ( savedModificationCount != modificationCount ) {
				throw new ConcurrentModificationException( 
						"The array collection was modified while iterating." );
			}
			return nextIndex < size;
		}
		@Override
		public Object getNextElement() {
			if ( savedModificationCount != modificationCount ) {
				throw new ConcurrentModificationException( 
						"The array collection was modified while iterating." );
			}
			if ( !hasNextElement() ) {
				throw new NoSuchElementException( "There are no elements left in array." );
			}
			return elements[nextIndex++];			
		}		
	}
	
	@Override
	public ArrayElementsGetter createElementsGetter() {
		return new ArrayElementsGetter();
	}

	@Override
	public int size() {
		return size;
	}
	/**
	 * Returns the capacity of a collection.
	 * @return
	 * 		Capacity.
	 */
	public int capacity() {
		return elements.length;
	}	
	/**
	 * If the collection is full, its capacity is doubled before adding.
	 */
	@Override
	public void add( Object value ) {
		insert( value, size );
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
		return Arrays.copyOf( elements, size );
	}
	
	/**
	 * The collection is left at current capacity.
	 */
	@Override
	public void clear() {
		for ( int i = size - 1; i >= 0; i-- ) {
			remove( i );
		}
	}
	@Override
	public Object get( int index ) {
		if ( index < 0 || index > size - 1 ) {
			throw new IndexOutOfBoundsException();
		}
		return elements[index];
	}
	/**
	 * If the collection is full, its capacity is doubled beforehand.
	 */
	@Override
	public void insert( Object value, int position ) {
		if ( value == null ) {
			throw new NullPointerException( "insert" );
		}
		if ( position < 0 || position > size ) {
			throw new IndexOutOfBoundsException();
		}		
		//array is full
		if ( capacity() == size() ) {
			//reallocation
			elements = Arrays.copyOf( elements, 2 * size );
			modificationCount++;
		}
		//make 'space' for new object	
		for ( int i = size; i > position; i-- ) {
				elements[i] = elements[i - 1];
		}
		//update modificationCount
		if ( position != size ) {
			modificationCount++;
		}
		elements[position] = value;
		size++;
		
	}
	@Override
	public int indexOf( Object value ) {
		for ( int i = 0; i < size; i++ ) {
			if ( elements[i].equals( value ) ) {
				return i;
			}
		}
		return -1;
	}
	@Override
	public void remove( int index ) {
		if ( index < 0 || index > size - 1 ) {
			throw new IndexOutOfBoundsException();
		}
		elements[index] = null;
		//fill the empty 'space'
		for ( int i = index; i < size - 1; i++ ) {
			elements[i] = elements[i + 1];
		}
		//update modificationCount
		if ( index != size ) {
			modificationCount++;
		}
		size--;
	}

}
