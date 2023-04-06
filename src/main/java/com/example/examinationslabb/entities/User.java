package com.example.examinationslabb.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "customers")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private UserType userType;
    private String username;


    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
