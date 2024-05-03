package no.ntnu.idi.stud.savingsapp.dto.goal;

import lombok.Data;
import no.ntnu.idi.stud.savingsapp.model.configuration.ChallengeType;

import java.math.BigDecimal;

@Data
public final class ChallengeTemplateDTO {

	private long id;

	private String templateName;

	private String text;

	private BigDecimal amount;

	private ChallengeType type;

}
