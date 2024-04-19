package no.ntnu.idi.stud.savingsapp.validation.impl;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import no.ntnu.idi.stud.savingsapp.properties.UserProperties;
import no.ntnu.idi.stud.savingsapp.validation.Name;

import java.util.regex.Pattern;

/**
 * Validator for the Name constraint.
 * This validator ensures that a name meets the specified
 * criteria defined in the application properties.
 */
public final class NameValidator implements ConstraintValidator<Name, String> {

  private Name nameConstraint;
  private Pattern pattern;

  /**
   * Initializes the validator.
   *
   * @param name The Name annotation.
   */
  @Override
  public void initialize(Name name) {
    this.nameConstraint = name;
    if (pattern == null) {
      pattern = Pattern.compile(UserProperties.NAME_REGEX);
    }
  }

  /**
   * Validates a name.
   *
   * @param username The name to validate.
   * @param context The constraint validator context.
   * @return true if the name is valid, false otherwise.
   */
  @Override
  public boolean isValid(String name, ConstraintValidatorContext context) {
    String message = null;
    if (name == null && !this.nameConstraint.nullable()) {
      message = UserProperties.NAME_EMPTY;
    } else if (name != null && (name.length() < UserProperties.NAME_LEN_MIN || name.length() > UserProperties.NAME_LEN_MAX)) {
      message = UserProperties.NAME_LEN_MSG;
    } else if (name != null && !pattern.matcher(name).matches()) {
      message = UserProperties.NAME_REGEX_MSG;
    }

    if (message != null) {
      context.disableDefaultConstraintViolation();
      context.buildConstraintViolationWithTemplate(message).addConstraintViolation();
      return false;
    }

    return true;
  }
}
