package ru.otus.homework.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.homework.repository.AuthorRepositoryJpa;
import ru.otus.homework.repository.GenreRepositoryJpa;

import static org.mockito.Mockito.*;

@DisplayName("Сервис для работы со справочниками")
@SpringBootTest
public class DictionaryServiceImplTest {

    @Configuration
    static class DictionaryServiceImplContextConfiguration {
        @Bean
        public DictionaryService dictionaryService(
                AuthorRepositoryJpa authorRepositoryJpa,
                GenreRepositoryJpa genreRepositoryJpa
        ) {
            return new DictionaryServiceImpl(
                    authorRepositoryJpa,
                    genreRepositoryJpa
            );
        }
    }

    @MockBean
    private AuthorRepositoryJpa authorRepositoryJpa;

    @MockBean
    private GenreRepositoryJpa genreRepositoryJpa;

    @Autowired
    private DictionaryService dictionaryService;

    @Test
    void showAuthors() {
        dictionaryService.getAuthors();
        verify(authorRepositoryJpa, times(1)).findAll();
    }

    @Test
    void getBookGenres() {
        dictionaryService.getGenres();
        verify(genreRepositoryJpa, times(1)).findAll();
    }
}
