package ru.otus.homework.service;

import ru.otus.homework.model.Author;
import ru.otus.homework.model.Genre;

import java.util.List;

public interface DictionaryService {
    List<Author> getAuthors();
    Author saveAuthor(Author author);
    Genre getGenreByName(String genre);
}
