package com.example.examinationslabb.repository;

import com.example.examinationslabb.model.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {
    Game findByTitle(String title);
    List<Game> findByDeveloper(String developer);
}
