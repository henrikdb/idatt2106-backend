package no.ntnu.idi.stud.savingsapp.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import no.ntnu.idi.stud.savingsapp.validation.impl.EnumeratorValidator;

import java.lang.annotation.*;

/**
 * Annotation for validating enum values. This annotation is used to mark fields or
 * parameters that represent enums, ensuring that they meet specific validation criteria
 * defined by the associated validator.
 */
@Documented
@Constraint(validatedBy = EnumeratorValidator.class)
@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER, ElementType.TYPE_USE })
@Retention(RetentionPolicy.RUNTIME)
public @interface Enumerator {

	/**
	 * Specifies the enum class that represents the valid values for the annotated
	 * element.
	 * @return The enum class representing valid values.
	 */
	Class<? extends Enum<?>> value();

	/**
	 * Specifies whether the annotated element is allowed to be null.
	 * @return True if the annotated element can be null, false otherwise.
	 */
	boolean nullable() default true;

	/**
	 * Specifies the default error message that will be used when the validation fails.
	 * @return The default error message.
	 */
	String message() default "Invalid enum value";

	/**
	 * Specifies the groups to which this constraint belongs.
	 * @return An array of group classes.
	 */
	Class<?>[] groups() default {};

	/**
	 * Specifies additional metadata about the annotation.
	 * @return An array of payload classes.
	 */
	Class<? extends Payload>[] payload() default {};

}
