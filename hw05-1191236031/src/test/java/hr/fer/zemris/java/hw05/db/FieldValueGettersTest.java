package hr.fer.zemris.java.hw05.db;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class FieldValueGettersTest {

	private IFieldValueGetter getter;
	private StudentRecord record;
	
	
	//FIRST_NAME
	@Test
	public void firstName() {
		record = new StudentRecord( "0000000000", "Doe", "John", 5 );
		getter = FieldValueGetters.FIRST_NAME;
		
		assertEquals( "John", getter.get( record ) );
	}
	
	//LAST_NAME
	@Test
	public void lastName() {
		record = new StudentRecord( "0000000000", "Doe", "John", 5 );
		getter = FieldValueGetters.LAST_NAME;
		
		assertEquals( "Doe", getter.get( record ) );
	}
	
	//JMBAG
	@Test
	public void jmbag() {
		record = new StudentRecord( "0000000000", "Doe", "John", 5 );
		getter = FieldValueGetters.JMBAG;
		
		assertEquals( "0000000000", getter.get( record ) );
	}
}
