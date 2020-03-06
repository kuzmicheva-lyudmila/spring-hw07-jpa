package ru.otus.homework.service;

import lombok.SneakyThrows;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.homework.model.Book;

import java.io.UnsupportedEncodingException;
import java.util.List;

public interface BookInfoService {
    Book insertBook(String title, String authors, String genre, String description);
    Book updateTitleBookById(long bookId, String newBookTitle);
    boolean deleteBookById(long bookId);
    List<Book> getAllBooks();
    Book getBookById(long bookId);
}
