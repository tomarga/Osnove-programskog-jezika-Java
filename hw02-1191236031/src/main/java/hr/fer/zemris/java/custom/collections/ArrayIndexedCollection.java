package hr.fer.zemris.java.custom.collections;

import java.util.Arrays;

/**
 * A class that represents some general collection of objects 
 * stored in indexed array.
 * 
 * @author Margarita Tolja
 *
 */
public class ArrayIndexedCollection extends Collection {
	
	private int size;
	private Object[] elements;
	
	/**
	 * Creates an empty collection with the capacity of 16 objects.
	 */
	public ArrayIndexedCollection() {
		elements = new Object[16];
		size = 0;
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
	 * Returns number of elements stored in collection.
	 */
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
	@Override
	/**
	 * Adds given object into the collection.
	 * If the collection is full, its capacity is doubled before adding.
	 * Adding null element is not permitted.
	 */
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
	
	@Override
	public void forEach( Processor processor ) {
		for ( int i = 0; i < size; i++ ) {
			processor.process( elements[i] );
		}
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
	/**
	 * Method returns the object at specific index in collection.
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
		return elements[index];
	}
	/**
	 * Inserts the given object at the given position in array. 
	 * All objects at indexes greater than or equal to given index
	 * are shifted one place to their 'right'.
	 * If the collection is full, its capacity is doubled beforehand.
	 * The legal positions are 0 to size.
	 *  
	 * @param value
	 * 		Object to be added.
	 * @param position
	 * 		Position of the added object.
	 */
	public void insert( Object value, int position ) {
		if ( value == null ) {
			throw new NullPointerException( "insert" );
		}
		if ( position < 0 || position > size ) {
			throw new IndexOutOfBoundsException();
		}		
		//array is full
		if ( capacity() == size() ) {
			elements = Arrays.copyOf( elements, 2 * size );
		}
		//make 'space' for new object	
		for ( int i = size; i > position; i-- ) {
				elements[i] = elements[i - 1];
		}

		elements[position] = value;
		size++;
	}
	/**
	 * Method finds the position of given object in array.
	 * @param value
	 * 		Object whose position is searched.
	 * @return
	 * 		Index if the object is found;
	 * 		-1 otherwise.
	 */
	public int indexOf( Object value ) {
		for ( int i = 0; i < size; i++ ) {
			if ( elements[i].equals( value ) ) {
				return i;
			}
		}
		return -1;
	}
	/**
	 * Removes the object at specific position in array. 
	 * Objects that were previously on indexes greater than the
	 * removed element are shifted one place to their 'left'.
	 * Legal indexes are 0 to size-1 .
	 * @param index
	 * 		index of Object to be removed.
	 */
	public void remove( int index ) {
		if ( index < 0 || index > size - 1 ) {
			throw new IndexOutOfBoundsException();
		}
		elements[index] = null;
		//fill the empty 'space'
		for ( int i = index; i < size - 1; i++ ) {
			elements[i] = elements[i + 1];
		}
		size--;
	}

}
