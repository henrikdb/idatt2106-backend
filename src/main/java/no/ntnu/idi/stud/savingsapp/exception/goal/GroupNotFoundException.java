package no.ntnu.idi.stud.savingsapp.exception.goal;

public final class GroupNotFoundException extends RuntimeException{

  /**
   * Constructs a GroupNotFoundException with default message.
   */
  public GroupNotFoundException() { super("Group not found");}

  /**
   * Constructs a GroupNotFoundException with custom message.
   *
   * @param string the custom exception message
   */
  public GroupNotFoundException(String string) {super(string);}
}
