package no.ntnu.idi.stud.savingsapp.model.user;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import no.ntnu.idi.stud.savingsapp.model.image.Image;

/**
 * Represents a badge a user can earn.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "badge")
public class Badge {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "badge_id")
  private Long id;

  @NonNull
  @Column(name = "badge_name", nullable = false)
  private String badgeName;

  @NonNull
  @Column(name = "criteria", nullable = false)
  private int criteria;

  @OneToOne
  @JoinColumn(name = "image_id")
  private Image image;

  @OneToMany(cascade = CascadeType.ALL)
  @JoinColumn(name = "badge_id")
  private List<BadgeUser> badgeUserList;
}
