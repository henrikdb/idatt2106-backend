package no.ntnu.idi.stud.savingsapp.model.savings;

import jakarta.persistence.*;

import java.sql.Timestamp;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import no.ntnu.idi.stud.savingsapp.model.user.User;

/**
 * Represents a saving goal.
 * This entity has a list of generated {@link Challenge SavingChallanges} associated with it.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "goal")
public class Goal {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "goal_id")
  private Long id;

  @NonNull
  @Column(name = "goal_name", nullable = false)
  private String GoalName;

  @NonNull
  @Column(name = "goal_description", nullable = false)
  private String description;

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

  @OneToMany
  @JoinColumn(name = "challenge_id")
  private List<Challenge> challenges;

  @ManyToOne
  @JoinTable(name = "goal_user",
  joinColumns = @JoinColumn(name = "user_id"),
  inverseJoinColumns = @JoinColumn(name = "goal_id"))
  private User creator;
}
