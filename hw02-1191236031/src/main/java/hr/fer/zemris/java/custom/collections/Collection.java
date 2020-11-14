package hr.fer.zemris.java.custom.collections;

/**
 * Class represents some abstract collection of objects.
 * It doesn't have any storage capacity.
 * 
 * @author Margarita Tolja
 *
 */
public class Collection {
	
	/**
	 * Method should create an empty collection.
	 * Here it does nothing.
	 */
	protected Collection() {
	}
	/**
	 * Method checks if collection is empty.
	 * @return
	 * 		<code>true<code> if collection is empty;
	 * 		<code>false<code> otherwise.
	 */
	public boolean isEmpty() {
		return size() == 0;
	}
	/**
	 * Method returns size of a collection.
	 * Here it returns zero.
	 * @return 
	 * 		The size of collection.
	 */
	public int size() {
		return 0;
	}
	/**
	 * Method should add passed object to collection.
	 * Here it does nothing.
	 * @param value
	 * 		Object to be added in collection's storage.
	 */
	public void add(Object value) {
	}
	/**
	 * Method should check if collection contains passed object.
	 * Here it returns false.
	 * @param value
	 * 		Object of interest. 	
	 * @return
	 * 		<code>true<code> if collection contains the object;
	 * 		<code>false<code> otherwise.
	 */
	public boolean contains(Object value) {
		return false;
	}
	/**
	 * Method should try to remove an occurrence of passed object from collection 
	 * and return true if successful.
	 * Here it only returns false.
	 * @param value
	 * 		Object to be removed.
	 * @return
	 * 		<code>true<code> if the object is removed;
	 * 		<code>false<code> otherwise.
	 */
	public boolean remove(Object value) {
		return false;
	}
	/**
	 * Method should return array with the copy of all objects stored in collection. 
	 * Here it throws an exception.
	 * @throws UnsupportedOperationException 
	 * @return 
	 * 		Array of objects in collection.
	 */
	public Object[] toArray() {
		if ( isEmpty() ) {
			throw ( new UnsupportedOperationException() );
		}
		Object[] result = new Object[size()];
		return result;
	}
	/**
	 * Method should call process method from processor passed as
	 * parameter onto all objects of collection.
	 * Here it does nothing.
	 * @param processor
	 * 		Object that specifies how to process each element.
	 */
	public void forEach(Processor processor) {		
	}
	/**
	 * Method adds all objects from collection in argument to
	 * collection.
	 * @param other
	 * 		Other collection whose objects are added.
	 */
	public void addAll(final Collection other) {
		/**
		 * Inner class specified for adding objects to collection.
		 */
		class AddProcessor extends Processor{
			/**
			 * Adds object in argument to container.
			 */
			@Override
			public void process(Object value) {
				add( value );
			}
		}		
		AddProcessor addProcessor = new AddProcessor();		
		other.forEach( addProcessor );
		
	}
	/**
	 * Method should clear the collection from all objects.
	 * Here it does nothing.
	 */
	public void clear() {
	}
}
