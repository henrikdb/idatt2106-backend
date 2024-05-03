package no.ntnu.idi.stud.savingsapp.model.configuration;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.List;
import no.ntnu.idi.stud.savingsapp.model.user.User;

/**
 * Represents a {@link User} objects configuration details.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "configuration")
public class Configuration {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "configuration_id")
  private Long id;

  @NonNull
  @Enumerated(EnumType.STRING)
  @Column(name = "commitment", nullable = false)
  private Commitment commitment;

  @NonNull
  @Enumerated(EnumType.STRING)
  @Column(name = "experience", nullable = false)
  private Experience experience;

  @ElementCollection(targetClass = ChallengeType.class)
  @JoinTable(name = "configuration_challenges", joinColumns = @JoinColumn(name = "configuration_id"))
  @Column(name = "challenge_type", nullable = false)
  @Enumerated(EnumType.STRING)
  private List<ChallengeType> challengeTypes;
}
