package no.ntnu.idi.stud.savingsapp.model.user;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import java.sql.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

/**
 * Represents users connected to one another as friends.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FriendList {

  @EmbeddedId
  private FriendListId id;

  @NonNull
  @Column(name = "status", nullable = false)
  private String status;

  @NonNull
  @Column(name = "created_at")
  private Timestamp createdAt;
}
