package ru.otus.homework.service;

import ru.otus.homework.model.Book;
import ru.otus.homework.model.Genre;

import java.util.List;

public interface BookInfoService {
    Book insertBook(String title, String authors, String genre, String description);
    Book updateTitleBookById(long bookId, String newBookTitle);
    Book updateBookById(long bookId, String bookTitle, String authors, Genre genre, String description);
    boolean deleteBookById(long bookId);
    List<Book> getAllBooks();
    Book getBookById(long bookId);
}
