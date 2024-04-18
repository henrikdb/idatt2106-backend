package no.ntnu.idi.stud.savingsapp.model.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.sql.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "badge_user")
public class BadgeUser {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "badge_user_id")
  private Long id;

  @ManyToOne
  @JoinColumn(name = "badge_id")
  private Badge badge;


  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;

  @Column(name = "earned_at")
  private Timestamp earnedAt;
}
