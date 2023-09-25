package me.gustavo.springordermanager.controller;

import me.gustavo.springordermanager.exception.EntityAlreadyExistsException;
import me.gustavo.springordermanager.model.Item;
import me.gustavo.springordermanager.model.dto.ItemDto;
import me.gustavo.springordermanager.service.intf.ItemService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class ItemController {

    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    /**
     * Retrieves a list of all items.
     *
     * @return A ResponseEntity containing the list of items (HTTP status 200) or
     *         NO_CONTENT if no items are found (HTTP status 204).
     */
    @GetMapping("/items")
    public ResponseEntity<List<Item>> getItems() {
        List<Item> items = this.itemService.findAll();

        if (items.isEmpty())
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(items);

        return ResponseEntity.ok(items);
    }

    /**
     * Retrieves an item by its ID.
     *
     * @param id The ID of the item to retrieve.
     * @return A ResponseEntity containing the item if found (HTTP status 200) or
     *         NOT_FOUND if the item does not exist (HTTP status 404).
     */
    @GetMapping("/item/{id}")
    public ResponseEntity<Item> getItem(@PathVariable long id) {
        return this.itemService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());

    }

    /**
     * Creates a new item.
     *
     * @param itemDto DTO representing the item to create.
     * @return A ResponseEntity containing the created item (HTTP status 200).
     * @throws EntityAlreadyExistsException if an item with the same SKU already exists.
     */
    @PostMapping("/item")
    public ResponseEntity<Item> createItem(@RequestBody ItemDto itemDto) {
        Optional<Item> existingItem = this.itemService.findBySku(itemDto.getSku());

        if (existingItem.isPresent()) {
            throw new EntityAlreadyExistsException("Item", "sku", itemDto.getSku());
        }

        Item item = this.itemService.create(itemDto);

        return ResponseEntity.ok(item);
    }

    /**
     * Updates an existing item.
     *
     * @param itemDto DTO representing the item to update.
     * @return A ResponseEntity containing the updated item if found (HTTP status 200)
     *         or NOT_FOUND if the item does not exist (HTTP status 404).
     */
    @PutMapping("/item")
    public ResponseEntity<Item> updateItem(@RequestBody ItemDto itemDto) {
        return this.itemService.update(itemDto)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());

    }

    /**
     * Deletes an item by its ID.
     *
     * @param id The ID of the item to delete.
     * @return A ResponseEntity with no content (HTTP status 204) upon successful
     *         deletion.
     */
    @DeleteMapping("/item/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable long id) {
        this.itemService.delete(id);

        return ResponseEntity.noContent().build();
    }

}
