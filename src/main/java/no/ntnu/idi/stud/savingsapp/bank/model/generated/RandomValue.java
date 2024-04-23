package no.ntnu.idi.stud.savingsapp.bank.model.generated;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;

public class RandomValue {

  public static BigDecimal generateAccountBalance() {
    Random random = new Random();

    // Generate a random number between 100 and 100,000
    double minBalance = 100.0;
    double maxBalance = 100000.0;
    double randomBalance = minBalance + (maxBalance - minBalance) * random.nextDouble();

    return BigDecimal.valueOf(randomBalance).setScale(2, RoundingMode.HALF_UP);
  }

}
