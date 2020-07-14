package ru.otus.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.access.prepost.PostAuthorize;
import ru.otus.homework.model.Genre;

public interface GenreRepositoryJpa extends JpaRepository<Genre, Long> {

    @PostAuthorize("hasPermission(returnObject, 'WRITE')")
    Genre findByGenre(String genre);
}
