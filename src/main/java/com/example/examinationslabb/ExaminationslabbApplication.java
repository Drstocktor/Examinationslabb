package com.example.examinationslabb;

import com.example.examinationslabb.model.*;
import com.example.examinationslabb.repository.BookRepository;
import com.example.examinationslabb.repository.GameRepository;
import com.example.examinationslabb.repository.MovieRepository;
import com.example.examinationslabb.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class ExaminationslabbApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExaminationslabbApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(UserRepository users, PasswordEncoder passwordEncoder, BookRepository books, MovieRepository movies, GameRepository games) {
        return args -> {
            users.save(new User("Victor", passwordEncoder.encode("victor"), UserType.USER));
            users.save(new User("Admin", passwordEncoder.encode("admin"), UserType.ADMIN));
            books.save(new Book("All Quiet On The Western Front", "Erich Maria Remarque", 30));
            books.save(new Book("Project Hail Mary", "Andy Weir", 60));
            books.save(new Book("Dune", "Frank Herbert", 50));
            movies.save(new Movie("Gladiator", "Ridley Scott", 2000, 20));
            movies.save(new Movie("The Prestige", "Christopher Nolan", 2006, 30));
            movies.save(new Movie("John Wick", "Derek Kolstad", 2014, 50));
            games.save(new Game("Cyperbunk 2077", "CD Project Red", 60));
            games.save(new Game("Dwarf Fortress", "Bay 12 Games", 30));
            games.save(new Game("Crusader Kings III", "Paradox Development Studio", 50));
        };
    }
}
