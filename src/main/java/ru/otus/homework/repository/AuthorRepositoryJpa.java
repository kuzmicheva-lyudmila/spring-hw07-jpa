package ru.otus.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import ru.otus.homework.model.Author;

import java.util.List;

@RepositoryRestResource(path = "authors")
public interface AuthorRepositoryJpa extends JpaRepository<Author, Long> {

    @RestResource(rel = "find-all", path = "find-all")
    List<Author> findAll();

    @RestResource(rel = "find-by-fullname", path = "find-by-fullname")
    Author findByFullName(String fullName);

    Author save(Author author);
}
