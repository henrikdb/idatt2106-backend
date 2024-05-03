package no.ntnu.idi.stud.savingsapp.exception.badge;

/**
 * Exception thrown when attempting to retrieve a badge that does not exist.
 */
public class BadgeNotFoundException extends RuntimeException {

	/**
	 * Constructs an BadgeNotFoundException with default message.
	 */
	public BadgeNotFoundException() {
		super("Badge is not found");
	}

}
