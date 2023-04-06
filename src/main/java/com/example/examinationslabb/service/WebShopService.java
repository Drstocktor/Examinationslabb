package com.example.examinationslabb.service;

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

    public WebShopService(BookRepository bookRepository, GameRepository gameRepository, MovieRepository movieRepository) {
        this.bookRepository = bookRepository;
        this.gameRepository = gameRepository;
        this.movieRepository = movieRepository;
    }
}
