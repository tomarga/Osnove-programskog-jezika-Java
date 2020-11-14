package hr.fer.zemris.java.custom.collections;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class ArrayIndexedCollectionTest {
	
	private ArrayIndexedCollection array;
	private Collection collection;
	
	
	//default constructor
	@Test
	public void defaultConstructorSize() {
		array = new ArrayIndexedCollection();
		int n = array.size();
		assertEquals( 0, n );				
	}
	@Test
	public void defaultConstructorCapacity() {
		array = new ArrayIndexedCollection();
		int c = array.capacity();
		assertEquals( 16, c );
	}
	@Test
	public void defaultConstructorEmpty() {
		array = new ArrayIndexedCollection();
		assertTrue( array.isEmpty() );
	}
	
	//initial capacity constructor	
	@Test
	public void constructorCapacitySize() {
		array = new ArrayIndexedCollection( 5 );
		int n = array.size();
		assertEquals( 0, n );
	}
	@Test
	public void constructorCapacityCapacity() {
		array = new ArrayIndexedCollection( 20 );
		int c = array.capacity();
		assertEquals( 20, c );
	}
	@Test
	public void constructorCapacityNegative() {
		assertThrows(IllegalArgumentException.class, () -> {
			array = new ArrayIndexedCollection( -1 );
		});
	}

	//other collection constructor	
	@Test 
	public void constructorCollectionSize() {
		collection = new ArrayIndexedCollection();
		collection.add( 1 );
		collection.add( 2 );
		array = new ArrayIndexedCollection( collection );
		assertEquals( 2, array.size() );
	}
	@Test 
	public void constructorCollectionGreaterSize() {
		collection = new ArrayIndexedCollection();
		for ( int i = 0; i < 17; i++ )
			collection.add( i );
		array = new ArrayIndexedCollection( collection );
		assertEquals( 17, array.size() );
	}
	@Test
	public void constructorCollectionArray() {
		collection = new ArrayIndexedCollection();
		for ( int i = 0; i < 20; i++ )
			collection.add( 1 );
		array = new ArrayIndexedCollection( collection );
		assertArrayEquals( array.toArray(), collection.toArray());		
	}
	@Test
	public void constructorCollectionNull() {
		collection = null;
		assertThrows(NullPointerException.class, () -> {
			array = new ArrayIndexedCollection( collection );
		});
	}
	
	//initial capacity + collection constructor
	@Test
	public void constructorCapacityCollectionSize1() {
		collection = new ArrayIndexedCollection();
		collection.add( 1 );
		collection.add( 2 );
		array = new ArrayIndexedCollection( collection, 4 );
		assertEquals( array.size(), 2 );
	}
	@Test
	public void constructorCapacityCollectionSize2() {
		collection = new ArrayIndexedCollection();
		collection.add( 1 );
		collection.add( 2 );
		array = new ArrayIndexedCollection( collection, 1 );
		assertEquals( array.size(), 2 );
	}
	@Test
	public void constructorCapacityCollectionCapacity1() {
		collection = new Collection();
		array = new ArrayIndexedCollection( collection, collection.size() + 5 );
		
		int c = array.capacity();
		assertEquals( collection.size() + 5, c );
	}
	@Test
	public void constructorCapacityCollectionCapacity2() {
		collection = new ArrayIndexedCollection();
		collection.add( 1 );
		collection.add( 2 );
		array = new ArrayIndexedCollection( collection, 1 );
		
		int c = array.capacity();
		assertEquals( collection.size(), c );
	}
	@Test
	public void constructorCapacityCollectionArray1() {
		collection = new ArrayIndexedCollection();
		for ( int i = 0; i < 20; i++ )
			collection.add( 1 );
		array = new ArrayIndexedCollection( collection, 10 );
		assertArrayEquals( array.toArray(), collection.toArray());		
	}
	@Test
	public void constructorCapacityCollectionArray2() {
		collection = new ArrayIndexedCollection();
		for ( int i = 0; i < 20; i++ )
			collection.add( 1 );
		array = new ArrayIndexedCollection( collection, 20 );
		assertArrayEquals( array.toArray(), collection.toArray());		
	}
	@Test
	public void constructorCapacityCollectionNegative() {
		collection = new Collection();

		assertThrows(IllegalArgumentException.class, () -> {
			array = new ArrayIndexedCollection( collection, -1 );
		});
	}
	@Test
	public void constructorCapacityCollectionNull() {
		collection = null;
		
		assertThrows(NullPointerException.class, () -> {
			array = new ArrayIndexedCollection( collection, 5 );
		});
	}
	
	//add
	@Test
	public void add() {
		array = new ArrayIndexedCollection();
		array.add( 1 );
		array.add( 2 );
		Object[] elements = array.toArray();
		Object[] result = { 1, 2 };
		assertArrayEquals( result, elements );
	}
	@Test
	public void addDoubleSize() {
		array = new ArrayIndexedCollection( 2 );
		array.add( 1 );
		array.add( 2 );	
		array.add( 3 );	
		Object[] result = {1, 2, 3};
		assertEquals( 3, array.size() );
		assertArrayEquals( result, array.toArray() );
	}
	@Test
	public void addNull() {
		array = new ArrayIndexedCollection( 2 );
		assertThrows(NullPointerException.class, () -> {
			array.add( null );
		});
	}
	
	//get
	@Test
	public void get() {
		array = new ArrayIndexedCollection( 2 );
		array.add( 1 );
		array.add( 2 );	
		array.add( 3 );
		assertEquals( 1, array.get( 0 ) );
		assertEquals( 2, array.get( 1 ) );
		assertEquals( 3, array.get( 2 ) );		
	}
	@Test
	public void getEmpty() {
		array = new ArrayIndexedCollection( 2 );
		assertThrows(IndexOutOfBoundsException.class, () -> {
			array.get( 0 );
		});	
	}
	@Test
	public void getIndexLessThanZero() {
		array = new ArrayIndexedCollection( 2 );
		assertThrows(IndexOutOfBoundsException.class, () -> {
			array.get( -1 );
		});
	}
	@Test
	public void getIndexGreaterThanSize() {
		array = new ArrayIndexedCollection( 2 );
		assertThrows(IndexOutOfBoundsException.class, () -> {
			array.get( array.size() );
		});
	}
	
	//clear
	@Test
	public void clear() {
		array = new ArrayIndexedCollection();
		array.add( 1 );
		array.add( 2 );	
		array.add( 3 );
		array.clear();
		
		assertEquals( 0, array.size() );
		assertEquals( 16, array.capacity() );
		assertTrue( array.isEmpty() );
	}
	@Test
	public void clearEmpty() {
		array = new ArrayIndexedCollection();
		array.clear();
		assertEquals( 0, array.size() );
		assertEquals( 16, array.capacity() );
		assertTrue( array.isEmpty() );
	}
	
	//insert
	@Test
	public void insertEmpty() {
		array = new ArrayIndexedCollection();
		array.insert( 1, 0 );
		array.insert( 2, 1 );
		array.insert( 3, 2 );
		
		assertEquals( 1, array.get( 0 ) );
		assertEquals( 2, array.get( 1 ) );
		assertEquals( 3, array.get( 2 ) );	
	}
	@Test 
	public void insertFull() {
		array = new ArrayIndexedCollection(2);
		array.insert( 1, 0 );
		array.insert( 2, 1 );
		array.insert( 3, 2 );
		
		array.insert( 4, 0 );
		array.insert( 5, 1 );
		array.insert( 6, array.size() );
		
		Object[] expected = { 4, 5, 1, 2, 3, 6 };
		assertEquals( 8, array.capacity() );
		assertArrayEquals( expected, array.toArray() );
	}
	@Test
	public void insertIndexLessThanZero() {
		array = new ArrayIndexedCollection();
		assertThrows(IndexOutOfBoundsException.class, () -> {
			array.insert( 1, -1 );
		});
	}
	@Test
	public void insertIndexGreaterThanSize() {
		array = new ArrayIndexedCollection();
		assertThrows(IndexOutOfBoundsException.class, () -> {
			array.insert( 1, array.size() + 1 );
		});
	}
	@Test
	public void insertNull() {
		array = new ArrayIndexedCollection();
		assertThrows(NullPointerException.class, () -> {
			array.insert( null, 0 );
		});
	}
	
	//indexOf
	@Test
	public void indexOf() {
		array = new ArrayIndexedCollection();
		array.add( 1 );
		array.add( 2 );	
		array.add( 3 );		
		
		assertEquals( 0, array.indexOf( 1 ));
		assertEquals( 1, array.indexOf( 2 ));
		assertEquals( 2, array.indexOf( 3 ));
		assertEquals( -1, array.indexOf( 0 ));
	}
	@Test
	public void indexOfNull() {
		array = new ArrayIndexedCollection();		
		assertEquals( -1, array.indexOf( null ) );
	}
	
	//remove( index )
	@Test 
	public void removeFirst() {
		array = new ArrayIndexedCollection();
		array.add( "1" );
		array.add( "2" );	
		array.add( "3" );
		
		array.remove( 0 );
		Object[] expected = { "2", "3" };
		assertArrayEquals( expected, array.toArray() );
		assertEquals( 2, array.size() );
	}
	@Test 
	public void removeMiddle() {
		array = new ArrayIndexedCollection();
		array.add( "1" );
		array.add( "2" );	
		array.add( "3" );
		
		array.remove( 1 );
		Object[] expected = { "1", "3" };
		assertArrayEquals( expected, array.toArray() );
		assertEquals( 2, array.size() );
	}
	@Test 
	public void removeLast() {
		array = new ArrayIndexedCollection();
		array.add( "1" );
		array.add( "2" );	
		array.add( "3" );
		
		array.remove( 2 );
		Object[] expected = { "1", "2" };
		assertArrayEquals( expected, array.toArray() );
		assertEquals( 2, array.size() );
	}
	@Test
	public void removeIndexLessThanZero() {
		array = new ArrayIndexedCollection();
		assertThrows(IndexOutOfBoundsException.class, () -> {
			array.remove( -1 );
		});		
	}
	@Test
	public void removeGreaterThanSize() {
		array = new ArrayIndexedCollection();
		array.add( "1" );
		assertThrows(IndexOutOfBoundsException.class, () -> {
			array.remove( array.size() );
		});		
	}	
	
}
