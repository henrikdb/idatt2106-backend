package no.ntnu.idi.stud.savingsapp.model.user.configuration;

import jakarta.persistence.Embeddable;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import no.ntnu.idi.stud.savingsapp.model.user.User;

/**
 * Composite Primary Key used in {@link UserConfiguration} entity.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class UserConfigurationId implements Serializable {

  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;

  @ManyToOne
  @JoinColumn(name = "question_id")
  private Question question;

  @ManyToOne
  @JoinColumn(name = "answer_id")
  private Answer answer;
}
