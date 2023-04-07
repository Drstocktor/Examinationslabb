package com.example.examinationslabb.entities;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "customers")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private UserType userType;
    private String username;
    private String password;
    @OneToMany
    private List<Order> orders;


    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<com.example.examinationslabb.entities.Order> getOrders() {
        return orders;
    }

    public void setOrders(List<com.example.examinationslabb.entities.Order> orders) {
        this.orders = orders;
    }
}
