package no.ntnu.idi.stud.savingsapp.dto.user;

import lombok.Data;
import no.ntnu.idi.stud.savingsapp.validation.Password;

@Data
public final class PasswordUpdateDTO {

  @Password
  private String oldPassword;

  @Password
  private String newPassword;

}
