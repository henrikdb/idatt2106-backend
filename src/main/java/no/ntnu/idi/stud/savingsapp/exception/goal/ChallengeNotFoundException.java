package no.ntnu.idi.stud.savingsapp.exception.goal;

public final class ChallengeNotFoundException extends RuntimeException {

  public ChallengeNotFoundException() { super("Challenge not found");}

  public ChallengeNotFoundException(String string) {super(string);}
}
