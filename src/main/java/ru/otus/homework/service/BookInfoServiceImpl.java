package ru.otus.homework.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import ru.otus.homework.model.Author;
import ru.otus.homework.model.Book;
import ru.otus.homework.model.Genre;
import ru.otus.homework.repository.BookInfoRepositoryJpa;
import ru.otus.homework.security.annotation.IsEditor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class BookInfoServiceImpl implements BookInfoService {
    private static final String ERROR_ON_UPDATING_BOOK = "error on updating book";
    private static final String ERROR_ON_INSERTING_BOOK = "error on inserting book";
    private static final String ERROR_ON_DELETING_BOOK = "error on deleting book";

    private final BookInfoRepositoryJpa bookInfoRepositoryJpa;
    private final DictionaryService dictionaryService;

    public BookInfoServiceImpl(
            DictionaryService dictionaryService,
            BookInfoRepositoryJpa bookInfoRepositoryJpa
    ) {
        this.bookInfoRepositoryJpa = bookInfoRepositoryJpa;
        this.dictionaryService = dictionaryService;
    }

    @Override
    @IsEditor
    public Book insertBook(String title, String authors, String genreName, String description) {
        List<Author> authorList = formAuthorList(authors);
        Genre genre = dictionaryService.getGenreByName(genreName);
        try {
            Book newBook = new Book(0L, title, genre, authorList, description);
            return bookInfoRepositoryJpa.save(newBook);
        } catch (Exception e) {
            log.error(ERROR_ON_INSERTING_BOOK, e);
        }
        return null;
    }

    @Override
    @IsEditor
    public Book updateBookById(long bookId, String bookTitle, String authors, Genre genre, String description) {
        List<Author> authorList = formAuthorList(authors);
        Book updatedBook = getBookById(bookId);
        if (updatedBook != null) {
            updatedBook.setFullName(bookTitle);
            updatedBook.setAuthors(authorList);
            updatedBook.setGenre(genre);
            updatedBook.setBookDescription(description);
            return updateBook(updatedBook);
        }
        return null;
    }

    @Override
    @PreAuthorize("hasRole('ROLE_EDITOR')")
    public boolean deleteBookById(long bookId) {
        try {
            Book book = getBookById(bookId);
            if (book != null) {
                bookInfoRepositoryJpa.delete(book);
                return true;
            }
        } catch (Exception e) {
            log.error(ERROR_ON_DELETING_BOOK, e);
        }
        return false;
    }

    @Override
    @PreAuthorize("hasRole('ROLE_VIEWER')")
    public List<Book> getAllBooks() {
        return bookInfoRepositoryJpa.findAll();
    }

    @Override
    @PreAuthorize("hasRole('ROLE_VIEWER')")
    public Book getBookById(long bookId) {
        Optional<Book> optionalBook = bookInfoRepositoryJpa.findById(bookId);
        return optionalBook.orElse(null);
    }

    @Override
    @PreAuthorize("hasRole('ROLE_VIEWER')")
    public long getBookCount() {
        return bookInfoRepositoryJpa.count();
    }

    private List<Author> formAuthorList(String authorFullNames) {
        List<Author> authors = dictionaryService.getAuthors();

        List<Author> userAuthorList = new ArrayList<>();
        if (authorFullNames != null) {
            Arrays.stream(authorFullNames.split(";"))
                    .forEach(
                            a -> {
                                Author author = findAuthorInRepository(a, authors);
                                if (author == null) {
                                    Author newAuthor = dictionaryService.saveAuthor(
                                            new Author(0L, a, "", null)
                                    );
                                    userAuthorList.add(newAuthor);
                                } else {
                                    userAuthorList.add(author);
                                }
                            }
                    );
        }
        return userAuthorList;
    }

    private Author findAuthorInRepository(String authorFullName, List<Author> authors) {
        Author resultAuthor = null;
        for (Author author: authors) {
            if (authorFullName.equalsIgnoreCase(author.getFullName())) {
                resultAuthor = author;
                break;
            }
        }
        return resultAuthor;
    }

    private Book updateBook(Book book) {
        try {
            return bookInfoRepositoryJpa.save(book);
        } catch (Exception e) {
            log.error(ERROR_ON_UPDATING_BOOK, e);
        }
        return null;
    }
}
