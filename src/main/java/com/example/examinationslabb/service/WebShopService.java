package com.example.examinationslabb.service;

import com.example.examinationslabb.model.*;
import com.example.examinationslabb.repository.BookRepository;
import com.example.examinationslabb.repository.GameRepository;
import com.example.examinationslabb.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@SessionScope
public class WebShopService {
    private final BookRepository bookRepository;
    private final GameRepository gameRepository;
    private final MovieRepository movieRepository;
    private final UserService userService;

    @Autowired
    public WebShopService(BookRepository bookRepository, GameRepository gameRepository, MovieRepository movieRepository, UserService userService) {
        this.bookRepository = bookRepository;
        this.gameRepository = gameRepository;
        this.movieRepository = movieRepository;
        this.userService = userService;
    }


    public BookRepository getBookRepository() {
        return bookRepository;
    }

    public GameRepository getGameRepository() {
        return gameRepository;
    }

    public MovieRepository getMovieRepository() {
        return movieRepository;
    }

    public UserService getUserService() {
        return userService;
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

    public List<Object> getProductByType(String type) {
        List<Object> products = new ArrayList<>();
        if (type != null) {
            switch (type) {
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
}
