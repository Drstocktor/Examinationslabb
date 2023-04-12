package com.example.examinationslabb.service;

import com.example.examinationslabb.model.*;
import com.example.examinationslabb.repository.BookRepository;
import com.example.examinationslabb.repository.GameRepository;
import com.example.examinationslabb.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@SessionScope
public class WebShopService {
    private final BookRepository bookRepository;
    private final GameRepository gameRepository;
    private final MovieRepository movieRepository;
    private final UserService userService;
    private List<Product> shoppingCart;

    @Autowired
    public WebShopService(BookRepository bookRepository, GameRepository gameRepository, MovieRepository movieRepository, UserService userService) {
        this.bookRepository = bookRepository;
        this.gameRepository = gameRepository;
        this.movieRepository = movieRepository;
        this.userService = userService;
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public List<Game> getAllGames() {
        return gameRepository.findAll();
    }

    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    public List<Product> getProductByType(String category) {
        List<Product> products = new ArrayList<>();
        if (category != null) {
            switch (category) {
                case "books" -> products.addAll(getAllBooks());
                case "movies" -> products.addAll(getAllMovies());
                case "games" -> products.addAll(getAllGames());
                default -> {
                    products.addAll(getAllGames());
                    products.addAll(getAllMovies());
                    products.addAll(getAllBooks());
                }
            }
        }
        return products;
    }

    public Book findBookById(Long id) {
        return bookRepository.findById(id).orElseThrow();
    }

    public Movie findMovieById(Long id) {
        return movieRepository.findById(id).orElseThrow();
    }

    public Game findGameById(Long id) {
        return gameRepository.findById(id).orElseThrow();
    }

    public List<Product> searchProducts(String search) {
        List<Product> productsFound = new ArrayList<>();

        List<Movie> movies = findMoviesBySearch(search);
        List<Game> games = findGamesBySearch(search);
        List<Book> books = findBooksBySearch(search);

        productsFound.addAll(movies);
        productsFound.addAll(games);
        productsFound.addAll(books);

        System.out.println(productsFound);

        return productsFound;
    }

    private List<Book> findBooksBySearch(String search) {
        return bookRepository.findAll().stream()
                .filter(book -> book
                        .getTitle()
                        .contains(search))
                .toList();

        //todo get titles and more?
        //todo make search in lowercase?
    }

    private List<Game> findGamesBySearch(String search) {
        return gameRepository.findAll().stream()
                .filter(game -> game
                        .getTitle()
                        .contains(search))
                .toList();
    }

    private List<Movie> findMoviesBySearch(String search) {
        return movieRepository.findAll().stream()
                .filter(movie -> movie
                        .getTitle()
                        .contains(search))
                .toList();
    }
}
