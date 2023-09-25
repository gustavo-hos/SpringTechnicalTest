package me.gustavo.springordermanager.service.impl;

import me.gustavo.springordermanager.exception.EntityNotCreatedException;
import me.gustavo.springordermanager.model.Item;
import me.gustavo.springordermanager.model.dto.ItemDto;
import me.gustavo.springordermanager.repository.ItemRepository;
import me.gustavo.springordermanager.service.intf.ItemService;
import me.gustavo.springordermanager.util.StringUtil;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ItemServiceImpl extends BaseCRUDService<Item, Long, ItemRepository> implements ItemService {

    public ItemServiceImpl(ItemRepository repository) {
        super(repository);
    }

    @Override
    public Item create(ItemDto itemDto) {
        Item item = new Item();

        item.setName(itemDto.getName());
        item.setSku(itemDto.getSku());

        item = this.create(item);

        if (item.getId() == 0)
            throw new EntityNotCreatedException("item");

        return item;
    }

    @Override
    public Optional<Item> findBySku(String sku) {

        if (StringUtil.isEmpty(sku))
            return Optional.empty();

        return this.repository.findItemBySku(sku);
    }

    @Override
    public Optional<Item> update(ItemDto itemDto) {
        Optional<Item> optItem = this.findById(itemDto.getId());

        if (!optItem.isPresent())
            return Optional.empty();

        Item item = optItem.get();

        if (!StringUtil.isEmpty(itemDto.getName()))
            item.setName(itemDto.getName());

        if (!StringUtil.isEmpty(itemDto.getSku()))
            item.setSku(itemDto.getSku());

        return this.update(itemDto.getId(), item);
    }

    @Override
    public Optional<Item> update(Item entity) {
        return this.update(entity.getId(), entity);
    }
}
