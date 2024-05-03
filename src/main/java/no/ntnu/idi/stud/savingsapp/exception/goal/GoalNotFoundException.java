package no.ntnu.idi.stud.savingsapp.exception.goal;

/**
 * Exception thrown when attempting to retrieve a goal that does not exist.
 */
public final class GoalNotFoundException extends RuntimeException {

	/**
	 * Constructs a GoalNotFoundException with default message.
	 */
	public GoalNotFoundException() {
		super("Goal not found");
	}

	/**
	 * Constructs a GoalNotFoundException with custom message.
	 * @param string the custom exception message
	 */
	public GoalNotFoundException(String string) {
		super(string);
	}

}
