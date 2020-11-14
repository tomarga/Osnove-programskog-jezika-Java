package hr.fer.zemris.java.custom.collections;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class LinkedListIndexedCollectionTest {
	
	private LinkedListIndexedCollection list;
	private Collection collection;
	private ArrayIndexedCollection array;
	
	//default constructor
	@Test
	public void defaultConstructor() {
		list = new LinkedListIndexedCollection();
		assertTrue( list.isEmpty() );
	}
	
	//constructor collection
	@Test
	public void constructorCollection() {
		array = new ArrayIndexedCollection();
		array.add( 1 );
		array.add( 2 );
		
		list = new LinkedListIndexedCollection( array );
		assertEquals( 2, list.size() );
		assertEquals( 1, list.get(0) );
		assertEquals( 2, list.get(1) );
		assertArrayEquals( list.toArray(), array.toArray() );	
	}
	@Test
	public void constructorEmpty() {
		collection = new Collection();
		list = new LinkedListIndexedCollection( collection );
		assertTrue( list.isEmpty() );
	}
	
	//add
	@Test
	public void add() {
		list = new LinkedListIndexedCollection();
		list.add( 1 );
		list.add( 2 );
		Object[] result = { 1, 2 };
		assertEquals( 2, list.size() );
		assertEquals( 1, list.get(0) );
		assertEquals( 2, list.get(1) );
		assertArrayEquals( result, list.toArray() );
	}
	@Test
	public void addNull() {
		list = new LinkedListIndexedCollection();
		assertThrows(NullPointerException.class, () -> {
			array.add( null );
		});
	}
	
	//get
	@Test
	public void get() {
		list = new LinkedListIndexedCollection();
		list.add( 1 );
		list.add( 2 );
		list.add( 3 );
		assertEquals( 1, list.get( 0 ) );
		assertEquals( 2, list.get( 1 ) );
		assertEquals( 3, list.get( 2 ) );		
	}
	@Test
	public void getEmpty() {
		list = new LinkedListIndexedCollection();
		assertThrows(IndexOutOfBoundsException.class, () -> {
			list.get( 0 );
		});	
	}
	@Test
	public void getIndexLessThanZero() {
		list = new LinkedListIndexedCollection();
		assertThrows(IndexOutOfBoundsException.class, () -> {
			list.get( -1 );
		});
	}
	@Test
	public void getIndexGreaterThanSize() {
		list = new LinkedListIndexedCollection();
		assertThrows(IndexOutOfBoundsException.class, () -> {
			list.get( list.size() );
		});
	}
	
	//clear
	@Test
	public void clear() {
		list = new LinkedListIndexedCollection();
		list.add( 1 );
		list.add( 2 );
		list.add( 3 );
		list.clear();

		assertTrue( list.isEmpty() );
	}
	@Test
	public void clearEmpty() {
		list = new LinkedListIndexedCollection();
		list.clear();
		assertTrue( list.isEmpty() );
	}	
	
	//insert
	@Test
	public void insertEmpty() {
		list = new LinkedListIndexedCollection();
		list.insert( 1, 0 );
		list.insert( 2, 1 );
		list.insert( 3, 2 );
		
		assertEquals( 1, list.get( 0 ) );
		assertEquals( 2, list.get( 1 ) );
		assertEquals( 3, list.get( 2 ) );	
	}
	@Test 
	public void insertFull() {
		list = new LinkedListIndexedCollection();
		list.insert( 1, 0 );
		list.insert( 2, 1 );
		list.insert( 3, 2 );
		
		list.insert( 4, 0 );
		list.insert( 5, 1 );
		list.insert( 6, list.size() );
		
		Object[] expected = { 4, 5, 1, 2, 3, 6 };
		assertArrayEquals( expected, list.toArray() );
	}
	@Test
	public void insertIndexLessThanZero() {
		list = new LinkedListIndexedCollection();
		assertThrows(IndexOutOfBoundsException.class, () -> {
			list.insert( 1, -1 );
		});
	}
	@Test
	public void insertIndexGreaterThanSize() {
		list = new LinkedListIndexedCollection();
		assertThrows(IndexOutOfBoundsException.class, () -> {
			list.insert( 1, list.size() + 1 );
		});
	}
	@Test
	public void insertNull() {
		list = new LinkedListIndexedCollection();
		assertThrows(NullPointerException.class, () -> {
			list.insert( null, 0 );
		});
	}
	
	//indexOf
	@Test
	public void indexOf() {
		list = new LinkedListIndexedCollection();
		list.insert( 1, 0 );
		list.insert( 2, 1 );
		list.insert( 3, 2 );		
		
		assertEquals( 0, list.indexOf( 1 ));
		assertEquals( 1, list.indexOf( 2 ));
		assertEquals( 2, list.indexOf( 3 ));
		assertEquals( -1, list.indexOf( 0 ));
	}
	@Test
	public void indexOfNull() {
		list = new LinkedListIndexedCollection();		
		assertEquals( -1, list.indexOf( null ) );
	}
	
	//remove( index )
	@Test 
	public void removeFirst() {
		list = new LinkedListIndexedCollection();
		list.add( "1" );
		list.add( "2" );	
		list.add( "3" );
		
		list.remove( 0 );
		Object[] expected = { "2", "3" };
		assertArrayEquals( expected, list.toArray() );
		assertEquals( 2, list.size() );
	}
	@Test 
	public void removeMiddle() {
		list = new LinkedListIndexedCollection();
		list.add( "1" );
		list.add( "2" );	
		list.add( "3" );
		
		list.remove( 1 );
		Object[] expected = { "1", "3" };
		assertArrayEquals( expected, list.toArray() );
		assertEquals( 2, list.size() );
	}
	@Test 
	public void removeLast() {
		list = new LinkedListIndexedCollection();
		list.add( "1" );
		list.add( "2" );	
		list.add( "3" );
		
		list.remove( 2 );
		Object[] expected = { "1", "2" };
		assertArrayEquals( expected, list.toArray() );
		assertEquals( 2, list.size() );
	}
	@Test
	public void removeIndexLessThanZero() {
		list = new LinkedListIndexedCollection();
		assertThrows(IndexOutOfBoundsException.class, () -> {
			list.remove( -1 );
		});		
	}
	@Test
	public void removeGreaterThanSize() {
		list = new LinkedListIndexedCollection();
		list.add( "1" );
		assertThrows(IndexOutOfBoundsException.class, () -> {
			list.remove( list.size() );
		});		
	}	
	
}
