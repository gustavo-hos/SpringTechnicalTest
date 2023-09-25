package me.gustavo.springordermanager.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import me.gustavo.springordermanager.serializer.UserIdSerializer;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;
import java.util.UUID;

@Entity(name = "om_order")
@Table(name = "om_order")
public class Order {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Type(type = "uuid-char")
    private UUID uuid;

    @ManyToOne
    @JoinColumn(name = "fk_item", nullable = false)
    private Item item;

    @ManyToOne
    @JoinColumn(name = "fk_user", nullable = false)
    @JsonSerialize(using = UserIdSerializer.class)
    private User user;

    @Column
    private int quantity;

    @Column
    private int supplied;

    @Column
    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(name = "creation_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.DETACH, CascadeType.REFRESH})
    @JoinTable(name = "om_order_stock_movement",
            joinColumns = {
                    @JoinColumn(name = "fk_order", referencedColumnName = "uuid")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "fk_stock_movement", referencedColumnName = "id")
            })
    private Set<StockMovement> stockMovements;

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Set<StockMovement> getStockMovements() {
        return stockMovements;
    }

    public void setStockMovements(Set<StockMovement> stockMovements) {
        this.stockMovements = stockMovements;
    }

    public int getSupplied() {
        return supplied;
    }

    public void setSupplied(int supplied) {
        this.supplied = supplied;
    }

    @Override
    public String toString() {
        return "Order{" +
                "uuid=" + uuid +
                ", item=" + item +
                ", user=" + user +
                ", quantity=" + quantity +
                ", status=" + status +
                ", creationDate=" + creationDate +
                ", stockMovements=" + stockMovements +
                '}';
    }

    public enum Status {
        PENDING, COMPLETED
    }
}
