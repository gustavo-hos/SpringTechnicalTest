package me.gustavo.springordermanager.model.dto;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

public class OrderDto implements Serializable {
    private UUID uuid;
    private String itemSku;
    private long userId;
    private int quantity;

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getItemSku() {
        return itemSku;
    }

    public void setItemSku(String itemSku) {
        this.itemSku = itemSku;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
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
        OrderDto entity = (OrderDto) o;
        return Objects.equals(this.uuid, entity.uuid) &&
                Objects.equals(this.itemSku, entity.itemSku) &&
                Objects.equals(this.userId, entity.userId) &&
                Objects.equals(this.quantity, entity.quantity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, itemSku, userId, quantity);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "uuid = " + uuid + ", " +
                "itemSku = " + itemSku + ", " +
                "userId = " + userId + ", " +
                "quantity = " + quantity + ")";
    }
}