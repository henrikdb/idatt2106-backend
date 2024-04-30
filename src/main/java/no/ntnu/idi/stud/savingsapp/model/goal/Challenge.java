package no.ntnu.idi.stud.savingsapp.model.goal;

import jakarta.persistence.*;

import java.sql.Timestamp;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

/**
 * Represents a challenge need to achieve a {@link Goal}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "challenge")
public class Challenge {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "challenge_id")
  private Long id;

  @Column(name = "potential_saving_amount", nullable = false)
  private int potentialSavingAmount;

  @Column(name = "points", nullable = false)
  private int points;

  @Column(name = "days", nullable = false)
  private int days;

  @NonNull
  @Column(name = "created_at", nullable = false)
  private Timestamp createdAt;

  @ManyToOne
  @JoinColumn(name = "goal_id")
  private Goal goal;

  @ManyToOne
  @JoinColumn (name = "challenge_template_id")
  private ChallengeTemplate challengeTemplate;

  @OneToMany(cascade = CascadeType.ALL)
  @JoinColumn(name = "challenge_id")
  private List<DailyChallengeProgress> dailyChallengeProgressList;
}
