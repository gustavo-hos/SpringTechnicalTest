package me.gustavo.springordermanager.repository;

import me.gustavo.springordermanager.model.Order;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<Order, UUID> {

    List<Order> findOrderByItem_IdAndStatus(long itemId, Order.Status status, Sort sort);

}
