package no.ntnu.idi.stud.savingsapp.validation.impl;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import no.ntnu.idi.stud.savingsapp.properties.UserProperties;
import no.ntnu.idi.stud.savingsapp.validation.Password;

import java.util.regex.Pattern;

/**
 * Validator for the Password constraint. This validator ensures that a password meets the
 * specified criteria defined in the application properties.
 */
public final class PasswordValidator implements ConstraintValidator<Password, String> {

	private Password passwordConstraint;

	public Pattern pattern;

	/**
	 * Initializes the validator.
	 * @param password The Password annotation.
	 */
	@Override
	public void initialize(Password password) {
		passwordConstraint = password;
		if (pattern == null) {
			pattern = Pattern.compile(UserProperties.PASS_REGEX);
		}
	}

	/**
	 * Validates a password.
	 * @param password The password to validate.
	 * @param context The constraint validator context.
	 * @return true if the password is valid, false otherwise.
	 */
	@Override
	public boolean isValid(String password, ConstraintValidatorContext context) {
		String message = null;
		if (password == null && !passwordConstraint.nullable()) {
			message = UserProperties.PASS_EMPTY;
		}
		else if (password != null && (password.length() < UserProperties.PASS_LEN_MIN
				|| password.length() > UserProperties.PASS_LEN_MAX)) {
			message = UserProperties.PASS_LEN_MSG;
		}
		else if (password != null && !pattern.matcher(password).matches()) {
			message = UserProperties.PASS_REGEX_MSG;
		}

		if (message != null) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(message).addConstraintViolation();
			return false;
		}

		return true;
	}

}
