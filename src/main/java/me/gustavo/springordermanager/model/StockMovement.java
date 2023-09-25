package me.gustavo.springordermanager.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity(name = "om_stock_movement")
@Table(name = "om_stock_movement")
public class StockMovement {

    @Id
    @SequenceGenerator(name = "om_stock_movement_sequence", sequenceName = "om_stock_movement_sequence", allocationSize = 1)
    @GeneratedValue(generator = "om_stock_movement_sequence", strategy = GenerationType.SEQUENCE)
    private long id;

    @OneToOne
    @JoinColumn(name = "fk_item")
    private Item item;

    @Column
    private Integer quantity;

    @Column(name = "creation_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.DETACH, CascadeType.REFRESH})
    @JoinTable(name = "om_order_stock_movement",
            joinColumns = {
                    @JoinColumn(name = "fk_stock_movement", referencedColumnName = "id")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "fk_order", referencedColumnName = "uuid")
            })
    @JsonBackReference
    private Set<Order> orders;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
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

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Set<Order> getOrders() {
        return orders;
    }

    public void setOrders(Set<Order> orders) {
        this.orders = orders;
    }

    @Override
    public String toString() {
        return "StockMovement{" +
                "id=" + id +
                ", item=" + item +
                ", quantity=" + quantity +
                ", creationDate=" + creationDate +
                '}';
    }
}
