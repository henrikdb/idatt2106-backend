package no.ntnu.idi.stud.savingsapp.exception.goal;

public final class InvalidChallengeDayException extends RuntimeException{

  public InvalidChallengeDayException() { super("Invalid challenge day");}

  public InvalidChallengeDayException(String string) {super(string);}
}
