package no.ntnu.idi.stud.savingsapp.model.savings;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import no.ntnu.idi.stud.savingsapp.model.ChallengeType;

/**
 * Represents a challenge need to achieve a {@link SavingGoal}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "saving_challenge")
public class SavingChallenge {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "saving_challenge_id")
  private Long id;

  @NonNull
  @Column(name = "saving_challenge_text", nullable = false)
  private String savingChallengeText;

  @NonNull
  @Column(name = "potential_saving_amount", nullable = false)
  private int potentialSavingAmount;

  @NonNull
  @Column(name = "points", nullable = false)
  private int points;

  @OneToMany(cascade = CascadeType.ALL)
  @JoinColumn(name = "saving_challenge_id")
  private List<DailyChallengeProgress> dailyChallengeProgressList;

  @NonNull
  @Enumerated(EnumType.STRING)
  @Column(name = "challenge_type", nullable = false)
  private ChallengeType challengeType;
}
