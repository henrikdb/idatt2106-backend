package no.ntnu.idi.stud.savingsapp.exception.budget;

public class BudgetNotFoundException extends RuntimeException {

  /**
   * Constructs an BudgetNotFoundException with default message.
   */
  public BudgetNotFoundException() {
    super("Budget is not found");
  }
}
