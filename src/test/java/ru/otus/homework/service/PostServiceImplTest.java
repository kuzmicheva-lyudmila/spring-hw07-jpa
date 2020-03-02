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
import ru.otus.homework.model.Post;
import ru.otus.homework.repository.AuthorRepositoryJpa;
import ru.otus.homework.repository.BookInfoRepositoryJpa;
import ru.otus.homework.repository.GenreRepositoryJpa;
import ru.otus.homework.repository.PostRepositoryJpa;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@DisplayName("Сервис для работы с комментариями к книгам")
@SpringBootTest
class PostServiceImplTest {

    @Configuration
    static class PostServiceImplContextConfiguration {
        @Bean
        public PostService postService(
                CommunicationService communicationService,
                BookInfoRepositoryJpa bookInfoRepositoryJpa,
                PostRepositoryJpa postRepositoryJpa
        ) {
            return new PostServiceImpl(
                    communicationService,
                    bookInfoRepositoryJpa,
                    postRepositoryJpa
            );
        }
    }

    @MockBean
    BookInfoRepositoryJpa bookInfoRepositoryJpa;

    @MockBean
    PostRepositoryJpa postRepositoryJpa;

    @MockBean
    CommunicationService communicationService;

    @Autowired
    PostService postService;

    @SneakyThrows
    @Test
    void insertPost() {
        when(communicationService.getUserInputString(any(), any(), (String) any()))
                .thenReturn(String.valueOf(1));
        when(bookInfoRepositoryJpa.findById(1L)).thenReturn(Optional.of(new Book()));

        postService.insertPostByBook();
        verify(bookInfoRepositoryJpa, times(1)).findById(1L);
        verify(postRepositoryJpa, times(1)).save(any());
    }

    @SneakyThrows
    @Test
    void deleteBookById() {
        when(communicationService.getUserInputString(any(), any(), (String) any()))
                .thenReturn(String.valueOf(1));

        Book book = new Book();
        when(bookInfoRepositoryJpa.findById(1L)).thenReturn(Optional.of(book));
        when(postRepositoryJpa.findByBook(book)).thenReturn(List.of(new Post()));
        postService.deletePostsByBook();

        verify(bookInfoRepositoryJpa, times(1)).findById(1L);
        verify(postRepositoryJpa, times(1)).delete(any(Post.class));
    }

    @SneakyThrows
    @Test
    void getAllBooks() {
        when(communicationService.getUserInputString(any(), any(), (String) any()))
                .thenReturn(String.valueOf(1));
        when(bookInfoRepositoryJpa.findById(anyLong())).thenReturn(Optional.of(new Book()));

        postService.getPostsByBook();
        verify(bookInfoRepositoryJpa, times(1)).findById(1L);
        verify(postRepositoryJpa, times(1)).findByBook(any());
    }
}