package no.ntnu.idi.stud.savingsapp.model.savings;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

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

  @NonNull
  @Column(name = "potential_saving_amount", nullable = false)
  private int potentialSavingAmount;

  @NonNull
  @Column(name = "points", nullable = false)
  private int points;

  @NonNull
  @Column(name = "days", nullable = false)
  private int days;

  @NonNull
  @Column(name = "created_at", nullable = false)
  private Timestamp createdAt;

  @OneToMany(cascade = CascadeType.ALL)
  @JoinColumn(name = "challenge_id")
  private List<DailyChallengeProgress> dailyChallengeProgressList;
}
