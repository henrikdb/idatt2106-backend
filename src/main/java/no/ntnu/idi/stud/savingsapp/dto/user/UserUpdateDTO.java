package no.ntnu.idi.stud.savingsapp.dto.user;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import no.ntnu.idi.stud.savingsapp.dto.configuration.ConfigurationDTO;
import no.ntnu.idi.stud.savingsapp.validation.Name;
import no.ntnu.idi.stud.savingsapp.validation.Password;

@Data
public final class UserUpdateDTO {

  @Name(nullable = true)
  private String firstName;

  @Name(nullable = true)
  private String lastName;

  @Email(message = "Invalid email")
  private String email;

  @Password(nullable = true)
  private String password;

  @Valid
  @NotNull(message = "Configuration is required")
  private ConfigurationDTO configuration;
}
