package me.gustavo.springordermanager.service.impl;

import me.gustavo.springordermanager.exception.EntityNotCreatedException;
import me.gustavo.springordermanager.model.Item;
import me.gustavo.springordermanager.model.Order;
import me.gustavo.springordermanager.model.StockMovement;
import me.gustavo.springordermanager.model.dto.StockMovementDto;
import me.gustavo.springordermanager.repository.StockMovementRepository;
import me.gustavo.springordermanager.service.intf.ItemService;
import me.gustavo.springordermanager.service.intf.ItemStockService;
import me.gustavo.springordermanager.service.intf.OrderService;
import me.gustavo.springordermanager.service.intf.StockMovementService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Component
public class StockMovementServiceImpl extends BaseCRUDService<StockMovement, Long, StockMovementRepository> implements StockMovementService {

    private static final Logger LOGGER = LoggerFactory.getLogger("order_processing");

    private final OrderService orderService;

    private final ItemService itemService;
    private final ItemStockService itemStockService;


    public StockMovementServiceImpl(StockMovementRepository repository, OrderService orderService, ItemService itemService, ItemStockService itemStockService) {
        super(repository);
        this.orderService = orderService;
        this.itemService = itemService;
        this.itemStockService = itemStockService;
    }

    @Override
    public Optional<StockMovement> create(StockMovementDto stockMovementDto) {
        StockMovement stockMovement = new StockMovement();

        Optional<Item> item = itemService.findBySku(stockMovementDto.getItemSku());

        if (!item.isPresent())
            return Optional.empty();

        stockMovement.setItem(item.get());
        stockMovement.setCreationDate(new Date());
        stockMovement.setQuantity(stockMovementDto.getQuantity());

        stockMovement = this.create(stockMovement);

        if (stockMovement.getId() == 0)
            throw new EntityNotCreatedException("stock movement");

        LOGGER.info("New movement stock created. {}", stockMovement);

        List<Order> pendingOrders = orderService.findPendingOrdersByItem(stockMovement.getItem().getId(), Sort.by(Sort.Direction.ASC, "creationDate"));

        if (pendingOrders.isEmpty()) {
            itemStockService.createOrUpdate(stockMovement);
            return Optional.empty();
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

        return Optional.of(stockMovement);
    }

    @Override
    public Optional<StockMovement> update(StockMovementDto stockMovementDto) {
        return Optional.empty();
    }

    @Override
    public Optional<StockMovement> update(StockMovement entity) {
        return this.update(entity.getId(), entity);
    }
}
