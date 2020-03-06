package ru.otus.homework.service;

import lombok.SneakyThrows;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.homework.model.Author;
import ru.otus.homework.model.Genre;

import java.util.List;

public interface DictionaryService {
    List<Author> getAuthors();
    Author saveAuthor(Author author);
    List<Genre> getGenres();
    List<String> getGenreNames();
    Genre getGenreByName(String genre);
}
