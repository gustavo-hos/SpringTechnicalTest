package me.gustavo.springordermanager.model;

import javax.persistence.*;

@Entity
@Table(name = "om_item_stock")
public class ItemStock {

    @Id
    @SequenceGenerator(name = "om_item_stock_sequence", sequenceName = "om_item_stock_sequence", allocationSize = 1)
    @GeneratedValue(generator = "om_item_stock_sequence", strategy = GenerationType.SEQUENCE)
    private long id;

    @OneToOne
    @JoinColumn(name = "fk_item", unique = true)
    private Item item;

    @Column
    private int quantity;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
