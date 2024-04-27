package no.ntnu.idi.stud.savingsapp.bank.model.generated;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;

/**
 * Class for generating random values. This class is used in the bank application to
 * generate random values for test data.
 */
public class RandomValue {

	/**
	 * Generates a random {@link BigDecimal} number between 100 and 100_000.
	 * @return A random number.
	 */
	public static BigDecimal generateAccountBalance() {
		Random random = new Random();
		double minBalance = 100.0;
		double maxBalance = 100_000.0;
		double randomBalance = minBalance + (maxBalance - minBalance) * random.nextDouble();
		return BigDecimal.valueOf(randomBalance).setScale(2, RoundingMode.HALF_UP);
	}

}
