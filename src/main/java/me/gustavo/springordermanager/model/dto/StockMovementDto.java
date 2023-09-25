package me.gustavo.springordermanager.model.dto;

import me.gustavo.springordermanager.model.StockMovement;

import java.io.Serializable;
import java.util.Objects;

/**
 * DTO for {@link StockMovement}
 */
public class StockMovementDto implements Serializable {
    private long id;
    private String itemSku;
    private Integer quantity;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getItemSku() {
        return itemSku;
    }

    public void setItemSku(String itemSku) {
        this.itemSku = itemSku;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StockMovementDto entity = (StockMovementDto) o;
        return Objects.equals(this.id, entity.id) &&
                Objects.equals(this.itemSku, entity.itemSku) &&
                Objects.equals(this.quantity, entity.quantity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, itemSku, quantity);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "itemSku = " + itemSku + ", " +
                "quantity = " + quantity + ")";
    }
}