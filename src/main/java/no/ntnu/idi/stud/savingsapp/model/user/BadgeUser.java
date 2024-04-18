package no.ntnu.idi.stud.savingsapp.model.user;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
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

  @EmbeddedId
  private BadgeUserId badgeUserId;

  @Column(name = "earned_at")
  private Timestamp earnedAt;
}
