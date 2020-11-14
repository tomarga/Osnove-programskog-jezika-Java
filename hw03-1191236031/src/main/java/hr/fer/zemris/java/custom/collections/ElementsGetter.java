package hr.fer.zemris.java.custom.collections;

/**
 * Interface of object used for iterating through collection.
 * 
 * @author Margarita Tolja
 *
 */
public interface ElementsGetter {
	
	/**
	 * Checks if there exists next element in the collection.
	 * Throws an exception if collection was modified meanwhile.
	 * @return
	 * 		<code>true</code> if there is next element;
	 * 		<code>false</code> otherwise.
	 */
	boolean hasNextElement();
	/**
	 * Gets the next element of collection.
	 * Throws an exception if there is no such element.
	 * Throws an exception if collection was modified meanwhile.
	 * @return
	 * 		The next object in collection.
	 */
	Object getNextElement();
	/**
	 * Processes all remaining elements of collection in a way
	 * specified by the processor in parameter.
	 * @param p
	 * 		Certain processor.
	 */
	default void processRemaining( Processor p ) {
		while( hasNextElement() ) {
			p.process( getNextElement() );
		}
	}
	
}