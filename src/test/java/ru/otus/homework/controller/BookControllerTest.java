package ru.otus.homework.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.homework.model.Book;
import ru.otus.homework.repository.AuthorRepositoryJpa;
import ru.otus.homework.repository.BookInfoRepositoryJpa;
import ru.otus.homework.repository.GenreRepositoryJpa;
import ru.otus.homework.repository.PostRepositoryJpa;
import ru.otus.homework.repository.UserRepositoryJpa;
import ru.otus.homework.service.BookInfoService;
import ru.otus.homework.service.DictionaryService;
import ru.otus.homework.service.PostService;

import java.util.Collections;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookController.class)
public class BookControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private BookInfoService bookInfoService;

    @MockBean
    private PostService postService;

    @MockBean
    private DictionaryService dictionaryService;

    @MockBean
    private UserDetailsService userDetailsService;

    @MockBean
    private BookInfoRepositoryJpa bookInfoRepositoryJpa;

    @MockBean
    private GenreRepositoryJpa genreRepositoryJpa;

    @MockBean
    private AuthorRepositoryJpa authorRepositoryJpa;

    @MockBean
    private PostRepositoryJpa postRepositoryJpa;

    @MockBean
    private UserRepositoryJpa userRepositoryJpa;

    @WithMockUser(
            username = "user",
            authorities = {"ROLE_USER"}
    )

    @Test
    public void test() throws Exception {
        List<Book> books = Collections.singletonList(new Book(0L, "new book", null, null, null));
        given(bookInfoService.getAllBooks())
                .willReturn(books);
        this.mvc.perform(get("/list"))
                .andExpect(status().isOk())
                .andExpect(content().string(ControllerAnswer.RESULT));
    }
}
