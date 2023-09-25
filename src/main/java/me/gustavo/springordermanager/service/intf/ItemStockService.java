package me.gustavo.springordermanager.service.intf;

import me.gustavo.springordermanager.model.ItemStock;
import me.gustavo.springordermanager.model.StockMovement;

import java.util.Optional;

public interface ItemStockService extends CRUDService<ItemStock, Long> {

    Optional<ItemStock> findByItemId(long itemId);

    Optional<ItemStock> createOrUpdate(StockMovement stockMovement);

    void withdraw(long itemId, int quantity);
}
