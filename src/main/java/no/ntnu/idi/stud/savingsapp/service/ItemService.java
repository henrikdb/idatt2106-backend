package no.ntnu.idi.stud.savingsapp.service;

import java.util.List;

import org.springframework.stereotype.Service;

import no.ntnu.idi.stud.savingsapp.model.store.Item;
import no.ntnu.idi.stud.savingsapp.model.user.User;

/**
 * Service interface for item-related operations.
 */
@Service
public interface ItemService {

	/**
	 * Retrieves the inventory of items for a specific user.
	 * @param userId the unique identifier of the user whose inventory is to be retrieved
	 * @return a list of {@link Item} objects representing the user's inventory
	 */
	List<Item> getInventory(Long userId);

	/**
	 * Retrieves a list of all items available in the store.
	 * @return a list of {@link Item} objects representing all items currently available
	 * in the store
	 */
	List<Item> getStore();

	/**
	 * Retrieves a specific item from the store based on its identifier.
	 * @param itemId the unique identifier of the item to be retrieved
	 * @return the {@link Item} corresponding to the provided identifier, or null if no
	 * such item exists
	 */
	Item getItemFromId(Long itemId);

	/**
	 * Adds an item to the inventory of a specified user.
	 * @param user the {@link User} object representing the user to whom the item will be
	 * added
	 * @param item the {@link Item} to be added to the user's inventory
	 */
	Boolean addItem(User user, Item item);

}
