package hr.fer.zemris.java.hw05.db;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class QueryFilterTest {
	
	private QueryFilter filter;
	private QueryParser parser;
	
	@Test
	public void acceptsDirect() {
		StudentRecord record1 = new StudentRecord( "0000000003", "Bosnić", "Andrea", 4 );
		StudentRecord record2 = new StudentRecord( "0000000005", "Matković", "Bruno", 5 );
		parser = new QueryParser( "jmbag=\"0000000003\"" );
		filter = new QueryFilter( parser.getQuery() );
		
		assertTrue( filter.accepts(record1) );
		assertFalse( filter.accepts(record2) );
	}
	@Test
	public void acceptsNotDirect() {
		StudentRecord record1 = new StudentRecord( "0000000003", "Bosnić", "Andrea", 4 );
		StudentRecord record2 = new StudentRecord( "0000000005", "Matković", "Bruno", 5 );
		parser = new QueryParser( "jmbag>\"0000000003\"" );
		filter = new QueryFilter( parser.getQuery() );
		
		assertTrue( filter.accepts(record2) );
		assertFalse( filter.accepts(record1) );
	}
	@Test
	public void acceptsMultipleConditions1() {
		StudentRecord record1 = new StudentRecord( "0000000003", "Bosnić", "Andrea", 4 );
		StudentRecord record2 = new StudentRecord( "0000000005", "Matković", "Bruno", 5 );
		parser = new QueryParser( "jmbag>=\"0000000003\" and firstName < \"Branko\"" );
		filter = new QueryFilter( parser.getQuery() );
		
		assertTrue( filter.accepts(record1) );
		assertFalse( filter.accepts(record2) );
	}
	@Test
	public void acceptsMultipleConditions2() {
		StudentRecord record1 = new StudentRecord( "0000000003", "Bosnić", "Andrea", 4 );
		StudentRecord record2 = new StudentRecord( "0000000005", "Matković", "Bruno", 5 );
		parser = new QueryParser( "jmbag>=\"0000000003\" and firstName LIKE \"B*\"" );
		filter = new QueryFilter( parser.getQuery() );
		
		assertTrue( filter.accepts(record2) );
		assertFalse( filter.accepts(record1) );
	}
	@Test
	public void acceptsMultipleConditions3() {
		StudentRecord record1 = new StudentRecord( "0000000003", "Bosnić", "Andrea", 4 );
		StudentRecord record2 = new StudentRecord( "0000000005", "Matković", "Bruno", 5 );
		parser = new QueryParser( "jmbag>=\"0000000003\" and firstName < \"Branko\" and lastName LIKE \"Bos*\"" );
		filter = new QueryFilter( parser.getQuery() );
		
		assertTrue( filter.accepts(record1) );
		assertFalse( filter.accepts(record2) );
	}
}
