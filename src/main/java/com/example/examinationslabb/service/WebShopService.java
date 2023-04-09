package com.example.examinationslabb.service;

import com.example.examinationslabb.model.User;
import com.example.examinationslabb.model.UserSecurity;
import com.example.examinationslabb.repository.BookRepository;
import com.example.examinationslabb.repository.GameRepository;
import com.example.examinationslabb.repository.MovieRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

@Service
@SessionScope
public class WebShopService {
    private final BookRepository bookRepository;
    private final GameRepository gameRepository;
    private final MovieRepository movieRepository;
    private User user;
    private final UserService userService;

    public WebShopService(BookRepository bookRepository, GameRepository gameRepository, MovieRepository movieRepository, UserSecurity userSecurity, UserService userService) {
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public UserService getUserService() {
        return userService;
    }
}
