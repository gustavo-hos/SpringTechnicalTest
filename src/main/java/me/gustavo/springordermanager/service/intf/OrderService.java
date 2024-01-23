package me.gustavo.springordermanager.service.intf;

import me.gustavo.springordermanager.model.Order;
import me.gustavo.springordermanager.model.dto.OrderDto;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrderService extends CRUDService<Order, UUID> {

    Order create(OrderDto orderDto);

    Optional<Order> update(OrderDto orderDto);

    List<Order> findPendingOrdersByItem(long itemId, Sort sort);

    void supplyOrder(Order order);
}
