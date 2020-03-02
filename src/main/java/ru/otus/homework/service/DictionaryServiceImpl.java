package ru.otus.homework.service;

import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.homework.model.Author;
import ru.otus.homework.model.Book;
import ru.otus.homework.model.Genre;
import ru.otus.homework.repository.GenreRepositoryJpa;
import ru.otus.homework.repository.AuthorRepositoryJpa;

import java.util.List;

@Service
public class DictionaryServiceImpl implements DictionaryService {

    private final AuthorRepositoryJpa authorRepositoryJpa;
    private final GenreRepositoryJpa genreRepositoryJpa;
    private final CommunicationService communicationService;

    public DictionaryServiceImpl(
            AuthorRepositoryJpa authorRepositoryJpa,
            GenreRepositoryJpa genreRepositoryJpa,
            CommunicationService communicationService
    ) {
        this.authorRepositoryJpa = authorRepositoryJpa;
        this.genreRepositoryJpa = genreRepositoryJpa;
        this.communicationService = communicationService;
    }

    @SneakyThrows
    @Override
    @Transactional(readOnly = true)
    public void showAuthors() {
        List<Author> authorList = authorRepositoryJpa.findAll();
        for (Author author : authorList) {
            communicationService.showMessage(author.toString());
            for (Book book : author.getBooks()) {
                communicationService.showMessage(book.toString());
            }
        }
    }

    @SneakyThrows
    @Override
    @Transactional(readOnly = true)
    public void showBookGenres() {
        List<Genre> genres = genreRepositoryJpa.findAll();
        for (Genre genre : genres) {
            communicationService.showMessage(genre.toString());
            for (Book book : genre.getBooks()) {
                communicationService.showMessage(book.toString());
            }
        }
    }
}
