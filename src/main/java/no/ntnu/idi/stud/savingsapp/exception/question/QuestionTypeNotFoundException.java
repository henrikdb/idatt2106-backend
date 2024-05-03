package no.ntnu.idi.stud.savingsapp.exception.question;

/**
 * Exception thrown when attempting to retrieve a question of a specific type that does
 * not exist in the system.
 */
public class QuestionTypeNotFoundException extends RuntimeException {

	/**
	 * Constructs a QuestionTypeNotFoundException with the default message.
	 */
	public QuestionTypeNotFoundException(String type) {
		super("Question for type '" + type + "' not found");
	}

}
