package hr.fer.zemris.java.custom.collections;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * A class that represents a simple hash table implementation
 * of a map. 
 * 
 * @author Margarita Tolja
 *
 * @param <K>
 * 		Type of keys.
 * @param <V>
 * 		Type of values.
 */
public class SimpleHashtable<K,V> implements Iterable<SimpleHashtable.TableEntry<K,V>> {
	
	private TableEntry<K,V>[] table;
	private int size;
	private int modificationCount;

	/**
	 * Nested class that represents one table entry,
	 * meaning one pair in a slot.
	 *
	 * @param <K>
	 * 		Type of keys.
	 * @param <V>
	 * 		Type of values.
	 */
	public static class TableEntry<K,V> {
		
		private K key;
		private V value;
		private TableEntry<K,V> next;
		
		/**
		 * Creates a new table entry with passed parameters.
		 * Sets the reference to the next entry to null.
		 * @param key
		 * 		The key of a pair.
		 * @param value
		 * 		The value of a pair.
		 */
		public TableEntry( K key, V value ) {
			this.key = key;
			this.value = value;
			this.next = null;
		}
		
		/**
		 * Returns the String representation of table entry in the 
		 * following form: "key=value".
		 */
		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder();
			sb.append( key ).append( "=" ).append( value );
			
			return sb.toString();
		}
		
		/**
		 * Key getter.
		 * @return
		 * 		The key of a pair.
		 */
		public K getKey() {
			return key;
		}
		
		/**
		 * Value getter.
		 * @return
		 * 		The value of a pair.
		 */
		public V getValue() {
			return value;
		}
		
