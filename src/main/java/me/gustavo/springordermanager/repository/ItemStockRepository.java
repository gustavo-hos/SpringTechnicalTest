package me.gustavo.springordermanager.repository;

import me.gustavo.springordermanager.model.ItemStock;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ItemStockRepository extends JpaRepository<ItemStock, Long> {

    Optional<ItemStock> findItemStockByItem_Id(long itemId);

}
