package hr.fer.zemris.java.custom.collections;

/**
 * Class represents a simple dictionary storage.
 * Every key is unique and bonded with specific value.
 * 
 * @author Margarita Tolja
 *
 * @param <K>
 * 		The type of keys.
 * @param <V>
 * 		The type of values.
 */
public class Dictionary<K,V> {

	private ArrayIndexedCollection<Pair> pairs;
	
	/**
	 * Inner class representing a key-value pair.
	 *
	 */
	private class Pair {
		
		private Object key;
		private Object value;
		
		/**
		 * Creates a pair with specific key and value.
		 * @throws NullPointerException if null is passed as a key value.
		 * @param key
		 * 		Key of a pair.
		 * @param value
		 * 		Value of a pair.
		 */
		public Pair( K key, V value ) {
			if ( key == null ) {
				throw new NullPointerException( "Key value cannot be null." );
			}
			this.key = key;
			this.value = value;
		}
		
		/**
		 * Value getter.
		 * @return
		 * 		Value of a pair.
		 */
		@SuppressWarnings("unchecked")
		public V getValue() {
			return (V) value;
		}
		
		/**
		 * The pairs are considered equal if their keys are equal.
		 */
		@Override
		public boolean equals( Object other ) {
			@SuppressWarnings("unchecked")
			Pair otherPair = (Pair) other;
			return key.equals( otherPair.key );
		}
	}
	
	/**
	 * Default constructor.
	 * Creates an empty dictionary.
	 */
	public Dictionary() {
		pairs = new ArrayIndexedCollection<Dictionary<K,V>.Pair>();
	}
	
	/**
	 * Checks if the dictionary is empty.
	 * @return
	 * 		<code>true</code> if it is empty;
	 * 		<code>false</code> otherwise.
	 */
	public boolean isEmpty() {
		return size() == 0;
	}
	
	/**
	 * Returns the number of pairs in dictionary.
	 * @return
	 * 		Dictionary's size.
	 */
	public int size() {
		return pairs.size();
	}
	
	/**
	 * Returns the value of the pair with the key passed as argument.
	 * If there is no such pair, null value is returned.
	 * @param key
	 * 		The key of a pair.
	 * @return
	 * 		Value of appropriate pair;
	 * 		null if there is no pair with given key.
	 */
	public V get( Object key ) {
		
		@SuppressWarnings("unchecked")
		int index = pairs.indexOf( new Pair( (K) key, null ) );
		
		if( index != -1 ) {
			return pairs.get( index ).getValue();
		}
		
		return null;		
	}
	
	/**
	 * Adds a new pair to dictionary with key and value passed as arguments.
	 * If dictionary already contains a pair with the same key, method overwrites it.
	 * @param key
	 * 		Key of a new pair.
	 * @param value
	 * 		Value of a new pair.
	 */
	public void put( K key, V value ) {
		Pair newPair = new Pair( key, value );
		pairs.remove( newPair );		
		pairs.add( newPair );
	}
	
	/**
	 * Clears the dictionary of all content.
	 */
	public void clear() {
		pairs.clear();		
	}
}
