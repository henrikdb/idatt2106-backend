package no.ntnu.idi.stud.savingsapp.model.savings;

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
public class SavingGoalChallengeId implements Serializable {

  @ManyToOne
  @JoinColumn(name = "saving_goal_id")
  private SavingGoal savingGoal;

  @ManyToOne
  @JoinColumn(name = "saving_challenge_id")
  private SavingChallenge savingChallenge;
}
