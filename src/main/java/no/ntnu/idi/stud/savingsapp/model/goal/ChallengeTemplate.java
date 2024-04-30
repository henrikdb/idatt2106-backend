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
  private String challengeText;

  @Column(name = "challenge_amount", nullable = false)
  private int amount;

  @NonNull
  @Enumerated(EnumType.STRING)
  @Column(name = "challenge_type", nullable = false)
  private ChallengeType challengeType;
}
