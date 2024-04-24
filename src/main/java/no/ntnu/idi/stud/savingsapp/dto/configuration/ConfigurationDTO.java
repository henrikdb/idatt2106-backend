package no.ntnu.idi.stud.savingsapp.dto.configuration;

import lombok.Data;
import lombok.NonNull;
import no.ntnu.idi.stud.savingsapp.model.configuration.ChallengeType;
import no.ntnu.idi.stud.savingsapp.validation.Enumerator;

import java.util.List;

@Data
public class ConfigurationDTO {

  @NonNull
  private String commitment;

  @NonNull
  private String experience;

  private List<@Enumerator(value = ChallengeType.class) String> challengeTypes;
}
