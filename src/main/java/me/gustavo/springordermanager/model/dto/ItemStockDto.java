package me.gustavo.springordermanager.model.dto;

import java.io.Serializable;
import java.util.Objects;

public class ItemStockDto implements Serializable {
    private long itemId;
    private int quantity;

    public long getItemId() {
        return itemId;
    }

    public void setItemId(long itemId) {
        this.itemId = itemId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemStockDto entity = (ItemStockDto) o;
        return Objects.equals(this.itemId, entity.itemId) &&
                Objects.equals(this.quantity, entity.quantity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(itemId, quantity);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "itemId = " + itemId + ", " +
                "quantity = " + quantity + ")";
    }
}