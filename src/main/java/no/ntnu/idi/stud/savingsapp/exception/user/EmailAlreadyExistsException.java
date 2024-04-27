package no.ntnu.idi.stud.savingsapp.exception.user;

/**
 * Exception thrown when attempting to create a user that already exists in the system.
 */
public final class EmailAlreadyExistsException extends RuntimeException {

	/**
	 * Constructs a UserAlreadyExistsException with the default message.
	 */
	public EmailAlreadyExistsException() {
		super("Email already exists");
	}

}
