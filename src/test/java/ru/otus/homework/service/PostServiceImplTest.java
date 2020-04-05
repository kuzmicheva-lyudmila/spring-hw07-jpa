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
import ru.otus.homework.repository.PostRepositoryJpa;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@DisplayName("Сервис для работы с комментариями к книгам")
@SpringBootTest
class PostServiceImplTest {

    private static final long BOOK_ID = 1L;
    private static final String POST_DESCRIPTION = "description";

    @Configuration
    static class PostServiceImplContextConfiguration {
        @Bean
        public PostService postService(
                BookInfoService bookInfoRepositoryJpa,
                PostRepositoryJpa postRepositoryJpa
        ) {
            return new PostServiceImpl(
                    bookInfoRepositoryJpa,
                    postRepositoryJpa
            );
        }
    }

    @MockBean
    private BookInfoService bookInfoService;

    @MockBean
    private PostRepositoryJpa postRepositoryJpa;

    @Autowired
    private PostService postService;

    @SneakyThrows
    @Test
    void insertPost() {
        when(bookInfoService.getBookById(BOOK_ID)).thenReturn(new Book());

        postService.insertPostByBook(BOOK_ID, POST_DESCRIPTION);
        verify(bookInfoService, times(1)).getBookById(BOOK_ID);
        verify(postRepositoryJpa, times(1)).save(any());
    }

    @SneakyThrows
    @Test
    void deleteBookById() {
        Book book = new Book();
        when(bookInfoService.getBookById(BOOK_ID)).thenReturn(book);
        when(postRepositoryJpa.findByBook(book)).thenReturn(List.of(new Post()));
        postService.deletePostsByBook(BOOK_ID);

        verify(bookInfoService, times(1)).getBookById(BOOK_ID);
        verify(postRepositoryJpa, times(1)).delete(any(Post.class));
    }

    @SneakyThrows
    @Test
    void getAllBooks() {
        when(bookInfoService.getBookById(anyLong())).thenReturn(new Book());

        postService.getPostsByBook(BOOK_ID);
        verify(bookInfoService, times(1)).getBookById(BOOK_ID);
        verify(postRepositoryJpa, times(1)).findByBook(any());
    }
}