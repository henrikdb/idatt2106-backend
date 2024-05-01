package no.ntnu.idi.stud.savingsapp.exception.badge;

public class BadgeNotFoundException extends RuntimeException {

  /**
   * Constructs an BadgeNotFoundException with default message.
   */
  public BadgeNotFoundException() {
    super("Badge is not found");
  }

}
