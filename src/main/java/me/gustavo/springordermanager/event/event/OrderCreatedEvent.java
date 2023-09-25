package me.gustavo.springordermanager.event.event;

import me.gustavo.springordermanager.event.AbstractApplicationEvent;
import me.gustavo.springordermanager.model.Order;

public class OrderCreatedEvent extends AbstractApplicationEvent {

    private final Order createdOrder;

    public OrderCreatedEvent(Object source, Order createdOrder) {
        super("order_created_event", source);

        this.createdOrder = createdOrder;
    }

    public Order getCreatedOrder() {
        return createdOrder;
    }
}
