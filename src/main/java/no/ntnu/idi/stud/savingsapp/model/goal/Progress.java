package no.ntnu.idi.stud.savingsapp.model.goal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.sql.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

/**
 * Represents the progress of daily challenges.
 * This entity keeps track of when challenges are completed, and whihc day they need to be
 * completed at.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "progress")
public class Progress {

  @Id()
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "progess_id")
  private Long id;

  @Column(name = "challenge_day", nullable = false)
  private int day;

  @Column(name = "progress_amount", nullable = false)
  private BigDecimal amount;

  @NonNull
  @Column(name = "completed_at", nullable = false)
  private Timestamp completedAt;
}
