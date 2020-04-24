package ru.otus.homework.service;

import lombok.SneakyThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.homework.model.Book;
import ru.otus.homework.repository.BookInfoRepositoryJpa;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@DisplayName("Сервис для работы с книгами")
@SpringBootTest
class BookInfoServiceImplTest {

    private static final long BOOK_ID = 1L;
    private static final String BOOK_TITLE = "title";
    private static final String BOOK_NEW_TITLE = "new_title";
    private static final String BOOK_AUTHORS = "author";
    private static final String BOOK_GENRE = "name";
    private static final String BOOK_DESCRIPTION = "description";

    @Configuration
    static class BookInfoServiceImplContextConfiguration {
        @Bean
        public BookInfoService bookInfoService(
                DictionaryService dictionaryService,
                BookInfoRepositoryJpa bookInfoRepositoryJpa
        ) {
            return new BookInfoServiceImpl(
                    dictionaryService,
                    bookInfoRepositoryJpa
            );
        }
    }

    @MockBean
    private BookInfoRepositoryJpa bookInfoRepositoryJpa;

    @MockBean
    private DictionaryService dictionaryService;

    @Autowired
    private BookInfoService bookInfoService;

    @Test
    void insertBook() {
        bookInfoService.insertBook(BOOK_TITLE, BOOK_AUTHORS, BOOK_GENRE, BOOK_DESCRIPTION);
        verify(bookInfoRepositoryJpa, times(1)).save(any());
    }

    @SneakyThrows
    @Test
    void updateTitleBookById() {
        when(bookInfoRepositoryJpa.findById(anyLong())).thenReturn(Optional.of(new Book()));
        bookInfoService.updateTitleBookById(BOOK_ID, BOOK_NEW_TITLE);

        verify(bookInfoRepositoryJpa, times(1)).findById(BOOK_ID);
        verify(bookInfoRepositoryJpa, times(1)).save(any());
    }

    @SneakyThrows
    @Test
    void deleteBookById() {
        when(bookInfoRepositoryJpa.findById(anyLong())).thenReturn(Optional.of(new Book()));
        bookInfoService.deleteBookById(BOOK_ID);

        verify(bookInfoRepositoryJpa, times(1)).delete(any());
    }

    @Test
    void getAllBooks() {
        bookInfoService.getAllBooks();
        verify(bookInfoRepositoryJpa, times(1)).findAll();
    }
}