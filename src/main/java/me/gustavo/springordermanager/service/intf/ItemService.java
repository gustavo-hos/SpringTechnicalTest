package me.gustavo.springordermanager.service.intf;

import me.gustavo.springordermanager.model.Item;
import me.gustavo.springordermanager.model.dto.ItemDto;

import java.util.Optional;

public interface ItemService extends CRUDService<Item, Long> {

    Item create(ItemDto itemDto);

    Optional<Item> findBySku(String sku);

    Optional<Item> update(ItemDto itemDto);
}
