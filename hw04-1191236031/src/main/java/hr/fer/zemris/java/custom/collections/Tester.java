package hr.fer.zemris.java.custom.collections;

/**
 * Interface of objects that tests if some object is acceptable or not.
 * 
 * @author Margarita Tolja
 *
 */
public interface Tester {
	/**
	 * Tests if some object is acceptable or not.
	 * @param obj
	 * 		Object submitted for testing.
	 * @return
	 * 		<code>true</code> if passed object is acceptable;
	 * 		<code>false</code> if it's not.
	 */
	boolean test( Object obj );
}
