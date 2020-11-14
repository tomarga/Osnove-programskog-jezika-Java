package hr.fer.zemris.java.hw05.db;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

public class StudentDatabaseTest {
	
	private class TrueFilter implements IFilter {
		@Override
		public boolean accepts(StudentRecord record) {
			return true;
		}	
	}
	private class FalseFilter implements IFilter {
		@Override
		public boolean accepts(StudentRecord record) {
			return false;
		}	
	}
	private StudentDatabase db;
	
	
	//forJMBAG
	@Test
	public void forJMBAG() {
		ArrayList<String> rows = new ArrayList<>();
		rows.add( "0000000023	Kalvarešin	Ana	4\n" );
		db = new StudentDatabase( rows );
		
		StudentRecord student = new StudentRecord( "0000000023", "Kalvarešin",	"Ana",	4 );
		assertEquals( student, db.forJMBAG( "0000000023"  ) );
	}
	@Test
	public void forJMBAGNull() {
		ArrayList<String> rows = new ArrayList<>();
		rows.add( "0000000023	Kalvarešin	Ana	4\n" );
		db = new StudentDatabase( rows );

		assertEquals( null, db.forJMBAG( "0000000024"  ) );
	}
	
	//filter
	@Test
	public void filterTrue() {
		ArrayList<String> rows = new ArrayList<>();
		rows.add( "0000000023	Kalvarešin	Ana	4\n" );
		rows.add( "0000000024	Karlović	Đive	5" );
		db = new StudentDatabase( rows );
		
		List<StudentRecord> filtered = db.filter( new TrueFilter() );
		
		assertEquals( 2, filtered.size() );
	}
	@Test
	public void filterFalse() {
		ArrayList<String> rows = new ArrayList<>();
		rows.add( "0000000023	Kalvarešin	Ana	4\n" );
		rows.add( "0000000024	Karlović	Đive	5" );
		db = new StudentDatabase( rows );
		
		List<StudentRecord> filtered = db.filter( new FalseFilter() );
		
		assertEquals( 0, filtered.size() );
	}
}
