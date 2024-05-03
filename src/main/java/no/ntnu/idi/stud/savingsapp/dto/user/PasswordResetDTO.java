package no.ntnu.idi.stud.savingsapp.dto.user;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import no.ntnu.idi.stud.savingsapp.validation.Password;

@Data
public final class PasswordResetDTO {

  @NotNull(message = "Token is required")
  private String token;

  @Password
  private String password;

}
