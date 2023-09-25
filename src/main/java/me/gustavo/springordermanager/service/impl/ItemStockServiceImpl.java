package me.gustavo.springordermanager.service.impl;

import me.gustavo.springordermanager.exception.EntityNotCreatedException;
import me.gustavo.springordermanager.model.Item;
import me.gustavo.springordermanager.model.ItemStock;
import me.gustavo.springordermanager.model.StockMovement;
import me.gustavo.springordermanager.repository.ItemStockRepository;
import me.gustavo.springordermanager.service.intf.ItemService;
import me.gustavo.springordermanager.service.intf.ItemStockService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ItemStockServiceImpl extends BaseCRUDService<ItemStock, Long, ItemStockRepository> implements ItemStockService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ItemStockService.class);

    private final ItemService itemService;

    public ItemStockServiceImpl(ItemStockRepository repository, ItemService itemService, ItemStockRepository itemStockRepository) {
        super(repository);
        this.itemService = itemService;
    }

    @Override
    public Optional<ItemStock> findByItemId(long itemId) {
        return this.repository.findItemStockByItem_Id(itemId);
    }

    @Override
    public Optional<ItemStock> createOrUpdate(StockMovement stockMovement) {
        LOGGER.info("Trying to create a new stock for item {}", stockMovement.getItem());

        Optional<ItemStock> optStock = this.findByItemId(stockMovement.getItem().getId());

        if (!optStock.isPresent()) {
            LOGGER.info("Stock for this item doesn't exist yet. Creating one. {}", stockMovement.getItem());
            ItemStock stock = new ItemStock();

            Optional<Item> item = itemService.findById(stockMovement.getItem().getId());

            if (!item.isPresent())
                return Optional.empty();

            stock.setItem(item.get());
            stock.setQuantity(stockMovement.getQuantity());

            stock = this.create(stock);

            if (stock.getId() == 0)
                throw new EntityNotCreatedException("Item stock");

            return Optional.of(stock);
        } else {
            LOGGER.info("Stock for this item already exists. Updating the stock size. {}", stockMovement.getItem());

            ItemStock stock = optStock.get();

            int newQuantity = stock.getQuantity() + stockMovement.getQuantity();

            LOGGER.info("Previous stock size {}, new stock size: {}", stock.getQuantity(), newQuantity);

            stock.setQuantity(newQuantity);

            return this.update(stock.getId(), stock);
        }
    }

    @Override
    public void withdraw(long itemId, int quantity) {
        Optional<ItemStock> optStock = this.findByItemId(itemId);

        if (!optStock.isPresent())
            return;

        ItemStock stock = optStock.get();

        if (quantity > stock.getQuantity())
            throw new UnsupportedOperationException();

        stock.setQuantity(stock.getQuantity() - quantity);

        this.update(stock.getId(), stock);
    }

    @Override
    public Optional<ItemStock> update(ItemStock entity) {
        return this.update(entity.getId(), entity);
    }
}
