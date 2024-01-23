package me.gustavo.springordermanager.service.impl;

import me.gustavo.springordermanager.exception.EntityNotCreatedException;
import me.gustavo.springordermanager.exception.EntityNotFoundException;
import me.gustavo.springordermanager.model.*;
import me.gustavo.springordermanager.model.dto.OrderDto;
import me.gustavo.springordermanager.repository.OrderRepository;
import me.gustavo.springordermanager.service.intf.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class OrderServiceImpl extends BaseCRUDService<Order, UUID, OrderRepository> implements OrderService {

    private static final Logger LOGGER = LoggerFactory.getLogger("order_processing");

    private final ItemService itemService;
    private final UserService userService;
    private final ItemStockService itemStockService;
    private final MailService mailService;

    public OrderServiceImpl(OrderRepository repository, ItemService itemService, UserService userService, ItemStockService itemStockService, MailService mailService) {
        super(repository);
        this.itemService = itemService;
        this.userService = userService;
        this.itemStockService = itemStockService;
        this.mailService = mailService;
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

        this.supplyOrder(order);

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

            if (order.getStatus().equals(Order.Status.COMPLETED)) {
                User userCreatedOrder = order.getUser();

                Map<String, Object> data = new HashMap<>();

                data.put("name", userCreatedOrder.getName());
                data.put("order_id", order.getUuid());

                mailService.sendMail("order-completed", data, new Mail(userCreatedOrder.getEmail(), "Order Completed!"));

                LOGGER.info("Order #{} is now completed!", order.getUuid());
            }

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

    @Override
    public void supplyOrder(Order order) {
        Optional<ItemStock> optItemStock = itemStockService.findByItemId(order.getItem().getId());

        if (!optItemStock.isPresent() || optItemStock.get().getQuantity() <= 0) {
            LOGGER.info("A new order was created, but there's no enough stock to supply it.");
            return;
        }

        ItemStock itemStock = optItemStock.get();

        if (itemStock.getQuantity() >= order.getQuantity()) {
            order.setSupplied(order.getQuantity());
            order.setStatus(Order.Status.COMPLETED);

            Optional<Order> optOrder = this.update(order);

            if (optOrder.isPresent())
                itemStockService.withdraw(order.getItem().getId(), order.getQuantity());
        }
    }
}
