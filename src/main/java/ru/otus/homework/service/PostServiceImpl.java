package ru.otus.homework.service;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.otus.homework.model.Book;
import ru.otus.homework.model.Post;
import ru.otus.homework.repository.PostRepositoryJpa;

import java.util.Collections;
import java.util.List;

@Service
@Slf4j
public class PostServiceImpl implements PostService {

    private final BookInfoService bookInfoService;
    private final PostRepositoryJpa postRepositoryJpa;

    public PostServiceImpl(
            BookInfoService bookInfoService,
            PostRepositoryJpa postRepositoryJpa
    ) {
        this.bookInfoService = bookInfoService;
        this.postRepositoryJpa = postRepositoryJpa;
    }

    @SneakyThrows
    @Override
    public Post insertPostByBook(long bookId, String description) {
        try {
            Book book = bookInfoService.getBookById(bookId);
            if (book != null) {
                return postRepositoryJpa.save(new Post(0L, book, description));
            }
        } catch (Exception e) {
            log.error("error on inserting post", e);
        }
        return null;
    }

    @SneakyThrows
    @Override
    public boolean deletePostsByBook(long bookId) {
        try {
            Book book = bookInfoService.getBookById(bookId);
            if (book != null) {
                List<Post> posts = postRepositoryJpa.findByBook(book);
                posts.forEach(
                        postRepositoryJpa::delete
                );
                return true;
            }
        } catch (Exception e) {
            log.error("error on deleting post", e);
        }
        return false;
    }

    @SneakyThrows
    @Override
    public List<Post> getPostsByBook(long bookId) {
        Book book = bookInfoService.getBookById(bookId);
        if (book != null) {
            return postRepositoryJpa.findByBook(book);
        } else {
            return Collections.emptyList();
        }
    }

    @Override
    public Long getAveragePostCountOnBook() {
        long bookCount = bookInfoService.getBookCount();
        if (bookCount == 0) {
            return null;
        }

        long postCount = postRepositoryJpa.count();
        if (postCount == 0) {
            return 0L;
        }

        return postCount / bookCount;
    }
}
