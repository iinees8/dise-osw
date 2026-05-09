package edu.es.ds.esiusuarios.model;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String password;

    private String token;

    private String email;
    @Column(name = "validation_date")
    private Long validationDate;

    public User() {
    }

    public User(String name, String password, String token, String email, Long validationDate) {
        this.name = name;
        this.password = password;
        this.token = token;
        this.email = email;
        this.validationDate = validationDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getToken() {
        return token;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getValidationDate() {
        return validationDate;
    }

    public void setValidationDate(Long validationDate) {
        this.validationDate = validationDate;
    }
}