package no.ntnu.idi.stud.savingsapp.model.user;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

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
  private String criteria;

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "badge")
  private List<BadgeUser> badgeUserList;
}
