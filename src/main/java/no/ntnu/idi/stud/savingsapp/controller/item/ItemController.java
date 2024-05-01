package no.ntnu.idi.stud.savingsapp.controller.item;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import no.ntnu.idi.stud.savingsapp.dto.store.InventoryDTO;
import no.ntnu.idi.stud.savingsapp.dto.store.ItemDTO;
import no.ntnu.idi.stud.savingsapp.model.store.Item;
import no.ntnu.idi.stud.savingsapp.model.user.User;
import no.ntnu.idi.stud.savingsapp.security.AuthIdentity;
import no.ntnu.idi.stud.savingsapp.service.ItemService;
import no.ntnu.idi.stud.savingsapp.service.UserService;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * Controller handling item related requests.
 */
@CrossOrigin
@RestController
@Validated
@RequestMapping("/api/item")
@EnableAutoConfiguration
@Tag(name = "Item", description = "Endpoints for managing store and user inventory.")
public class ItemController {
    @Autowired
    private ItemService itemService;

    @Autowired
    private UserService userService;

    @Autowired
    private ModelMapper modelMapper;

    
    @Operation(summary = "Get available store items", description = "Retrieves all items available in the store and a flag indicating whether the user has purchased each item.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "List of store items fetched successfully")
    })
    @GetMapping("/store")
    public ResponseEntity<List<ItemDTO>> getStore(@AuthenticationPrincipal AuthIdentity identity) {
        List<Item> store = itemService.getStore();
        List<Item> inventory = itemService.getInventory(identity.getId());
        List<ItemDTO> storeDTO = new ArrayList<>();
        for(Item item : store) {
            ItemDTO itemDTO = modelMapper.map(item, ItemDTO.class);
            if(inventory.contains(item)) {
                itemDTO.setAlreadyBought(true);
            }
            storeDTO.add(itemDTO);
        }
        return ResponseEntity.ok(storeDTO);
    }

    @Operation(summary = "Get user inventory items", description = "Retrieves a list of all items currently in the inventory of the user.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "List of inventory items fetched successfully")
    })
    @GetMapping("/inventory")
    public ResponseEntity<List<InventoryDTO>> getInventory(@AuthenticationPrincipal AuthIdentity identity) {
        List<Item> inventory = itemService.getInventory(identity.getId());
        List<InventoryDTO> inventoryDTO = new ArrayList<>();
        for(Item item : inventory) {
            inventoryDTO.add(modelMapper.map(item, InventoryDTO.class));
        }
        return ResponseEntity.ok(inventoryDTO);
    }

    @Operation(summary = "Purchase an item", description = "Performs a purchase of the item by the user. Points will be deducted from the user.")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Item purchased and added to inventory successfully", content = @Content(mediaType = "application/json")),
        @ApiResponse(responseCode = "403", description = "Insufficient points to purchase the item", content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class)))
    })
    @PostMapping("/{itemId}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> buyItem(@AuthenticationPrincipal AuthIdentity identity, @PathVariable long itemId) {
        Item item = itemService.getItemFromId(itemId);
        User user = userService.findById(identity.getId());
        boolean purchaseSuccessful = itemService.addItem(user, item);

        if (purchaseSuccessful) {
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Insufficient points to purchase the item");
        }
    }
}
