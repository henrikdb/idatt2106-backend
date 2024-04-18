package no.ntnu.idi.stud.savingsapp.model.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import java.sql.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

/**
 * Represents the streak of a user.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "streak")
public class Streak {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "streak_id")
  private Long id;

  @NonNull
  @Column(name = "current_streak", nullable = false)
  private int currentStreak;

  @NonNull
  @Column(name = "current_streak_created_at", nullable = false)
  private Timestamp currentStreakCreatedAt;

  @NonNull
  @Column(name = "current_streak_updated_at", nullable = false)
  private Timestamp currentStreakUpdatedAt;

  @NonNull
  @Column(name = "highest_streak", nullable = false)
  private int highestStreak;

  @NonNull
  @Column(name = "highest_streak_ended_at")
  private Timestamp highestStreakEndedAt;

}
