package no.ntnu.idi.stud.savingsapp.exception.user;

/**
 * Exception thrown when a user attempts an action they don't have permission to.
 */
public final class PermissionDeniedException extends RuntimeException {

  /**
   * Constructs a PermissionDeniedException with the default message.
   */
  public PermissionDeniedException() {
    super("Permission denied");
  }
}
