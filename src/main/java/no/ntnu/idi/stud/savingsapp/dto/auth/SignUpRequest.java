package no.ntnu.idi.stud.savingsapp.dto.auth;

import jakarta.validation.constraints.Email;
import lombok.Data;
import no.ntnu.idi.stud.savingsapp.model.ChallengeType;
import no.ntnu.idi.stud.savingsapp.model.ChangeWilling;
import no.ntnu.idi.stud.savingsapp.model.Experience;
import no.ntnu.idi.stud.savingsapp.validation.Enumerator;
import no.ntnu.idi.stud.savingsapp.validation.Name;
import no.ntnu.idi.stud.savingsapp.validation.Password;

import java.util.List;

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

  @Enumerator(value = ChangeWilling.class, nullable = false)
  private String changeWilling;

  @Enumerator(value = Experience.class, nullable = false)
  private String experience;

  private List<@Enumerator(value = ChallengeType.class, nullable = false) String> challenges;

}
