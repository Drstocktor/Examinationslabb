package com.example.examinationslabb.model;


import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany
    List<Book> booksOrdered;
    @OneToMany
    List<Movie> moviesOrdered;
    @OneToMany
    List<Game> gamesOrdered;
    private int totalPrice;
    private LocalDateTime placedOn;
    private boolean isPaid;


    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public List<Book> getBooksOrdered() {
        return booksOrdered;
    }

    public void setBooksOrdered(List<Book> booksOrdered) {
        this.booksOrdered = booksOrdered;
    }

    public List<Movie> getMoviesOrdered() {
        return moviesOrdered;
    }

    public void setMoviesOrdered(List<Movie> moviesOrdered) {
        this.moviesOrdered = moviesOrdered;
    }

    public List<Game> getGamesOrdered() {
        return gamesOrdered;
    }

    public void setGamesOrdered(List<Game> gamesOrdered) {
        this.gamesOrdered = gamesOrdered;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public LocalDateTime getPlacedOn() {
        return placedOn;
    }

    public void setPlacedOn(LocalDateTime placedOn) {
        this.placedOn = placedOn;
    }

}