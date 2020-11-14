package hr.fer.zemris.java.custom.collections;

/**
 * Interface of an class instance capable 
 * of performing some operation on the passed object.
 * 
 * @author Margarita Tolja
 *
 */
@FunctionalInterface
public interface Processor<T> {
	/**
	 * Processes given object in some way.
	 * @param value 
	 * 		Object passed for processing. 		
	 */
	void process( T value );
}
