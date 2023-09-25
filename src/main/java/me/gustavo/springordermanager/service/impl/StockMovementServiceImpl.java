package me.gustavo.springordermanager.service.impl;

import me.gustavo.springordermanager.event.event.StockMovementCreatedEvent;
import me.gustavo.springordermanager.exception.EntityNotCreatedException;
import me.gustavo.springordermanager.model.Item;
import me.gustavo.springordermanager.model.StockMovement;
import me.gustavo.springordermanager.model.dto.StockMovementDto;
import me.gustavo.springordermanager.repository.StockMovementRepository;
import me.gustavo.springordermanager.service.intf.ItemService;
import me.gustavo.springordermanager.service.intf.StockMovementService;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Optional;

@Component
public class StockMovementServiceImpl extends BaseCRUDService<StockMovement, Long, StockMovementRepository> implements StockMovementService {

    private final ItemService itemService;
    private final ApplicationEventPublisher eventPublisher;

    public StockMovementServiceImpl(StockMovementRepository repository, ItemService itemService, ApplicationEventPublisher eventPublisher) {
        super(repository);
        this.itemService = itemService;
        this.eventPublisher = eventPublisher;
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

        eventPublisher.publishEvent(new StockMovementCreatedEvent(this, stockMovement));

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
