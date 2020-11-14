package hr.fer.zemris.java.custom.collections;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
//import org.junit.jupiter.api.Disabled;

public class SimpleHashtableTest {

	private SimpleHashtable<String, Integer> map;
	Iterator<SimpleHashtable.TableEntry<String,Integer>> iter;
	
	//constructor
	@Test
	public void defaultConstructor() {
		map = new SimpleHashtable<String, Integer>();
		assertTrue( map.isEmpty() );
		assertEquals( 16, map.capacity() );
	}	
	@Test
	public void capacityConstructorOne() {
		map = new SimpleHashtable<String, Integer>( 1 );
		assertTrue( map.isEmpty() );
		assertEquals( 1, map.capacity() );		
	}	
	@Test
	public void capacityConstructorTwo() {
		map = new SimpleHashtable<String, Integer>( 2 );
		assertTrue( map.isEmpty() );
		assertEquals( 2, map.capacity() );		
	}	
	@Test
	public void capacityConstructorLessThanPower() {
		map = new SimpleHashtable<String, Integer>( 30 );
		assertTrue( map.isEmpty() );
		assertEquals( 32, map.capacity() );		
	}	
	@Test
	public void capacityConstructorEqualToPower() {
		map = new SimpleHashtable<String, Integer>( 32 );
		assertTrue( map.isEmpty() );
		assertEquals( 32, map.capacity() );		
	}	
	@Test
	public void capacityConstructorLessThanOne() {
		assertThrows( IllegalArgumentException.class, () -> {
			map = new SimpleHashtable<String, Integer>( 0 );;
		});
	}
	
	
	//put
	@Test
	public void putBasic() {
		map = new SimpleHashtable<String, Integer>();
		map.put( "A", 1 );
		map.put( "B", 2 );
		map.put( "C", 3 );
		
		assertEquals( 3, map.size() );
		assertEquals( 1, map.get( "A" ) );
		assertEquals( 2, map.get( "B" ) );
		assertEquals( 3, map.get( "C" ) );		
	}
	@Test
	public void putOverwrite() {
		map = new SimpleHashtable<String, Integer>();
		map.put( "A", 1 );
		map.put( "B", 2 );
		map.put( "A", 3 );
		
		assertEquals( 2, map.size() );
		assertEquals( 3, map.get( "A" ) );
		assertEquals( 2, map.get( "B" ) );	
	}
	@Test
	public void putOverflow() {
		map = new SimpleHashtable<String, Integer>( 2 );
		map.put( "A", 1 );
		map.put( "B", 2 );
		map.put( "C", 3 );
		map.put( "D", 4 );

		assertEquals( 4, map.size() );
		assertEquals( 1, map.get( "A" ) );
		assertEquals( 2, map.get( "B" ) );
		assertEquals( 3, map.get( "C" ) );	
		assertEquals( 4, map.get( "D" ) );
		
	}
	@Test
	public void putOverflowOverwrite() {
		map = new SimpleHashtable<String, Integer>( 2 );
		map.put( "A", 1 );
		map.put( "B", 2 );
		map.put( "C", 3 );
		map.put( "D", 4 );
		map.put( "E", 5 );
		map.put( "A", 6 );
		map.put( "C", 7 );
		
		assertEquals( 5, map.size() );
		assertEquals( 6, map.get( "A" ) );
		assertEquals( 2, map.get( "B" ) );
		assertEquals( 7, map.get( "C" ) );	
		assertEquals( 4, map.get( "D" ) );
		assertEquals( 5, map.get( "E" ) );
	}
	@Test
	public void putNullValue() {
		map = new SimpleHashtable<String, Integer>( 2 );
		map.put( "A", 1 );
		map.put( "B", null );
		
		assertEquals( 2, map.size() );
		assertEquals( 1, map.get( "A" ) );
		assertEquals( null, map.get( "B" ) );
	}
	@Test
	public void putNullKey() {
		map = new SimpleHashtable<String, Integer>( 2 );
		
		assertThrows( NullPointerException.class, () -> {
			map.put( null, 7 );
		});
	}
	
	
	//get
	@Test
	public void getNotThere() {
		map = new SimpleHashtable<String, Integer>( 2 );
		map.put( "A", 1 );
		assertEquals( null, map.get( "B" ) );
	}
	@Test
	public void getNullKey() {
		map = new SimpleHashtable<String, Integer>( 2 );
		map.put( "A", 1 );
		assertEquals( null, map.get( null ) );
	}
	@Test
	public void getWrongType() {
		map = new SimpleHashtable<String, Integer>( 2 );
		map.put( "A", 1 );
		assertEquals( null, map.get( 3 ) );
	}
	
	
	//remove
	@Test
	public void removeBasic() {
		map = new SimpleHashtable<String, Integer>();
		map.put( "A", 1 );
		map.put( "B", 2 );
		map.put( "C", 3 );
		map.remove( "A" ); 
		
		assertEquals( 2, map.size() );
		assertEquals( 2, map.get( "B" ) );
		assertEquals( 3, map.get( "C" ) );
		assertEquals( null, map.get( "A" ) );		
	}	
	@Test
	public void removeFromList() {
		map = new SimpleHashtable<String, Integer>(2);
		map.put( "A", 1 );
		map.put( "B", 2 );
		map.put( "C", 3 );
		map.remove( "C" );
		
		assertEquals( 2, map.size() );
		assertEquals( 2, map.get( "B" ) );
		assertEquals( 1, map.get( "A" ) );
		assertEquals( null, map.get( "C" ) );		
	}
	@Test
//	@Disabled
	public void removeMultiple() {
		map = new SimpleHashtable<String, Integer>(2);
		map.put( "A", 1 );
		map.put( "B", 2 );
		map.put( "C", 3 );
		map.remove( "C" );
		map.remove( "A" );
		
		assertEquals( 1, map.size() );
		assertEquals( 2, map.get( "B" ) );
		assertEquals( null, map.get( "A" ) );
		assertEquals( null, map.get( "C" ) );		
	}
	@Test
	public void removeAll() {
		map = new SimpleHashtable<String, Integer>(2);
		map.put( "A", 1 );
		map.put( "B", 2 );
		map.put( "C", 3 );
		map.remove( "C" );
		map.remove( "A" );
		map.remove( "B" );
		map.remove( "D" );
		
		assertTrue( map.isEmpty() );		
	}
	@Test
	public void removeNotExisting() {
		map = new SimpleHashtable<String, Integer>(2);
		map.put( "A", 1 );
		map.remove( "D" );
		
		assertEquals( 1, map.size() );
		assertEquals( 1, map.get( "A" ) );		
	}
	@Test
	public void removeFirstAndPut() {
		map = new SimpleHashtable<String, Integer>(2);
		map.put( "A", 1 );
		map.put( "B", 2 );
		map.remove( "A" );
		map.put( "C", 3 );
		
		assertEquals( 2, map.size() );
		assertEquals( 2, map.get( "B" ) );
		assertEquals( null, map.get( "A" ) );
		assertEquals( 3, map.get( "C" ) );		
	}
	@Test
//	@Disabled
	public void removeLastAndPut() {
		map = new SimpleHashtable<String, Integer>(2);
		map.put( "A", 1 );
		map.put( "B", 2 );
		map.remove( "B" );
		map.put( "C", 3 );
		
		assertEquals( 2, map.size() );
		assertEquals( null, map.get( "B" ) );
		assertEquals( 1, map.get( "A" ) );
		assertEquals( 3, map.get( "C" ) );		
	}
	@Test
//	@Disabled
	public void removeAndPut1() {
		map = new SimpleHashtable<String, Integer>(5);
		map.put( "A", 1 );
		map.put( "B", 2 );
		map.put( "C", 3 );
		map.remove( "B" );
		map.put( "D", 4 );

		assertEquals( 3, map.size() );
		assertEquals( null, map.get( "B" ) );
		assertEquals( 1, map.get( "A" ) );
		assertEquals( 3, map.get( "C" ) );	
		assertEquals( 4, map.get( "D" ) );
	}
	@Test
//	@Disabled
	public void removeAndPut2() {
		map = new SimpleHashtable<String, Integer>(3);
		map.put( "A", 1 );
		map.put( "B", 2 );
		map.put( "C", 3 );
		map.put( "D", 4 );
		map.put( "E", 5 );
		map.remove( "D" );

		assertEquals( 4, map.size() );
		assertEquals( 2, map.get( "B" ) );
		assertEquals( 1, map.get( "A" ) );
		assertEquals( 3, map.get( "C" ) );	
		assertEquals( 5, map.get( "E" ) );
		assertEquals( null, map.get( "D" ) );
	}
	
	
	//containsKey
	@Test
	public void containsKeyTrue() {
		map = new SimpleHashtable<String, Integer>(2);
		map.put( "A", 1 );
		map.put( "B", 2 );
		
		assertTrue( map.containsKey( "A" ) );
		assertTrue( map.containsKey( "B" ) );
	}
	@Test
	public void containsKeyTrueOverflow() {
		map = new SimpleHashtable<String, Integer>(2);
		map.put( "A", 1 );
		map.put( "B", 2 );
		map.put( "C", 3 );
		map.put( "D", 4 );
		
		assertTrue( map.containsKey( "A" ) );
		assertTrue( map.containsKey( "B" ) );
		assertTrue( map.containsKey( "C" ) );
		assertTrue( map.containsKey( "D" ) );		
	}
	@Test
	public void containsKeyFalse() {
		map = new SimpleHashtable<String, Integer>(2);
		map.put( "A", 1 );
		map.put( "B", 2 );
		
		assertTrue( !map.containsKey( "C" ) );
		assertTrue( !map.containsKey( 0 ) );
		assertTrue( !map.containsKey( null ) );		
	}
	
	
	//containsValue
	@Test
	public void containsValueTrue() {
		map = new SimpleHashtable<String, Integer>(2);
		map.put( "A", 1 );
		map.put( "B", 2 );
		
		assertTrue( map.containsValue( 1 ) );
		assertTrue( map.containsValue( 2 ) );
	}
	@Test
	public void containsValueTrueOverflow() {
		map = new SimpleHashtable<String, Integer>(2);
		map.put( "A", 1 );
		map.put( "B", 2 );
		map.put( "C", 3 );
		map.put( "D", 4 );
		
		assertTrue( map.containsValue( 1 ) );
		assertTrue( map.containsValue( 2 ) );
		assertTrue( map.containsValue( 3 ) );
		assertTrue( map.containsValue( 4 ) );		
	}
	@Test
	public void containsValueFalse() {
		map = new SimpleHashtable<String, Integer>(2);
		map.put( "A", 1 );
		map.put( "B", 2 );
		
		assertTrue( !map.containsValue( "C" ) );
		assertTrue( !map.containsValue( 0 ) );
		assertTrue( !map.containsValue( null ) );		
	}
	
	
	//toString
	@Test
	public void toStringEmpty() {
		map = new SimpleHashtable<String, Integer>(2);
		assertEquals( "[]", map.toString() );
	}
	@Test
	public void toStringBasic() {
		map = new SimpleHashtable<String, Integer>();
		map.put( "A", 1 );
		map.put( "B", 2 );
		
		assertEquals( "[A=1, B=2]", map.toString() );
	}	
	
