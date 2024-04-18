package no.ntnu.idi.stud.savingsapp.model.user;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class FriendListId implements Serializable {

  private Long userId;
  private Long friendId;
}
