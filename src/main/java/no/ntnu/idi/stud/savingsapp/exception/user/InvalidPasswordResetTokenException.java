package no.ntnu.idi.stud.savingsapp.exception.user;

/**
 * Exception thrown when a user attempts to reset a password with an invalid token.
 */
public final class InvalidPasswordResetTokenException extends RuntimeException {

  /**
   * Constructs a InvalidPasswordResetTokenException with the default message.
   */
  public InvalidPasswordResetTokenException() {
    super("Invalid token");
  }
}
