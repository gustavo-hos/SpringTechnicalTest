package me.gustavo.springordermanager.service.impl;

import me.gustavo.springordermanager.event.event.OrderCreatedEvent;
import me.gustavo.springordermanager.event.event.OrderStatusChangedEvent;
import me.gustavo.springordermanager.exception.EntityNotCreatedException;
import me.gustavo.springordermanager.exception.EntityNotFoundException;
import me.gustavo.springordermanager.model.Item;
import me.gustavo.springordermanager.model.Order;
import me.gustavo.springordermanager.model.User;
import me.gustavo.springordermanager.model.dto.OrderDto;
import me.gustavo.springordermanager.repository.OrderRepository;
import me.gustavo.springordermanager.service.intf.ItemService;
import me.gustavo.springordermanager.service.intf.OrderService;
import me.gustavo.springordermanager.service.intf.UserService;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class OrderServiceImpl extends BaseCRUDService<Order, UUID, OrderRepository> implements OrderService {

    private final ItemService itemService;
    private final UserService userService;
    private final ApplicationEventPublisher eventPublisher;

    public OrderServiceImpl(OrderRepository repository, ItemService itemService, UserService userService, ApplicationEventPublisher eventPublisher) {
        super(repository);
        this.itemService = itemService;
        this.userService = userService;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public Order create(OrderDto orderDto) {
        Order order = new Order();

        Optional<Item> item = this.itemService.findBySku(orderDto.getItemSku());
        Optional<User> user = this.userService.findById(orderDto.getUserId());

        if (!item.isPresent())
            throw new EntityNotFoundException("Item");

        if (!user.isPresent())
            throw new EntityNotFoundException("User");

        order.setItem(item.get());
        order.setUser(user.get());
        order.setSupplied(0);
        order.setQuantity(orderDto.getQuantity());
        order.setStatus(Order.Status.PENDING);
        order.setCreationDate(new Date());

        order = this.create(order);

        if (order.getUuid() == null)
            throw new EntityNotCreatedException("Order");

        eventPublisher.publishEvent(new OrderCreatedEvent(this, order));

        return order;
    }

    @Override
    public Optional<Order> update(OrderDto orderDto) {
        Optional<Order> optOrder = this.findById(orderDto.getUuid());

        if (!optOrder.isPresent())
            return Optional.empty();

        Optional<Item> item = this.itemService.findBySku(orderDto.getItemSku());

        if (!item.isPresent())
            throw new EntityNotFoundException("Item");

        Order order = optOrder.get();

        order.setItem(item.get());
        order.setQuantity(orderDto.getQuantity());

        return this.update(orderDto.getUuid(), order);
    }

    @Override
    public Optional<Order> update(UUID uuid, Order obj) {
        Optional<Order> optOrder = this.findById(uuid);

        if (!optOrder.isPresent())
            return Optional.empty();

        optOrder = super.update(uuid, obj);

        // order is not complete yet, so check if status has changed.
        // if so, dispatch status changed event.
        optOrder.ifPresent((order) -> {
            if (order.getStatus().equals(Order.Status.COMPLETED))
                eventPublisher.publishEvent(new OrderStatusChangedEvent(this, order));
        });

        return optOrder;
    }

    @Override
    public Optional<Order> update(Order entity) {
        return this.update(entity.getUuid(), entity);
    }

    @Override
    public List<Order> findPendingOrdersByItem(long itemId, Sort sort) {
        return this.repository.findOrderByItem_IdAndStatus(itemId, Order.Status.PENDING, sort);
    }
}
