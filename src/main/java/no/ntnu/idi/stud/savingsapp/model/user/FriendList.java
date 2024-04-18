package no.ntnu.idi.stud.savingsapp.model.user;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
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
@Entity
@Table(name = "friend_list")
public class FriendList {

  @EmbeddedId
  private FriendListId id;

  @NonNull
  @Column(name = "status", nullable = false)
  private boolean pending;

  @NonNull
  @Column(name = "created_at")
  private Timestamp createdAt;
}
