package no.ntnu.idi.stud.savingsapp.dto.configuration;

import lombok.Data;
import lombok.NoArgsConstructor;
import no.ntnu.idi.stud.savingsapp.model.configuration.ChallengeType;
import no.ntnu.idi.stud.savingsapp.validation.Enumerator;

import java.util.List;

@Data
@NoArgsConstructor
public class ConfigurationDTO {

	private String commitment;

	private String experience;

	private List<@Enumerator(value = ChallengeType.class) String> challengeTypes;

}
