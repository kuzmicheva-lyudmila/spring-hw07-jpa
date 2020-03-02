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
import ru.otus.homework.repository.AuthorRepositoryJpa;
import ru.otus.homework.repository.BookInfoRepositoryJpa;
import ru.otus.homework.repository.GenreRepositoryJpa;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@DisplayName("Сервис для работы с книгами")
@SpringBootTest
class BookInfoServiceImplTest {

    @Configuration
    static class BookInfoServiceImplContextConfiguration {
        @Bean
        public BookInfoService bookInfoService(
                CommunicationService communicationService,
                BookInfoRepositoryJpa bookInfoRepositoryJpa,
                AuthorRepositoryJpa authorRepositoryJpa,
                GenreRepositoryJpa genreRepositoryJpa
        ) {
            return new BookInfoServiceImpl(
                    communicationService,
                    bookInfoRepositoryJpa,
                    authorRepositoryJpa,
                    genreRepositoryJpa
            );
        }
    }

    @MockBean
    BookInfoRepositoryJpa bookInfoRepositoryJpa;

    @MockBean
    AuthorRepositoryJpa authorRepositoryJpa;

    @MockBean
    GenreRepositoryJpa genreRepositoryJpa;

    @MockBean
    CommunicationService communicationService;

    @Autowired
    BookInfoService bookInfoService;

    @Test
    void insertBook() {
        bookInfoService.insertBook();
        verify(bookInfoRepositoryJpa, times(1)).save(any());
    }

    @SneakyThrows
    @Test
    void updateTitleBookById() {
        when(communicationService.getUserInputString(any(), any(), (String) any()))
                .thenReturn(String.valueOf(1));
        when(bookInfoRepositoryJpa.findById(anyLong())).thenReturn(Optional.of(new Book()));
        bookInfoService.updateTitleBookById();

        verify(bookInfoRepositoryJpa, times(1)).findById(1L);
        verify(bookInfoRepositoryJpa, times(1)).save(any());
    }

    @SneakyThrows
    @Test
    void deleteBookById() {
        when(communicationService.getUserInputString(any(), any(), (String) any()))
                .thenReturn(String.valueOf(1));
        when(bookInfoRepositoryJpa.findById(anyLong())).thenReturn(Optional.of(new Book()));
        bookInfoService.deleteBookById();

        verify(bookInfoRepositoryJpa, times(1)).delete(any());
    }

    @Test
    void getAllBooks() {
        bookInfoService.getAllBooks();
        verify(bookInfoRepositoryJpa, times(1)).findAll();
    }
}