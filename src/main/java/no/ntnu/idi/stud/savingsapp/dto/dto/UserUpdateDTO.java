package no.ntnu.idi.stud.savingsapp.dto.dto;

import jakarta.validation.constraints.Email;
import lombok.Data;
import no.ntnu.idi.stud.savingsapp.model.ChallengeType;
import no.ntnu.idi.stud.savingsapp.model.ChangeWilling;
import no.ntnu.idi.stud.savingsapp.model.Experience;
import no.ntnu.idi.stud.savingsapp.validation.Enumerator;
import no.ntnu.idi.stud.savingsapp.validation.Name;
import no.ntnu.idi.stud.savingsapp.validation.Password;

import java.util.List;

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

  @Enumerator(value = ChangeWilling.class)
  private String commitment;

  @Enumerator(value = Experience.class)
  private String experience;

  private List<@Enumerator(value = ChallengeType.class, nullable = false) String> challengeTypes;
}
