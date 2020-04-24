package ru.otus.homework.service;

import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import ru.otus.homework.model.Author;
import ru.otus.homework.model.Genre;
import ru.otus.homework.repository.GenreRepositoryJpa;
import ru.otus.homework.repository.AuthorRepositoryJpa;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DictionaryServiceImpl implements DictionaryService {

    private final AuthorRepositoryJpa authorRepositoryJpa;
    private final GenreRepositoryJpa genreRepositoryJpa;

    public DictionaryServiceImpl(
            AuthorRepositoryJpa authorRepositoryJpa,
            GenreRepositoryJpa genreRepositoryJpa
    ) {
        this.authorRepositoryJpa = authorRepositoryJpa;
        this.genreRepositoryJpa = genreRepositoryJpa;
    }

    @SneakyThrows
    @Override
    public List<Author> getAuthors() {
        return authorRepositoryJpa.findAll();
     }

    @SneakyThrows
    @Override
    public Author saveAuthor(Author author) {
        return authorRepositoryJpa.save(author);
    }

    @SneakyThrows
    @Override
    public List<Genre> getGenres() {
        return genreRepositoryJpa.findAll();
    }

    @SneakyThrows
    @Override
    public List<String> getGenreNames() {
        List<Genre> genres = getGenres();
        if (!genres.isEmpty()) {
            return genres.stream()
                    .map(Genre::getName)
                    .collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    @SneakyThrows
    @Override
    public Genre getGenreByName(String genre) {
        return genreRepositoryJpa.findByGenre(genre);
    }
}