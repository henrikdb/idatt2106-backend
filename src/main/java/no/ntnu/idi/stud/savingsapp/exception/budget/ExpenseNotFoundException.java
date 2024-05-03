package no.ntnu.idi.stud.savingsapp.exception.budget;

/**
 * Exception thrown when attempting to retrieve an expense that does not exist.
 */
public class ExpenseNotFoundException extends RuntimeException {

	/**
	 * Constructs an ExpenseNotFoundException with default message.
	 */
	public ExpenseNotFoundException() {
		super("Expense is not found");
	}

}
