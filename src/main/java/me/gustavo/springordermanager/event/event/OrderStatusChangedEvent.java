package me.gustavo.springordermanager.event.event;

import me.gustavo.springordermanager.event.AbstractApplicationEvent;
import me.gustavo.springordermanager.model.Order;

public class OrderStatusChangedEvent extends AbstractApplicationEvent {

    private final Order changedOrder;

    public OrderStatusChangedEvent(Object source, Order changedOrder) {
        super("order_changed_event", source);

        this.changedOrder = changedOrder;
    }

    public Order getChangedOrder() {
        return changedOrder;
    }
}
