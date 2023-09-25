package me.gustavo.springordermanager.event.listener;

import me.gustavo.springordermanager.event.event.StockMovementCreatedEvent;
import me.gustavo.springordermanager.model.Order;
import me.gustavo.springordermanager.model.StockMovement;
import me.gustavo.springordermanager.service.intf.ItemStockService;
import me.gustavo.springordermanager.service.intf.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class StockMovementCreatedEventListener implements ApplicationListener<StockMovementCreatedEvent> {

//    private static final Function<Set<StockMovement>, Integer> SUM_STOCK_MOVEMENTS = (sets) -> sets.stream().mapToInt(StockMovement::getQuantity).sum();

    private static final Logger LOGGER = LoggerFactory.getLogger("order_processing");

    private final OrderService orderService;
    private final ItemStockService itemStockService;


    public StockMovementCreatedEventListener(OrderService orderService, ItemStockService itemStockService) {
        this.orderService = orderService;
        this.itemStockService = itemStockService;
    }


    @Override
    public void onApplicationEvent(StockMovementCreatedEvent event) {
        LOGGER.info("New movement stock created. {}", event.getStockMovement());
        StockMovement stockMovement = event.getStockMovement();
        List<Order> pendingOrders = orderService.findPendingOrdersByItem(stockMovement.getItem().getId(), Sort.by(Sort.Direction.ASC, "creationDate"));

        if (pendingOrders.isEmpty()) {
            itemStockService.createOrUpdate(stockMovement);
            return;
        }

        int stockSize = stockMovement.getQuantity();

        for (Order order : pendingOrders) {
            // Calculate the remaining quantity to be supplied
            int orderRealQuantity = order.getQuantity() - order.getSupplied();

            // Update the remaining stock size
            stockSize -= orderRealQuantity;

            // Update the total supplied quantity
            if (stockSize >= 0)
                order.setSupplied(order.getSupplied() + orderRealQuantity);
            else
                order.setSupplied(order.getSupplied() + orderRealQuantity + stockSize);

            // Add the current stock movement to the order's list of movements
            order.getStockMovements().add(stockMovement);

            // If the total supplied quantity equals the order quantity, mark the order as completed
            if (order.getSupplied() == order.getQuantity()) {
                order.setStatus(Order.Status.COMPLETED);
            }

            orderService.update(order);

            // If there is no more stock to distribute, exit the loop
            if (stockSize <= 0)
                break;
        }

        // If there is remaining stock after all orders processing, create or update the item stock
        // of current item
        if (stockSize > 0) {
            stockMovement.setQuantity(stockSize);
            itemStockService.createOrUpdate(stockMovement);
        }
    }
}
