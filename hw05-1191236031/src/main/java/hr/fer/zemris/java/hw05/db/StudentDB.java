package hr.fer.zemris.java.hw05.db;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * A simple student database emulator. 
 * The database is filled with the records from 'database.txt' file.
 * Each record contains information about student: jmbag, last name, first name and final grade.
 * Program outputs the results of queries given as an keyboard input.
 * Conditional expressions should be in the following form: 'query field_name operator string_literal'.
 * A valid query consists of one or more conditional expressions, separated with 'and'.
 * 
 * @author Margarita Tolja
 *
 */
public class StudentDB {

	/**
	 * Main method fills the database with records and outputs
	 * the queries results. If user inputed 'exit' keyword 
	 * the program terminates with appropriate message to user.
	 * No argument values(args) expected.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		
		Scanner sc = new Scanner( System.in );
		
		//read from file	
		List<String> lines = null;
		try {
			lines = Files.readAllLines(
			Paths.get( "./src/main/resources/database.txt" ),
			StandardCharsets.UTF_8
			);
		} catch ( IOException e ) {
			e.printStackTrace();
		}
		//fill database
		StudentDatabase db = null;
		try {
			db = new StudentDatabase( lines );
		} catch ( IllegalArgumentException e ) {
			System.out.println( e.getMessage() );
			sc.close();
			return;
		}
		//make queries
		String query = "";
		while( true ) {
			System.out.print( "> " );
			//check for next query
			if ( sc.hasNextLine() ) {
				query = sc.nextLine();
			}
			else {
				continue;
			}
			//check if exit
			if ( query.equals( "exit" ) ) {
				System.out.println( "Goodbye!" );
				break;
			}
			//check for 'query' keyword
			if ( !query.split( "\\s+" )[0].equals( "query" ) ) {
				System.out.println( "Missing the 'query' keyword.");
				continue;
			}
			String queryBody = query.substring( query.indexOf( 'y' ) + 1 );
			
			List<StudentRecord> validRecords = null;
			try {
				validRecords = filterRecords( db, queryBody );
			} catch ( QueryParserException e ) {
				System.out.println( e.getMessage() );
				continue;
			} catch ( IllegalArgumentException ex ) {
				System.out.println( ex.getMessage() );
				continue;
			}
			
			if ( validRecords.contains( null ) ) {
				//direct query with no result
				System.out.println( "Records selected: 0\n" );
				continue;
			}
			if ( !validRecords.isEmpty() ) {
				outputRecords( validRecords );	
			}
			System.out.println( "Records selected: " + validRecords.size() + "\n" );			
		}	
		sc.close();		
	}

	/**
	 * Filters the database records according to the query given as an argument.
	 * @param db
	 * 		Student database.
	 * @param query
	 * 		String containing the inputed query without the 'query' keyword.
	 * @return
	 * 		Records accepted by the filter.
	 */
	private static List<StudentRecord> filterRecords( StudentDatabase db, String query ) {
		
		List<StudentRecord> list = new ArrayList<>();
		QueryParser parser = new QueryParser( query );

		if( parser.isDirectQuery() ) {
			System.out.println( "Using index for record retrieval." );
			list.add( db.forJMBAG( parser.getQueriedJMBAG() ) );
		} 
		else {
			list = db.filter( new QueryFilter( parser.getQuery() ) );
		}
		return list;
	}
	
	/**
	 * Outputs the filtered records in a specific format.
	 * @param records
	 * 		Filtered records.
	 */
	private static void outputRecords( List<StudentRecord> records ) {
		
		//longest name and surname length
		int nameLength = 0;
		int surnameLength = 0;
		
		for ( StudentRecord record : records ) {
			int currentNameLength = FieldValueGetters.FIRST_NAME.get(record).length();
			if ( currentNameLength > nameLength ) {
				nameLength = currentNameLength;
			}
			int currentsurnameLLength = FieldValueGetters.LAST_NAME.get(record).length();
			if ( currentsurnameLLength > surnameLength ) {
				surnameLength = currentsurnameLLength;
			}	
		}
		nameLength += 2;
		surnameLength += 2;
		
		printHeaderFooter(surnameLength, nameLength);	
		for ( StudentRecord record : records ) {
				System.out.print("| " + FieldValueGetters.JMBAG.get(record) + " |" );
				String lastName = FieldValueGetters.LAST_NAME.get(record);
				String firstName = FieldValueGetters.FIRST_NAME.get(record);
				printName( lastName, surnameLength );
				printName( firstName, nameLength );	
				int grade = record.getFinalGrade();
				System.out.println( " " + grade + " |" );
		}		
		printHeaderFooter(surnameLength, nameLength);	
	}
	 
	/**
	 * Prints the header or footer 'line'.
	 * @param surnameLength
	 * 		Length of the longest surname of all the filtered records.
	 * @param nameLength
	 * 		Length of the longest name of all the filtered records.
	 */
	private static void printHeaderFooter( int surnameLength, int nameLength ) {

		System.out.print( "+============+");
		for ( int i = 0; i < surnameLength; i++ ) {
			System.out.print( "=" );
		}
		System.out.print( "+" );
		for ( int j = 0; j < nameLength; j++ ) {
			System.out.print( "=" );
		}
		System.out.println( "+===+" );
	}
	
	/**
	 * Prints name in a specific format.
	 * @param name
	 * 		Name of the filtered record.
	 * @param nameLength
	 *		Length of the longest name of all the filtered records.
	 * 		
	 */
	private static void printName( String name, int nameLength ) {
		System.out.print( " " + name );
		for ( int i = 0; i < nameLength - name.length() - 1; i++ ) {
			System.out.print( " ");
		}
		System.out.print( "|" );
	}

}
