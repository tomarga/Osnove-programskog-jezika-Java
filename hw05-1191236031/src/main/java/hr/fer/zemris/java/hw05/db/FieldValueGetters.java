package hr.fer.zemris.java.hw05.db;

/**
 * A class that offers three different field value getters(first name, last name and jmbag).
 * 
 * @author Margarita Tolja
 *
 */
public class FieldValueGetters {

	public static final IFieldValueGetter FIRST_NAME = ( record ) -> record.getFirstName();
	public static final IFieldValueGetter LAST_NAME = ( record ) -> record.getLastName();
	public static final IFieldValueGetter JMBAG = ( record ) -> record.getJmbag();

}
