package no.ntnu.idi.stud.savingsapp.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import no.ntnu.idi.stud.savingsapp.validation.impl.PasswordValidator;

import java.lang.annotation.*;

/**
 * Annotation for validating passwords.
 * This annotation is used to mark fields or parameters that represent passwords,
 * ensuring that they meet specific validation criteria defined by the associated validator.
 */
@Documented
@Constraint(validatedBy = PasswordValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface Password {

  /**
   * Specifies whether the annotated element is allowed to be null.
   * @return True if the annotated element can be null, false otherwise.
   */
  boolean nullable() default false;

  /**
   * Specifies the default error message that will be used when the validation fails.
   *
   * @return The default error message.
   */
  String message() default "Invalid password";

  /**
   * Specifies the groups to which this constraint belongs.
   *
   * @return An array of group classes.
   */
  Class<?>[] groups() default {};

  /**
   * Specifies additional metadata about the annotation.
   *
   * @return An array of payload classes.
   */
  Class<? extends Payload>[] payload() default {};

}
