package edu.es.ds.esiusuarios.model;

import java.util.UUID;

import jakarta.persistence.*;

@Entity
@Table(name = "token2")
public class Token2 {

    @Id
    @Column(length = 36)
    private String id;

    private long time;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Token2() {

        this.id =
            UUID.randomUUID().toString();

        this.time =
            System.currentTimeMillis();
    }

    public String getId() {
        return id;
    }

    public long getTime() {
        return time;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}