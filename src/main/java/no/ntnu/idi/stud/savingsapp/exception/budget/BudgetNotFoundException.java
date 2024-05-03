package no.ntnu.idi.stud.savingsapp.exception.budget;

/**
 * Exception thrown when attempting to retrieve a Budget that does not exist.
 */
public class BudgetNotFoundException extends RuntimeException {

  /**
   * Constructs an BudgetNotFoundException with default message.
   */
  public BudgetNotFoundException() {
    super("Budget is not found");
  }
}
