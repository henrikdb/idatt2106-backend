package no.ntnu.idi.stud.savingsapp.model.user;

import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class BadgeUserId implements Serializable {

  @ManyToOne
  @JoinColumn(name = "badge_id")
  private Badge badge;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;


}
