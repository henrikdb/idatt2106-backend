package no.ntnu.idi.stud.savingsapp.dto.auth;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import no.ntnu.idi.stud.savingsapp.dto.configuration.ConfigurationDTO;
import no.ntnu.idi.stud.savingsapp.validation.Name;
import no.ntnu.idi.stud.savingsapp.validation.Password;

/**
 * Represents a sign-up request used for user registration.
 */
@Data
public final class SignUpRequest {

	@Name
	private String firstName;

	@Name
	private String lastName;

	@Email(message = "Invalid email")
	private String email;

	@Password
	private String password;

	@Valid
	@NotNull(message = "Configuration is required")
	private ConfigurationDTO configuration;

}
