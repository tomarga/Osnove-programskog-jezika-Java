package hr.fer.zemris.java.custom.collections;

/**
 * Interface of some abstract collection of objects.
 * It doesn't have any storage capacity.
 * 
 * @author Margarita Tolja
 *
 */

public interface Collection<T> {
	
	/**
	 * Creates a collection's iterator.
	 * @return
	 * 		Collection's iterator.
	 */
	ElementsGetter<T> createElementsGetter();
	/**
	 * Method checks if collection is empty.
	 * @return	
	 * 		<code>true<code> if collection is empty;
	 * 		<code>false<code> otherwise.
	 */
	default boolean isEmpty() {
		return size() == 0;
	}
	/**
	 * Method returns size of the collection.
	 * @return	
	 * 		The size of collection.
	 */
	int size();
	/**
	 * Adds passed object to collection.
	 * It is illegal to add null element.
	 * @param value	 
	 * 		Object to be added in collection's storage.
	 */
	void add(T value);
	/**
	 * Checks if collection contains passed object.
	 * @param value
	 * 		Object of interest. 	
	 * @return
	 * 		<code>true<code> if collection contains the object;
	 * 		<code>false<code> otherwise.
	 */
	boolean contains(Object value);
	/**
	 * Tries to remove an occurrence of passed object from collection 
	 * and returns true if successful.
	 * @param value
	 * 		Object to be removed.
	 * @return
	 * 		<code>true<code> if the object is removed;
	 * 		<code>false<code> otherwise.
	 */
	boolean remove(Object value);
	/**
	 * Returns array with the copy of all objects stored in collection. 
	 * @return 
	 * 		Array of objects in collection.
	 */
	Object[] toArray();
	/**
	 * Processes all objects from collection in a way
	 * specified by processor object in parameter.
	 * @param processor
	 * 		Object that specifies how to process each element.
	 */
	default void forEach( Processor<? super T> processor ) {
		ElementsGetter<T> iterator = createElementsGetter();
		while( iterator.hasNextElement() ) {
			processor.process( iterator.getNextElement() );
		}
	}
	/**
	 * Method adds all objects from collection in argument to
	 * collection.
	 * @param other
	 * 		Other collection whose objects are added.
	 */
	default void addAll(final Collection<? extends T> other) {
		Processor<T> addProcessor = this::add;
		other.forEach( addProcessor );		
	}
	/**
	 * Adds all elements from passed collection that are acceptable
	 * by the means of tester in argument.
	 * @param col
	 * 		Collection whose elements are tested and maybe added.
	 * @param tester
	 * 		Tester that specifies the test. 		
	 */
	default void addAllSatisfying(Collection<? extends T> col, Tester tester) {
		ElementsGetter<? extends T> iterator = col.createElementsGetter();
		while( iterator.hasNextElement() ) {
			T currentElement = iterator.getNextElement();
			if ( tester.test( currentElement ) ) {
				add( currentElement );
			}
		}
	}
	/**
	 * Clears the collection from all objects.
	 */
	void clear();
}
