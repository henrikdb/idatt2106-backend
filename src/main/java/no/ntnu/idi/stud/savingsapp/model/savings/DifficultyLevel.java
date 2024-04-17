package no.ntnu.idi.stud.savingsapp.model.savings;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "difficulty_level")
public class DifficultyLevel {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "difficulty_level_id")
  private Long id;

  @NonNull
  @Column(name = "difficulty_level_text", nullable = false)
  private String diffucultyLevelText;

  @OneToMany(cascade = CascadeType.ALL)
  @JoinColumn(name = "difficulty_level_id")
  private List<SavingChallenge> savingChallengeList;
}
