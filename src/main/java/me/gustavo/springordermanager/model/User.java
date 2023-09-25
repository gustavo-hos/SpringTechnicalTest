package me.gustavo.springordermanager.model;

import javax.persistence.*;

@Entity(name = "om_user")
@Table(name = "om_user")
public class User {

    @Id
    @SequenceGenerator(name = "om_user_sequence", sequenceName = "om_user_sequence", allocationSize = 1)
    @GeneratedValue(generator = "om_user_sequence", strategy = GenerationType.SEQUENCE)
    private long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
