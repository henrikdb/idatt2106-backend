package no.ntnu.idi.stud.savingsapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import no.ntnu.idi.stud.savingsapp.model.store.Inventory;
import no.ntnu.idi.stud.savingsapp.model.store.InventoryId;
import no.ntnu.idi.stud.savingsapp.model.store.Item;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, InventoryId> {
    @Query(value = "SELECT i.* FROM item i JOIN inventory inv ON i.item_id = inv.item_id WHERE inv.user_id = :userId", nativeQuery = true)
    List<Item> getInventory(@Param("userId") Long userId);
}
