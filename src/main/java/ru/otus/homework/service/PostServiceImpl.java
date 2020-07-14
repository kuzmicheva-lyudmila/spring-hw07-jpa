package ru.otus.homework.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
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

    @HystrixCommand(commandKey="insertPostByBook")
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

    @HystrixCommand(commandKey="deletePostsByBook")
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

    @HystrixCommand(commandKey="getPostsByBook", fallbackMethod="buildFallbackPostList")
    @Override
    public List<Post> getPostsByBook(long bookId) {
        Book book = bookInfoService.getBookById(bookId);
        if (book != null) {
            return postRepositoryJpa.findByBook(book);
        } else {
            return Collections.emptyList();
        }
    }

    public List<Post> buildFallbackPostList(long bookId) {
        return Collections.emptyList();
    }

    @HystrixCommand(commandKey="getAveragePostCountOnBook", fallbackMethod="buildFallbackAveragePostCount")
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

    public Long buildFallbackAveragePostCount() {
        return 0L;
    }
}
