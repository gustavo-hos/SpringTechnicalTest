package me.gustavo.springordermanager.event.listener;

import me.gustavo.springordermanager.event.event.OrderCreatedEvent;
import me.gustavo.springordermanager.model.ItemStock;
import me.gustavo.springordermanager.model.Order;
import me.gustavo.springordermanager.service.intf.ItemStockService;
import me.gustavo.springordermanager.service.intf.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class OrderCreatedEventListener implements ApplicationListener<OrderCreatedEvent> {

    private static final Logger LOGGER = LoggerFactory.getLogger("order_processing");

    private final OrderService orderService;
    private final ItemStockService itemStockService;

    public OrderCreatedEventListener(OrderService orderService, ItemStockService itemStockService) {
        this.orderService = orderService;
        this.itemStockService = itemStockService;
    }

    @Override
    public void onApplicationEvent(OrderCreatedEvent event) {
        Order order = event.getCreatedOrder();

        Optional<ItemStock> optItemStock = itemStockService.findByItemId(order.getItem().getId());

        if (!optItemStock.isPresent() || optItemStock.get().getQuantity() <= 0) {
            LOGGER.info("A new order was created, but there's no enough stock to supply it.");
            return;
        }

        ItemStock itemStock = optItemStock.get();

        if (itemStock.getQuantity() >= order.getQuantity()) {
            order.setSupplied(order.getQuantity());
            order.setStatus(Order.Status.COMPLETED);

            Optional<Order> optOrder = this.orderService.update(order);

            if (optOrder.isPresent())
                itemStockService.withdraw(order.getItem().getId(), order.getQuantity());
        }

    }
}
