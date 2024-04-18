package no.ntnu.idi.stud.savingsapp.model.user.configuration;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Composite Primary Key used in {@link UserConfiguration} entity.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class UserConfigurationId implements Serializable {
  private Long userId;
  private Long questionId;
  private Long answerId;
}
