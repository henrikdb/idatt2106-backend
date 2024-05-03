package no.ntnu.idi.stud.savingsapp.exception.goal;

/**
 * Exception thrown when attempting to retrieve a challenge that does not exist.
 */
public final class ChallengeNotFoundException extends RuntimeException {

  /**
   * Constructs a ChallengeNotFoundException with default message.
   */
  public ChallengeNotFoundException() { super("Challenge not found");}

  /**
   * Constructs a ChallengeNotFoundException with custom message.
   *
   * @param string the custom exception message
   */
  public ChallengeNotFoundException(String string) {super(string);}
}
