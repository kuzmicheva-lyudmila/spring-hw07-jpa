package ru.otus.homework.service;

import org.springframework.stereotype.Service;
import ru.otus.homework.model.Author;
import ru.otus.homework.model.Genre;
import ru.otus.homework.repository.GenreRepositoryJpa;
import ru.otus.homework.repository.AuthorRepositoryJpa;

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

    @Override
    public List<Author> getAuthors() {
        return authorRepositoryJpa.findAll();
     }

    @Override
    public Author saveAuthor(Author author) {
        return authorRepositoryJpa.save(author);
    }

    @Override
    public Genre getGenreByName(String genre) {
        return genreRepositoryJpa.findByGenre(genre);
    }
}
