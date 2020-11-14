package hr.fer.zemris.java.hw05.db;

/**
 * A class that represents a record for some student.
 * The record consists of student's jmbag, name, surname and final grade.
 * 
 * @author Margarita Tolja
 *
 */
public class StudentRecord {

	private String jmbag;
	private String lastName;
	private String firstName;
	private int finalGrade;
	
	/**
	 * Creates a new student record with information as passed in arguments.
	 * @param jmbag
	 * 		Student's id number.
	 * @param lastName
	 * 		Student's surname.
	 * @param firstName
	 * 		Student's name.
	 * @param finalGrade
	 * 		Student's final grade.
	 */
	public StudentRecord( String jmbag, String lastName, String firstName, int finalGrade ) {
		
		this.jmbag = jmbag;
		this.lastName = lastName;
		this.firstName = firstName;
		this.finalGrade = finalGrade;
	}

	/**
	 * The hash code is based on student's jmbag.
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((jmbag == null) ? 0 : jmbag.hashCode());
		return result;
	}

	/**
	 * The students are equal iff their jmbag numbers are equal.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		StudentRecord other = (StudentRecord) obj;
		if (jmbag == null) {
			if (other.jmbag != null) {
				return false;
			}
		} else if (!jmbag.equals(other.jmbag)) {
			return false;
		}
		return true;
	}

	/**
	 * @return the jmbag
	 */
	public String getJmbag() {
		return jmbag;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @return the finalGrade
	 */
	public int getFinalGrade() {
		return finalGrade;
	}
}