		/**
		 * Value setter.
		 * @param value
		 * 		The new value of a pair.
		 */
		public void setValue( V value ) {
			this.value = value;
		}
		
	}
	
	/**
	 * Inner class that represents a construct used for iterating
	 * the hash table - starting form first until the last filled slot.
	 * The iterator goes to the next slot only when it had passed all 
	 * entries in the list of previous slot.
	 */
	private class IteratorImpl implements Iterator<SimpleHashtable.TableEntry<K,V>> {
		
		private TableEntry<K,V> currentEntry;
		private TableEntry<K,V> nextEntry;
		private int lastModification;
		
		/**
		 * Creates an iterator set to first non-null entry in a hash table.
		 * If there is not such entry, the current iterator is set to null.
		 */
		public IteratorImpl() {		
			lastModification = modificationCount;
			currentEntry = null;
			
			for ( TableEntry<K,V> slot : table ) {				
				if ( slot != null ) {
					nextEntry = slot;			
					return;
				}
			}
			nextEntry = null;
		}
		
		/**
		 * Checks if there is a table entry after the current one.
		 * @throws ConcurrentModificationException if the table changed while iterating.
		 * @return
		 * 		<code>true</code> if there is such;
		 * 		<code>false</code> otherwise.
		 */
		public boolean hasNext() {	
			if ( lastModification != modificationCount ) {
				throw new ConcurrentModificationException( "has next method" );
			}
			return nextEntry != null;
		}
		
		/**
		 * Gets the next table entry after the current one.
		 * Updates the current entry.
		 * @throws NoSuchElementException if there is no next element available in table.
		 * @throws ConcurrentModificationException if the table changed while iterating.
		 * @return
		 * 		The next table entry.
		 */
		public SimpleHashtable.TableEntry<K,V> next() {
			
			if ( lastModification != modificationCount ) {
				throw new ConcurrentModificationException( "next method" );
			}
			if ( nextEntry == null ) {
				throw new NoSuchElementException();
			}
			
			//currentEntry is updated unless it's the first call of this method
			if ( currentEntry == null || !currentEntry.equals( nextEntry ) ) {
				currentEntry = nextEntry;
			}
			
			//update nextEntry
			//check the same slot
			if ( nextEntry.next != null ) {
				nextEntry = nextEntry.next;
			} 
			//check the next slot
			else {			
				int nextSlot = hash( nextEntry.key ) + 1;
				
				while( nextSlot < table.length ) {
					if ( table[nextSlot] != null ) {
						nextEntry = table[nextSlot];
						break;
					}
					nextSlot++;
				}
			}
			
			//if there is no next entry
			if ( currentEntry.equals( nextEntry ) ) {
				nextEntry = null;
			}
			return currentEntry;			
		}
		/**
		 * Removes the entry last returned by next method.
		 * This method can only be called once after each next method.
		 * @throws IllegalStateException if previous method was something other than next method.
		 * @throws ConcurrentModificationException if the table changed while iterating.
		 */
		public void remove() {	
			
			if ( lastModification != modificationCount ) {
				throw new ConcurrentModificationException( "remove method" );
			}
			if ( currentEntry == null ) {
				throw new IllegalStateException( "Iterator can only remove one element after next method"
						+ " is called upon it." );
			}
			
			SimpleHashtable.this.remove( currentEntry.key );
			currentEntry = null;
			lastModification++;			
		}
	}
	
	/**
	 * Creates an empty hash table with 16 slots.
	 */
	@SuppressWarnings("unchecked")
	public SimpleHashtable() {
		table = ( TableEntry<K,V>[] )new TableEntry[16];
		size = 0;
		modificationCount = 0;
	}
	
	/**
	 * Creates an empty hash table with initial capacity of a first power of two 
	 * greater than or equal to the passed argument.
	 * @throws IllegalArgumentException if the passed argument is less than 1.
	 * @param capacity
	 * 		Parameter used for calculating initial capacity..
	 */
	@SuppressWarnings("unchecked")
	public SimpleHashtable( int capacity ) {
		if ( capacity < 1 ) {
			throw new IllegalArgumentException( "Hashtable capacity has to be a tleast 1." );
		}
		
		double potencyOfTwo = Math.ceil( Math.log( capacity ) / Math.log( 2 ) );
		
		table = ( TableEntry<K,V>[] )new TableEntry[(int) Math.pow( 2, potencyOfTwo )];
		size = 0;
		modificationCount = 0;
	}
	
	/**
	 * Gets the specified iterator for going through table entries.
	 */
	public Iterator<SimpleHashtable.TableEntry<K,V>> iterator() {
		return new IteratorImpl();
	}
	
	/**
	 * Gets the size of a table.
	 * @return
	 * 		The number of pairs stored in hash table.
	 */
	public int size() {
		return size;
	}
	
	/**
	 * Gets the capacity of a table.
	 * For testing purposes.
	 * @return
	 * 		The number of hash slots.
	 */
	public int capacity() {
		return table.length;
	}
	
	/**
	 * Checks if the hash table is empty.
	 * @return
	 * 		<code>true</code> if it is;
	 * 		<code>false</code> otherwise.
	 */
	public boolean isEmpty() {
		return size == 0;
	}
	
	/**
	 * Checks if the table contains a pair whose key is as passed in argument.
	 * @param key
	 * 		Key of interest.
	 * @return
	 * 		<code>true</code> if there is such pair;
	 * 		<code>false</code> otherwise.
	 */
	public boolean containsKey( Object key ) {
		
		if ( key == null ) {
			return false;
		}
		TableEntry<K,V> currentPair = table[hash(key)];
		while( currentPair != null ) {
			if ( currentPair.getKey().equals( key ) ) {
				return true;
			}
			currentPair = currentPair.next;
		}	
		return false;
	}
	
	/**
	 * Checks if the table contains a pair whose value is as passed in argument.
	 * @param value
	 * 		Value of interest.
	 * @return
	 * 		<code>true</code> if there is such pair;
	 * 		<code>false</code> otherwise.
	 */
	public boolean containsValue( Object value ) {
		
		for( TableEntry<K,V> slot : table ) {
			if ( slot == null ) {
				continue;
			}
			TableEntry<K,V> currentPair = slot;
			while( currentPair != null ) {
				if ( currentPair.getValue().equals( value ) ) {
					return true;
				}
				currentPair = currentPair.next;
			}
		}
		return false;
	}
	
	/**
	 * Adds a new pair to in a slot based on the key value.
	 * If there are more pairs in one slot, the new one is added on the end
	 * of the list.
	 * If existing, the value with the same key will be overwritten.
	 * @throws NullPointerException if null value is passed for a key.
	 * @param key
	 * 		The new pair's key.
	 * @param value
	 * 		The new pair's value.
	 */
	public void put( K key, V value ) {
		
		if ( key == null ) {
			throw new NullPointerException( "Key cannot be null value." );
		}	
		
		//if this is the first pair in this slot
		if ( table[hash(key)] == null ) {
			table[hash(key)] = new TableEntry<K,V>( key, value );
			size++;
			//the table is modified
			modificationCount++;
			//update capacity if needed
			updateCapacity();
			return;
		}		
		
		TableEntry<K,V> currentPair = table[hash(key)];
		//searching for a pair with the same key	
		//check first
		if ( currentPair != null && currentPair.getKey().equals( key ) ) {
			currentPair.setValue( value );
			return;
		}
		//check the rest
		while( currentPair != null && currentPair.next != null ) {
			if ( currentPair.next.getKey().equals( key ) ) {
				currentPair.next.setValue( value );
				return;
			}
			currentPair = currentPair.next;
		}
		//if there is no pair with such key, add new pair on the end of the list
		currentPair.next = new TableEntry<K,V>( key, value ); 
		size++;
		//the table is modified
		modificationCount++;
		//update capacity if needed
		updateCapacity();	
	}
	
	/**
	 * Gets the value of a pair whose key is as the one passed as argument.
	 * @param key
	 * 		The key of a pair.
	 * @return
	 * 		The value of a pair;
	 * 		null if there is no pair with passed key.
	 */
	public V get( Object key ) {	
		
		if ( key == null ) {
			return null;
		}
		TableEntry<K,V> currentPair = table[hash(key)];
		
		//searching for a pair with the same key
		while( currentPair != null ) {
			if ( currentPair.getKey().equals( key ) ) {
				return currentPair.getValue();
			}
			currentPair = currentPair.next;
		}	
		return null;	
	}
	
	/**
	 * Removes the pair whose key is as the one passed as argument.
	 * If there is no such pair, it does nothing.
	 * @param key
	 * 		The key of a pair to be removed.
	 */
	public void remove( Object key ) {
		
		TableEntry<K,V> currentPair = table[hash(key)];
		
		//check first
		if ( currentPair != null && currentPair.getKey().equals( key ) ) {
			table[hash(key)] = currentPair.next;
			size--;
			//the table is modified
			modificationCount++;
			return;
		}
		
		//check the rest
		while( currentPair != null && currentPair.next != null ) {
			if ( currentPair.next.getKey().equals( key ) ) {
				currentPair.next = currentPair.next.next;
				size--;
				//the table is modified
				modificationCount++;
				return;
			}
			currentPair = currentPair.next;
		}	
	}
	
	/**
	 * Returns the String representation of the hash table in 
	 * the following format: "[key=value, ..., key=value]".
	 */
	@Override
	public String toString() {
		StringBuilder sb =  new StringBuilder();
		sb.append( "[" );
		
		for ( TableEntry<K,V> slot : table ) {
			TableEntry<K,V> currentPair = slot;
			
			//iterating list
			while( currentPair != null ) {
				sb.append( currentPair.toString() ).append( ", " );
				currentPair = currentPair.next;
			}
		}	
		if ( sb.length() >= 2 ) {
			sb.replace( sb.length() - 2, sb.length(), "]" );
		} else {
			sb.append( "]" );
		}
		return sb.toString();
	}
	
	/**
	 * Doubles the table's capacity if more than 75% is full.
	 * Keeps the old pairs stored.
	 */
	@SuppressWarnings("unchecked")
	public void updateCapacity() {

		double borderCapacity = 75 * (double) table.length / 100;

		if ( size >= borderCapacity ) {
			
			//the table will be modified
			modificationCount++;
			
			TableEntry<K,V>[] oldTable = table;
			table = ( TableEntry<K,V>[] )new TableEntry[2 * capacity()];
			size = 0;
			
			//fill new table
			for ( TableEntry<K,V> slot: oldTable ) {
				TableEntry<K,V> currentPair = slot;
				
				while( currentPair != null ) {
					put( currentPair.key, currentPair.value );
					currentPair = currentPair.next;
				}
			}
		}
	}
	
	/**
	 * Clears the hash table of all content.
	 */
	public void clear() {
		for ( int i = 0; i < table.length; i++ ) {
			table[i] = null;
		}
		size = 0;
		//the table is modified
		modificationCount++;
	}
	
	/**
	 * Returns the slot where the pair with passed key belongs in a table.
	 * Appropriate slot is calculated as key's hash code divided by length of a table.
	 * @param key
	 * 		Key of the pair.
	 * @return
	 * 		The index of a slot where the pair belong.
	 */
	private int hash( Object key ) {
		return Math.abs( key.hashCode() ) % table.length;
	}
}
