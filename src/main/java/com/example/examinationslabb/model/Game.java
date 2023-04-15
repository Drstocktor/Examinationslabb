package com.example.examinationslabb.model;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "games")
public class Game implements Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;
    private String developer;
    private int releaseYear;
    private int price;
    private final String category = "Game";

    public Game() {
    }

    public Game(String title, String developer, int price) {
        this.title = title;
        this.developer = developer;
        this.price = price;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDeveloper() {
        return developer;
    }

    public void setDeveloper(String developer) {
        this.developer = developer;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Game game = (Game) o;
        return releaseYear == game.releaseYear && price == game.price && Objects.equals(id, game.id) && Objects.equals(title, game.title) && Objects.equals(developer, game.developer) && Objects.equals(category, game.category);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, developer, releaseYear, price, category);
    }

    @Override
    public String getCategory() {
        return category;
    }

}
