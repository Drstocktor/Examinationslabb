package com.example.examinationslabb.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "customers")
public class User {

    private UserType userType;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
