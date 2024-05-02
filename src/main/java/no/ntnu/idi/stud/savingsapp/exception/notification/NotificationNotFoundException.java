package no.ntnu.idi.stud.savingsapp.exception.notification;

/**
 * Exception thrown when attempting to retrieve a Notification that does not exist.
 */
public class NotificationNotFoundException extends RuntimeException {
  /**
   * Constructs a NotificationNotFoundException with the default message.
   */
  public NotificationNotFoundException() {
    super("Notification is not found");
  }
}
