package hr.fer.zemris.java.hw05.db;

/**
 * An interface of an object responsible for obtaining a requested
 * student record.
 * 
 * @author Margarita Tolja
 *
 */
public interface IFieldValueGetter {

	/**
	 * Gets one fields from given record.
	 * @param record
	 * 		Student record.
	 * @return
	 * 		One field of a record.
	 */
	public String get( StudentRecord record );
}
