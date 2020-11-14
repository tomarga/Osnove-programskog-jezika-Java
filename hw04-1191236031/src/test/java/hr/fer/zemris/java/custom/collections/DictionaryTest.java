package hr.fer.zemris.java.custom.collections;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class DictionaryTest {

	//constructor
	@Test
	public void constructor() {
		Dictionary<String,Integer> dictionary = new Dictionary<>();
		
		assertEquals( 0, dictionary.size() );
		assertTrue( dictionary.isEmpty() );
	}
	
	
	//put
	@Test
	public void putBasic() {
		Dictionary<String,Integer> dictionary = new Dictionary<>();
		dictionary.put( "A", Integer.valueOf( 0 ) );
		dictionary.put( "B", Integer.valueOf( 1 ) );
		dictionary.put( "C", Integer.valueOf( 2 ) );
		
		assertEquals( 3, dictionary.size() );
		assertEquals( Integer.valueOf( 0 ), dictionary.get( "A" ) );
		assertEquals( Integer.valueOf( 1 ), dictionary.get( "B" ) );
		assertEquals( Integer.valueOf( 2 ), dictionary.get( "C" ) );
	}
	@Test
	public void putExistingKey() {
		Dictionary<String,Integer> dictionary = new Dictionary<>();
		dictionary.put( "A", Integer.valueOf( 0 ) );
		dictionary.put( "A", Integer.valueOf( 1 ) );
		
		assertEquals( 1, dictionary.size() );
		assertEquals( Integer.valueOf( 1 ), dictionary.get( "A" ) );
	}	
	@Test
	public void putNullKey() {
		Dictionary<String,Integer> dictionary = new Dictionary<>();
		
		assertThrows(NullPointerException.class, () -> {
			dictionary.put( null, Integer.valueOf( 0 ) );
		});
	}
	
	
	//get
	@Test
	public void getWrongType() {
		Dictionary<String,Integer> dictionary = new Dictionary<>();
		dictionary.put( "A", Integer.valueOf( 0 ) );
		dictionary.put( "B", Integer.valueOf( 1 ) );
		dictionary.put( "C", Integer.valueOf( 2 ) );
		
		assertEquals( null, dictionary.get( 1 ) );
	}
	@Test
	public void getNonExisting() {
		Dictionary<String,Integer> dictionary = new Dictionary<>();
		dictionary.put( "A", Integer.valueOf( 0 ) );
		dictionary.put( "B", Integer.valueOf( 1 ) );
		dictionary.put( "C", Integer.valueOf( 2 ) );
		
		assertEquals( null, dictionary.get( "D" ) );
	}
	
	
	//clear
	@Test
	public void clear() {
		Dictionary<String,Integer> dictionary = new Dictionary<>();
		dictionary.put( "A", Integer.valueOf( 0 ) );
		dictionary.put( "B", Integer.valueOf( 1 ) );
		dictionary.put( "C", Integer.valueOf( 2 ) );
		
		dictionary.clear();
		
		assertTrue( dictionary.isEmpty() );
	}

}
