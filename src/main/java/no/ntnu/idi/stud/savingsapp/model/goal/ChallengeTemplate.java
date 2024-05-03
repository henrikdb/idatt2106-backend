package no.ntnu.idi.stud.savingsapp.model.goal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import no.ntnu.idi.stud.savingsapp.model.configuration.ChallengeType;

import java.math.BigDecimal;

/**
 * Represents a challenge template for generating a {@link Challenge}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "challenge_template")
public class ChallengeTemplate {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "challenge_template_id")
  private Long id;

  @NonNull
  @Column(name = "challenge_text", nullable = false)
  private String text;

  @NonNull
  @Column(name = "challenge_name", nullable = false)
  private String challengeName;

  @Column(name = "challenge_amount", nullable = false)
  private BigDecimal amount;

  @NonNull
  @Enumerated(EnumType.STRING)
  @Column(name = "challenge_type", nullable = false)
  private ChallengeType challengeType;
}
