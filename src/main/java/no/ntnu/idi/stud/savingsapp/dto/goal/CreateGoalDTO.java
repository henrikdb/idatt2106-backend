package no.ntnu.idi.stud.savingsapp.dto.goal;

import lombok.Data;
import lombok.NonNull;

import java.math.BigDecimal;
import java.util.List;

@Data
public final class CreateGoalDTO {

	@NonNull
	private String name;

	@NonNull
	private String description;

	private BigDecimal targetAmount;

	@NonNull
	private String targetDate;

	private List<GroupUserDTO> distribution;

}
