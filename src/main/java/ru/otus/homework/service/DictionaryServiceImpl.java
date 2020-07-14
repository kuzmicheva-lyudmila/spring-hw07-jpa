package ru.otus.homework.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.stereotype.Service;
import ru.otus.homework.model.Author;
import ru.otus.homework.model.Genre;
import ru.otus.homework.repository.GenreRepositoryJpa;
import ru.otus.homework.repository.AuthorRepositoryJpa;

import java.util.Collections;
import java.util.List;

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

    @HystrixCommand(commandKey="getAuthors", fallbackMethod="buildFallbackAuthors")
    @Override
    public List<Author> getAuthors() {
        return authorRepositoryJpa.findAll();
     }

    @HystrixCommand(commandKey="saveAuthor")
    @Override
    public Author saveAuthor(Author author) {
        return authorRepositoryJpa.save(author);
    }

    @HystrixCommand(commandKey="getGenre", fallbackMethod="buildFallbackGenre")
    @Override
    public Genre getGenreByName(String genre) {
        return genreRepositoryJpa.findByGenre(genre);
    }

    public List<Author> buildFallbackAuthors() {
        return Collections.emptyList();
    }

    public Genre buildFallbackGenre(String genre) {
        return null;
    }
}
