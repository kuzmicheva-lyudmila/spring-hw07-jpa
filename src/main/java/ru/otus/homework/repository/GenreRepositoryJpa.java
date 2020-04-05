package ru.otus.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.homework.model.Book;
import ru.otus.homework.model.Genre;

import java.util.List;

public interface GenreRepositoryJpa extends JpaRepository<Genre, Long> {
    List<Genre> findAll();
    Genre findByGenre(String genre);
}
