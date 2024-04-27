package no.ntnu.idi.stud.savingsapp.model.goal;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.sql.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "goal_challenge")
public class GoalChallenge {

	@EmbeddedId
	private GoalChallengeId id;

	@NonNull
	@Column(name = "created_at", nullable = false)
	private Timestamp createdAt;

}