	//doubleCapacity
	@Test
	public void doubleCapacity() {
		map = new SimpleHashtable<String, Integer>(2);
		map.put( "A", 1 );
		map.put( "B", 2 );
		assertEquals( 4, map.capacity() );
		assertEquals( 2, map.get( "B" ) );
		assertEquals( 1, map.get( "A" ) );
		map.put( "C", 3 );
		assertEquals( 8, map.capacity() );
		assertEquals( 2, map.get( "B" ) );
		assertEquals( 1, map.get( "A" ) );
		assertEquals( 3, map.get( "C" ) );
		map.put( "D", 4 );
		assertEquals( 8, map.capacity() );
		assertEquals( 2, map.get( "B" ) );
		assertEquals( 1, map.get( "A" ) );
		assertEquals( 3, map.get( "C" ) );
		assertEquals( 4, map.get( "D" ) );
	}
	
	
	//clear
	@Test
	public void clear() {
		map = new SimpleHashtable<String, Integer>(4);
		map.put( "A", 1 );
		map.put( "B", 2 );
		map.clear();
		
		assertTrue( map.isEmpty() );
		assertEquals( 4, map.capacity() );
	}
	@Test
	public void clearReuse() {
		map = new SimpleHashtable<String, Integer>(4);
		map.put( "A", 1 );
		map.put( "B", 2 );
		map.clear();
		map.put( "A", 1 );
		
		assertTrue( !map.isEmpty() );
		assertEquals( 4, map.capacity() );
	}
	
	
	//iterator hasNext
	@Test
	public void iteratorHasNextEmpty() {
		map = new SimpleHashtable<String, Integer>();
		iter = map.iterator();
		
		assertFalse( iter.hasNext() );
	}
	@Test
	public void iteratorHasNextNonEmpty() {
		map = new SimpleHashtable<String, Integer>();
		map.put( "A", 1 );
		iter = map.iterator();
		
		assertTrue( iter.hasNext() );
	}
	@Test
	public void iteratorHasNextModified() {
		map = new SimpleHashtable<String, Integer>();
		iter = map.iterator();
		assertFalse( iter.hasNext() );
		
		map.put( "A", 1 );
		assertThrows(ConcurrentModificationException.class, () -> {
			iter.hasNext();
		});
	}
	
	
	//iterator next
	@Test
	public void iteratorNextBasic() {
		map = new SimpleHashtable<String, Integer>(2);
		map.put( "A", 1 );
		map.put( "B", 2 );
		map.put( "C", 3 );
		map.put( "D", 4 );
		iter = map.iterator();
		
		assertEquals( "A=1", iter.next().toString() );
		assertEquals( "B=2", iter.next().toString() );
		assertEquals( "C=3", iter.next().toString() );
		assertEquals( "D=4", iter.next().toString() );
		assertFalse( iter.hasNext() );
	}
	@Test
	public void iteratorNextEmpty() {
		map = new SimpleHashtable<String, Integer>();
		iter = map.iterator();
		assertThrows(NoSuchElementException.class, () -> {
			iter.next();
		});	
	}
	@Test
	public void iteratorNextNoNext() {
		map = new SimpleHashtable<String, Integer>();
		map.put( "A", 1 );
		iter = map.iterator();
		iter.next();
		
		assertThrows(NoSuchElementException.class, () -> {
			iter.next();
		});	
	}
	@Test
	public void iteratorNextModified() {
		map = new SimpleHashtable<String, Integer>();
		iter = map.iterator();
		map.put( "A", 1 );
		
		assertThrows(ConcurrentModificationException.class, () -> {
			iter.next();
		});	
	}
	
	
	//iterator remove
	@Test
	public void iteratorRemoveOne() {
		map = new SimpleHashtable<String, Integer>();
		map.put( "A", 1 );
		map.put( "B", 2 );
		iter = map.iterator();
		String keyRemoved = iter.next().getKey();
		iter.remove();
		
		assertEquals( 1, map.size() );
		assertEquals( null, map.get( keyRemoved ) );		
	}
	@Test
	public void iteratorRemoveAll() {
		map = new SimpleHashtable<String, Integer>();
		map.put( "A", 1 );
		map.put( "B", 2 );
		iter = map.iterator();
		iter.next();
		iter.remove();
		iter.next();
		iter.remove();
		
		assertEquals( 0, map.size() );		
	}
	@Test
	public void itaretorRemoveBeforeNext() {
		map = new SimpleHashtable<String, Integer>();
		map.put( "B", 2 );
		iter = map.iterator();
		
		assertThrows(IllegalStateException.class, () -> {
			iter.remove();
		});
	}
	@Test
	public void iteratorRemoveBeforeNext() {
		map = new SimpleHashtable<String, Integer>();
		map.put( "B", 2 );
		iter = map.iterator();
		
		assertThrows(IllegalStateException.class, () -> {
			iter.remove();
		});
	}
	@Test
	public void removeTwice() {
		map = new SimpleHashtable<String, Integer>();
		map.put( "A", 1 );
		map.put( "B", 2 );
		iter = map.iterator();
		iter.next();
		iter.remove();
		
		assertThrows(IllegalStateException.class, () -> {
			iter.remove();
		});
	}
	@Test
	public void removeModified() {
		map = new SimpleHashtable<String, Integer>();
		map.put( "A", 1 );
		iter = map.iterator();
		map.put( "B", 2 );
		
		assertThrows(ConcurrentModificationException.class, () -> {
			iter.remove();
		});
	}

}
