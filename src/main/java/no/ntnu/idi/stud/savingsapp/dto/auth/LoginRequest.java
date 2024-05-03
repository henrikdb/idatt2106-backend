package no.ntnu.idi.stud.savingsapp.dto.auth;

import jakarta.validation.constraints.Email;
import lombok.Data;
import no.ntnu.idi.stud.savingsapp.validation.Password;

/**
 * Represents a login request used for authentication purposes.
 */
@Data
public final class LoginRequest {

	@Email(message = "Invalid email")
	private String email;

	@Password
	private String password;

}
