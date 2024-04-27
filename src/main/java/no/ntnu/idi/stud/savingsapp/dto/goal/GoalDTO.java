package no.ntnu.idi.stud.savingsapp.dto.goal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import no.ntnu.idi.stud.savingsapp.dto.user.UserDTO;

import java.sql.Timestamp;
import java.util.List;

@Data
@NoArgsConstructor
public final class GoalDTO {

	private Long id;

	@NonNull
	private String goalName;

	@NonNull
	private String description;

	@NonNull
	private int targetAmount;

	@NonNull
	private Timestamp targetDate;

	@NonNull
	private Timestamp completedAt;

	@NonNull
	private Timestamp createdAt;

	private List<ChallengeDTO> challenges;

	private List<ParticipantDTO> participants;

}
