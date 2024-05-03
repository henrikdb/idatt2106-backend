package no.ntnu.idi.stud.savingsapp.model.user;

import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

/**
 * Composite Primary Key used in {@link Friend} entity.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class FriendId implements Serializable {

	@NonNull
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	@NonNull
	@ManyToOne
	@JoinColumn(name = "friend_id")
	private User friend;

}
