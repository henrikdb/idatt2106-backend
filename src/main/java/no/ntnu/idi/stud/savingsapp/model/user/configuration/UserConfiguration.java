package no.ntnu.idi.stud.savingsapp.model.user.configuration;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.sql.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user_configuration")
public class UserConfiguration {

  @EmbeddedId
  @Column(name = "user_configuration_id")
  private UserConfigurationId id;

  @Column(name = "updated_at")
  private Timestamp updatedAt;
}
