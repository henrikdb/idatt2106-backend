package no.ntnu.idi.stud.savingsapp.dto.user;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import lombok.Data;
import no.ntnu.idi.stud.savingsapp.dto.configuration.ConfigurationDTO;
import no.ntnu.idi.stud.savingsapp.validation.Name;

@Data
public final class UserUpdateDTO {

	@Name(nullable = true)
	private String firstName;

	@Name(nullable = true)
	private String lastName;

	@Email(message = "Invalid email")
	private String email;

	private Long profileImage;

	private Long bannerImage;

	private Long savingsAccountBBAN;

	private Long checkingAccountBBAN;

	@Valid
	private ConfigurationDTO configuration;

}
