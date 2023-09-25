package me.gustavo.springordermanager.event.event;

import me.gustavo.springordermanager.event.AbstractApplicationEvent;
import me.gustavo.springordermanager.model.StockMovement;

public class StockMovementCreatedEvent extends AbstractApplicationEvent {

    private final StockMovement stockMovement;

    public StockMovementCreatedEvent(Object source, StockMovement stockMovement) {
        super("stock_movement_created", source);
        this.stockMovement = stockMovement;
    }

    public StockMovement getStockMovement() {
        return stockMovement;
    }
}
