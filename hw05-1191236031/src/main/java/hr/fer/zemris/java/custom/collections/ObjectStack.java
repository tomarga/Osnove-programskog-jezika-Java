package hr.fer.zemris.java.custom.collections;

/**
 * A Class that represents a stack-like collection of some elements.
 * 
 * @author Margarita Tolja
 *
 */
public class ObjectStack<T> {
	
	private ArrayIndexedCollection<T> storage;
	
	/**
	 * Creates an empty stack collection.
	 */
	public ObjectStack() {
		storage = new ArrayIndexedCollection<T>();
	}
	/**
	 * Checks whether the stack collection is empty.
	 * @return
	 * 		<code>true</code> if the stack is empty;
	 * 		<code>false</code> otherwise.
	 */
	public boolean isEmpty() {
		return storage.isEmpty();
	}
	/**
	 * Gets the size of the stack collection.
	 * @return
	 * 		Size.
	 */
	public int size() {
		return storage.size();
	}
	/**
	 * Pushes(adds) given value on stack.
	 * Null element is not allowed to be pushed on stack.
	 * @param value
	 * 		Object to be pushed.
	 */
	public void push( T value ) {
		storage.add( value );
	}
	/**
	 * Pops(removes) last value pushed on stack and returns it.
	 * Throws exception if there is no element to pop.
	 * @return
	 * 		The removed element.
	 */
	public T pop() {
		if ( isEmpty() ) {
			throw new EmptyStackException( "Cannot pop value from an empty stack." );
		}
		T topObject = storage.get( size() - 1 );
		storage.remove( size() - 1 );

		return topObject;
	}
	/**
	 * Gets the last value pushed on stack.
	 * Throws an exception if stack is empty.
	 * @return
	 */
	public T peek() {
		if ( isEmpty() ) {
			throw new EmptyStackException( "Cannot pop value from an empty stack." );
		}
		return storage.get( size() - 1 );
	}
	/**
	 * Removes all elements from stack.
	 */
	public void clear() {
		storage.clear();
	}
}
