package no.ntnu.idi.stud.savingsapp.exception.goal;


public final class GoalNotFoundException extends RuntimeException {

  public GoalNotFoundException() { super("Goal not found");}

  public GoalNotFoundException(String string) {super(string);}
}
