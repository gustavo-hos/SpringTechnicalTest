package me.gustavo.springordermanager.controller;

import me.gustavo.springordermanager.model.StockMovement;
import me.gustavo.springordermanager.model.dto.StockMovementDto;
import me.gustavo.springordermanager.service.intf.StockMovementService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class StockMovementController {

    private final StockMovementService stockMovementService;

    public StockMovementController(StockMovementService stockMovementService) {
        this.stockMovementService = stockMovementService;
    }

    /**
     * Retrieves a list of all stock movements.
     *
     * @return A ResponseEntity containing the list of stock movements (HTTP status 200) or
     *         NO_CONTENT if no stock movements are found (HTTP status 204).
     */
    @GetMapping("/stockMovement")
    public ResponseEntity<List<StockMovement>> getStockMovements() {
        List<StockMovement> stockMovements = this.stockMovementService.findAll();

        if (stockMovements.isEmpty())
            return ResponseEntity.noContent().build();

        return ResponseEntity.ok(stockMovements);
    }

    /**
     * Retrieves a stock movement by its ID.
     *
     * @param id ID of the stock movement to retrieve.
     * @return A ResponseEntity containing the stock movement if found (HTTP status 200) or
     *         NOT_FOUND if the stock movement does not exist (HTTP status 404).
     */
    @GetMapping("/stockMovement/{id}")
    public ResponseEntity<StockMovement> getStockMovement(@PathVariable long id) {
        return this.stockMovementService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());

    }

    /**
     * Creates a new stock movement.
     *
     * @param stockMovementDto DTO representing the stock movement to create.
     * @return A ResponseEntity containing the created stock movement (HTTP status 200)
     *         or BAD_REQUEST if the request is invalid (HTTP status 400).
     */
    @PostMapping("/stockMovement")
    public ResponseEntity<StockMovement> createStockMovement(@RequestBody StockMovementDto stockMovementDto) {
        return this.stockMovementService.create(stockMovementDto)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.badRequest().build());

    }

    /**
     * Updates an existing stock movement.
     *
     * @param stockMovementDto DTO representing the stock movement to update.
     * @return A ResponseEntity containing the updated stock movement if found (HTTP status 200)
     *         or NOT_FOUND if the stock movement does not exist (HTTP status 404).
     */
    @PutMapping("/stockMovement")
    public ResponseEntity<StockMovement> updateStockMovement(@RequestBody StockMovementDto stockMovementDto) {
        return this.stockMovementService.update(stockMovementDto)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());

    }

    /**
     * Deletes a stock movement by its ID.
     *
     * @param id ID of the stock movement to delete.
     * @return A ResponseEntity with no content (HTTP status 204) upon successful deletion.
     */
    @DeleteMapping("/stockMovement/{id}")
    public ResponseEntity<Void> deleteStockMovement(@PathVariable long id) {
        this.stockMovementService.delete(id);

        return ResponseEntity.noContent().build();
    }

}
