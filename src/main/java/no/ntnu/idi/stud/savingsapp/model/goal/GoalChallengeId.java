package no.ntnu.idi.stud.savingsapp.model.goal;

import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class GoalChallengeId implements Serializable {

	@ManyToOne
	@JoinColumn(name = "goal_id")
	private Goal goal;

	@ManyToOne
	@JoinColumn(name = "challenge_id")
	private Challenge challenge;

}
