package no.ntnu.idi.stud.savingsapp.model.configuration;

import java.util.Random;

public enum Commitment {

  LITTLE(0.33),
  SOME(0.66),
  MUCH(1);

  private static final Random random = new Random();

  private final double percentage;

  Commitment(double percentage) {
    this.percentage = percentage;
  }

  public int getCheckDays(int totalDays) {
    double p = random.nextDouble(percentage - 0.2) + 0.2;
    System.out.println("p: " + p);
    return (int) (p * totalDays);
  }
}
