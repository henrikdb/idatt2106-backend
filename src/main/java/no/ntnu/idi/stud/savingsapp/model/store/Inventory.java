package no.ntnu.idi.stud.savingsapp.model.store;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a user's inventory.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "inventory")
public class Inventory {

	@EmbeddedId
	private InventoryId inventoryId;

	@Column(name = "bought_at")
	private Timestamp boughtAt;

}
