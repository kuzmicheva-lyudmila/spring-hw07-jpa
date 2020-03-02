package ru.otus.homework.repository;

import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.homework.model.Author;

import java.util.List;

public interface AuthorRepositoryJpa extends JpaRepository<Author, Long> {
    List<Author> findAll();
    Author findByFullName(String fullName);
    Author save(Author author);
}
