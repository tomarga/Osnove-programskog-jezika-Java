package hr.fer.zemris.java.hw05.db;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.BiConsumer;

/**
 * A class that represents a simple database implementation.
 * The database storages student records.
 * 
 * @author Margarita Tolja
 *
 */
public class StudentDatabase {
	
	private Map<String,StudentRecord> database;
	
	/**
	 * Creates a student database with the records read from list of rows 
	 * that represent a line in a database file.
	 * @throws IllegalArgumentException if the attributes aren't valid.
	 * @param rows
	 * 		List of rows in following format "jmbag surname name final grade".
	 */
	public StudentDatabase( List<String> rows ) {
		
		database = new TreeMap<>();
		
		for ( String row : rows ) {
			String[] attributes = row.split( "\\s+" );
			
			//check the number of attributes
			if ( attributes.length < 4 ) {
				throw new IllegalArgumentException( "The number of attributes in a row must be at least "
						+ "4." );
			}
			//checks if finalGrade is in valid format
			int grade = 0;
			try {
				grade = Integer.valueOf( attributes[attributes.length-1] );
			} catch( NumberFormatException e ) {
				throw new IllegalArgumentException( "The final grade must be a whole number." ); 
			}
			if ( grade < 1 || grade > 5 ) {
				throw new IllegalArgumentException( "Final grade must be a whole number from 1 to 5." );
			}
			//check for duplicates
			if ( database.containsKey( attributes[0] ) ) {
				throw new IllegalArgumentException( "Duplicate jmbag id's." );
			}
			
			StudentRecord record = null;
			if ( attributes.length == 5 ) {
				 record = new StudentRecord( attributes[0], attributes[1] +"-"+ attributes[2], attributes[3], grade );
			} else {
				record = new StudentRecord( attributes[0], attributes[1], attributes[2], grade );
			}
			database.put( attributes[0], record );			
		}
	}
	
	/**
	 * Gets the record of a student with jmbag as passed in argument.
	 * @param jmbag
	 * 		Jmbag of a student.
	 * @return
	 * 		Student record if there is a student in the database with such jmbag;
	 * 		null value otherwise.
	 */
	public StudentRecord forJMBAG( String jmbag ) {
		return database.get( jmbag );
	}
	
	/**
	 * Returns the list of all records accepted by filter.
	 * @param filter
	 * 		Filter that 'decides' which record to accept.
	 * @return
	 * 		The list of all accepted records. 		
	 */
	public List<StudentRecord> filter( IFilter filter ) {
		
		List<StudentRecord> acceptedRecords = new ArrayList<>();
		
		database.forEach( new BiConsumer<String, StudentRecord>() {
			@Override
			public void accept( String key, StudentRecord record ) {
				if ( filter.accepts( record ) ) {
					acceptedRecords.add( record );
				}	
			}			
		});
		return acceptedRecords;
	}
	
}
