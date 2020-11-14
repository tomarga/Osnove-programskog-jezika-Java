package hr.fer.zemris.java.hw05.db;

/**
 * 
 * An interface that implements a filter method for student record objects.
 * 
 * @author Margarita Tolja
 *
 */
public interface IFilter {
	
	/**
	 * Checks if the student record is acceptable by some standards.
	 * @param record
	 * 		The student record of interest.
	 * @return
	 * 		<code>true</code> if the record is accepted;
	 * 		<code>false</code> otherwise.
	 */
	public boolean accepts( StudentRecord record );
}
