package hr.fer.zemris.java.custom.collections;

/**
 * Interface of indexed collections.
 * 
 * @author Margarita Tolja
 *
 */
public interface List<T> extends Collection<T> {
	
	/**
	 * 
	 * Method returns the object at specific index in collection.
	 * Given index has to be contained in interval [0, size-1].
	 * @param index
	 * 		Index of an object.
	 * @return
	 * 		Object at that index.
	 */
	T get(int index);
	/**
	 * Inserts the given object at the given position in collection. 
	 * All objects at indexes greater than or equal to given index
	 * are shifted one place to their 'right'.
	 * The legal positions are 0 to size.
	 * Null value cannot be inserted.
	 * @param value
	 * 		Object to be inserted.
	 * @param position
	 * 		Position of the inserted object.
	 */
	void insert(T value, int position);
	/**
	 * Method finds the position of given object in its collection.
	 * @param value
	 * 		Object whose position is searched.
	 * @return
	 * 		Index if the object is found;
	 * 		-1 otherwise.
	 */
	int indexOf(Object value);
	/**
	 * Removes the object at specific position in collection. 
	 * Objects that were previously on indexes greater than the
	 * removed element are shifted one place to their 'left'.
	 * Legal indexes are 0 to size-1.
	 * @param index
	 * 		index of Object to be removed.
	 */
	void remove(int index);
	
}
