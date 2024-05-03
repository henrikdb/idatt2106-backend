package no.ntnu.idi.stud.savingsapp.model.store;

import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import no.ntnu.idi.stud.savingsapp.model.user.User;

/**
 * Represents the many-to-many relation between user and item entities.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class InventoryId implements Serializable {

	@ManyToOne
	@JoinColumn(name = "item_id")
	private Item item;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

}
