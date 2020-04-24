package ru.otus.homework.service;

import lombok.SneakyThrows;
import org.apache.logging.log4j.util.Strings;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.homework.model.Genre;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Сервис для ввода/вывода данных")
@SpringBootTest
public class CommunicationServiceImplTest {

    private static final String EXAMPLE_OF_BOOK_GENRE = "history";

    @Configuration
    static class CommunicationServiceImplContextConfiguration {
        @Bean
        public CommunicationService communicationService() {
            return new CommunicationServiceImpl();
        }
    }

    @Autowired
    private CommunicationService communicationService;

    @SneakyThrows
    @Test
    void getUserInputStringWithList() {
        Genre bookGenre = new Genre(1L, EXAMPLE_OF_BOOK_GENRE, null);

        InputStream in = new ByteArrayInputStream(EXAMPLE_OF_BOOK_GENRE.getBytes());
        System.setIn(in);

        assertThat(communicationService.getUserInputString(
                Strings.EMPTY, Strings.EMPTY, Collections.singletonList(bookGenre.getName())
        )).isEqualTo(bookGenre.getName());
    }
}
