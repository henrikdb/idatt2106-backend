package no.ntnu.idi.stud.savingsapp.exception.goal;

/**
 * Exception thrown when attempting to choose an invalid challenge day.
 */
public final class InvalidChallengeDayException extends RuntimeException {

	/**
	 * Constructs a InvalidChallengeDayException with default message.
	 */
	public InvalidChallengeDayException() {
		super("Invalid challenge day");
	}

	/**
	 * Constructs a InvalidChallengeDayException with custom message.
	 * @param string the custom exception message
	 */
	public InvalidChallengeDayException(String string) {
		super(string);
	}

}
