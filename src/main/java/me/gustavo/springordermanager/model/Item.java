package me.gustavo.springordermanager.model;

import javax.persistence.*;

@Entity(name = "om_item")
@Table(name = "om_item")
public class Item {

    @Id
    @SequenceGenerator(name = "om_item_sequence", sequenceName = "om_item_sequence", allocationSize = 1)
    @GeneratedValue(generator = "om_item_sequence", strategy = GenerationType.SEQUENCE)
    private long id;

    @Column(nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    private String sku;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", sku='" + sku + '\'' +
                '}';
    }
}
