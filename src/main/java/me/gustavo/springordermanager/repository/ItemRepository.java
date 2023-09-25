package me.gustavo.springordermanager.repository;

import me.gustavo.springordermanager.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

    Optional<Item> findItemBySku(String sku);

}
