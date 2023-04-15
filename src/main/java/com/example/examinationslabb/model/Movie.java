package com.example.examinationslabb.model;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "movies")
public class Movie implements Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;
    private String director;
    private int releaseYear;
    private int price;
    private final String category = "Movie";

    public Movie() {

    }

    public Movie(String title, String director, int releaseYear, int price) {
        this.title = title;
        this.director = director;
        this.releaseYear = releaseYear;
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

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Movie movie = (Movie) o;
        return releaseYear == movie.releaseYear && price == movie.price && Objects.equals(id, movie.id) && Objects.equals(title, movie.title) && Objects.equals(director, movie.director) && Objects.equals(category, movie.category);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, director, releaseYear, price, category);
    }

    @Override
    public String getCategory() {
        return category;
    }

}
