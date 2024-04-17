package no.ntnu.idi.stud.savingsapp.model.saving;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.sql.Timestamp;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "saving_goal")
public class SavingGoal {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "saving_goal_id")
  private Long id;

  @NonNull
  @Column(name = "saving_goal_name", nullable = false)
  private String savingGoalName;

  @NonNull
  @Column(name = "target_amount", nullable = false)
  private int targetAmount;

  @NonNull
  @Column(name = "target_date", nullable = false)
  private Timestamp targetDate;

  @NonNull
  @Column(name = "completed_at", nullable = false)
  private Timestamp completedAt;

  @NonNull
  @Column(name = "created_at", nullable = false)
  private Timestamp createdAt;

  @OneToMany(cascade = CascadeType.ALL)
  private List<SavingChallenge> savingChallenges;
}
