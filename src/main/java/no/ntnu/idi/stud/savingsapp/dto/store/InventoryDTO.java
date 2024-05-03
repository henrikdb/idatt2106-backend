package no.ntnu.idi.stud.savingsapp.dto.store;

import java.sql.Timestamp;

import lombok.Data;

@Data
public final class InventoryDTO {

	private long id;

	private String itemName;

	private long imageId;

	private Timestamp boughtAt;

}
