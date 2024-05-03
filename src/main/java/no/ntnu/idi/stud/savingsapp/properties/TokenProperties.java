package no.ntnu.idi.stud.savingsapp.properties;

import org.springframework.stereotype.Component;

/**
 * Configuration properties for token generation.
 */
@Component
public final class TokenProperties {

	/**
	 * The secret key used for token generation.
	 */
	public static final String SECRET = "topsecretkey";

	/**
	 * The duration of the token validity in minutes.
	 */
	public static final int DURATION = 30;

}
