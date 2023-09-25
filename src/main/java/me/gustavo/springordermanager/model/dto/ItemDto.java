package me.gustavo.springordermanager.model.dto;

import java.io.Serializable;
import java.util.Objects;

public class ItemDto implements Serializable {
    private long id;
    private String name;
    private String sku;

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSku() {
        return sku;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemDto entity = (ItemDto) o;
        return Objects.equals(this.id, entity.id) &&
                Objects.equals(this.name, entity.name) &&
                Objects.equals(this.sku, entity.sku);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, sku);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "name = " + name + ", " +
                "sku = " + sku + ")";
    }
}