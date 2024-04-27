package no.ntnu.idi.stud.savingsapp.dto.goal;

import lombok.Data;
import no.ntnu.idi.stud.savingsapp.model.configuration.ChallengeType;

@Data
public final class ChallengeTemplateDTO {

	private long id;

	private String challengeText;

	private int challengeMinLength;

	private int challengeMaxLength;

	private ChallengeType challengeType;

}
