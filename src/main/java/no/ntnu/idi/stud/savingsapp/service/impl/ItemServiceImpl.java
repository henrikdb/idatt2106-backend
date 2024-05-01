package no.ntnu.idi.stud.savingsapp.service.impl;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import no.ntnu.idi.stud.savingsapp.model.store.Inventory;
import no.ntnu.idi.stud.savingsapp.model.store.InventoryId;
import no.ntnu.idi.stud.savingsapp.model.store.Item;
import no.ntnu.idi.stud.savingsapp.model.user.User;
import no.ntnu.idi.stud.savingsapp.repository.InventoryRepository;
import no.ntnu.idi.stud.savingsapp.repository.StoreRepository;
import no.ntnu.idi.stud.savingsapp.service.ItemService;
import no.ntnu.idi.stud.savingsapp.service.UserService;

@Service
public class ItemServiceImpl implements ItemService {
    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private UserService userService;

    /**
     * Retrieves the inventory of items for a specific user.
     *
     * @param userId the unique identifier of the user whose inventory is to be retrieved
     * @return a list of {@link Item} objects representing the user's inventory
     */
    @Override
    public List<Item> getInventory(Long userId) {
        return storeRepository.getInventory(userId);
    }

    /**
     * Retrieves a list of all items available in the store.
     *
     * @return a list of {@link Item} objects representing all items currently available in the store
     */
    @Override
    public List<Item> getStore() {
        return storeRepository.findAll();
    }

    /**
     * Retrieves a specific item from the store based on its identifier.
     *
     * @param itemId the unique identifier of the item to be retrieved
     * @return the {@link Item} corresponding to the provided identifier, or null if no such item exists
     */
    @Override
    public Item getItemFromId(Long itemId) {
        Optional<Item> item = storeRepository.findById(itemId);
        if(item.isPresent()) {
            return item.get();
        } else {
            return null;
        }
    }

    /**
     * Adds an item to the inventory of a specified user.
     *
     * @param user the {@link User} object representing the user to whom the item will be added
     * @param item the {@link Item} to be added to the user's inventory
     */
    @Override
    public Boolean addItem(User user, Item item) {
        InventoryId inventoryId = new InventoryId(item, user);
        if(userService.hasMorePoints(user, item.getPrice())) {
            inventoryRepository.save(new Inventory(inventoryId, new Timestamp(System.currentTimeMillis())));
            userService.deductPoints(user.getId(), item.getPrice());
            return true;
        } else {
            return false;
        }
    }
}
