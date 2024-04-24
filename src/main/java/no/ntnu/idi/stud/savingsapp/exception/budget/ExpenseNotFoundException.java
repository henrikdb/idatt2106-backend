package no.ntnu.idi.stud.savingsapp.exception.budget;

public class ExpenseNotFoundException extends RuntimeException {

  /**
   * Constructs an ExpenseNotFoundException with default message.
   */
  public ExpenseNotFoundException() {
    super("Expense is not found");
  }
}
