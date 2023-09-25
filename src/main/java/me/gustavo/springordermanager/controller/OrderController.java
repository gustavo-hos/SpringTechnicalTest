package me.gustavo.springordermanager.controller;

import me.gustavo.springordermanager.model.Order;
import me.gustavo.springordermanager.model.StockMovement;
import me.gustavo.springordermanager.model.dto.OrderDto;
import me.gustavo.springordermanager.service.intf.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    /**
     * Retrieves a list of all orders.
     *
     * @return A ResponseEntity containing the list of orders (HTTP status 200) or
     *         NO_CONTENT if no orders are found (HTTP status 204).
     */
    @GetMapping("/orders")
    public ResponseEntity<List<Order>> getOrders() {
        List<Order> orders = this.orderService.findAll();

        if (orders.isEmpty())
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(orders);

        return ResponseEntity.ok(orders);
    }

    /**
     * Retrieves an order by its UUID.
     *
     * @param uuid UUID of the order to retrieve.
     * @return A ResponseEntity containing the order if found (HTTP status 200) or
     *         NOT_FOUND if the order does not exist (HTTP status 404).
     */
    @GetMapping("/order/{uuid}")
    public ResponseEntity<Order> getOrder(@PathVariable UUID uuid) {
        return this.orderService.findById(uuid)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());

    }

    /**
     * Retrieves the completion status of an order by its UUID.
     *
     * @param uuid UUID of the order to check.
     * @return A ResponseEntity with a message indicating the completion status.
     * they could be: <br>
     * <ul>
     *     <li>Order is complete. - When order is already finished.</li>
     *     <li>Order is still being processed. - When order is not finished yet.</li>
     * </ul>
     * <br>
     * It will throw an exception in case of Status could not be processed.
     */
    @GetMapping("/order/{uuid}/completion")
    public ResponseEntity<String> getCompletion(@PathVariable UUID uuid) {
        return this.orderService.findById(uuid)
                .map(order -> {
                    if (order.getStatus().equals(Order.Status.COMPLETED))
                        return ResponseEntity.ok("Order is complete.");
                    else if (order.getStatus().equals(Order.Status.PENDING))
                        return ResponseEntity.ok("Order is still being processed.");
                    else
                        return ResponseEntity.unprocessableEntity().body("Order status cannot be processed.");
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Retrieves the stock movements associated with an order by its UUID.
     *
     * @param uuid UUID of the order to retrieve stock movements for.
     * @return A ResponseEntity containing the set of stock movements (HTTP status 200)
     *         or NOT_FOUND if the order does not exist (HTTP status 404).
     */
    @GetMapping("/order/{uuid}/trackMovements")
    public ResponseEntity<Set<StockMovement>> getOrderMovements(@PathVariable UUID uuid) {
        return this.orderService.findById(uuid)
                .map(order -> ResponseEntity.ok(order.getStockMovements()))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Creates a new order.
     *
     * @param userDto DTO representing the order to create.
     * @return A ResponseEntity containing the created order (HTTP status 200).
     */
    @PostMapping("/order")
    public ResponseEntity<Order> createOrder(@RequestBody OrderDto userDto) {
        Order order = this.orderService.create(userDto);

        return ResponseEntity.ok(order);
    }

    /**
     * Updates an existing order.
     *
     * @param userDto DTO representing the order to update.
     * @return A ResponseEntity containing the updated order if found (HTTP status 200)
     *         or NOT_FOUND if the order does not exist (HTTP status 404).
     */
    @PutMapping("/order")
    public ResponseEntity<Order> updateOrder(@RequestBody OrderDto userDto) {
        return this.orderService.update(userDto)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());

    }

    /**
     * Deletes an order by its UUID.
     *
     * @param uuid UUID of the order to delete.
     * @return A ResponseEntity with no content (HTTP status 204) upon successful deletion.
     */
    @DeleteMapping("/order/{uuid}")
    public ResponseEntity<Void> deleteOrder(@PathVariable UUID uuid) {
        this.orderService.delete(uuid);

        return ResponseEntity.noContent().build();
    }

}
