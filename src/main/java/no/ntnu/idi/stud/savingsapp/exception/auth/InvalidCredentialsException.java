package no.ntnu.idi.stud.savingsapp.exception.auth;

/**
 * Exception thrown when authentication fails due to invalid credentials.
 */
public class InvalidCredentialsException extends RuntimeException {

	/**
	 * Constructs an InvalidCredentialsException with the default message.
	 */
	public InvalidCredentialsException() {
		super("Invalid credentials");
	}

}
