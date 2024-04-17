package no.ntnu.idi.stud.savingsapp.model.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "points")
public class Point {

  @Id
  @OneToOne
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  @NonNull
  @Column(name = "current_points", nullable = false)
  private int currentPoints;

  @NonNull
  @Column(name = "total_earned_points", nullable = false)
  private int totalEarnedPoints;
}
