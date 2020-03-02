package ru.otus.homework.service;

import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.homework.model.Book;
import ru.otus.homework.model.Post;
import ru.otus.homework.repository.BookInfoRepositoryJpa;
import ru.otus.homework.repository.PostRepositoryJpa;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Optional;

@Service
public class PostServiceImpl implements PostService {

    private final CommunicationService communicationService;
    private final BookInfoRepositoryJpa bookInfoRepositoryJpa;
    private final PostRepositoryJpa postRepositoryJpa;

    public PostServiceImpl(
            CommunicationService communicationService,
            BookInfoRepositoryJpa bookInfoRepositoryJpa,
            PostRepositoryJpa postRepositoryJpa
    ) {
        this.communicationService = communicationService;
        this.bookInfoRepositoryJpa = bookInfoRepositoryJpa;
        this.postRepositoryJpa = postRepositoryJpa;
    }

    @SneakyThrows
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void insertPostByBook() {
        Book book = getBook();
        if (book != null) {
            String description = communicationService.getUserInputString(
                    "Введите комментарий к книге",
                    "Некорректный комментарий! Введите еще раз",
                    "[^.]+"
            );
            Post post = postRepositoryJpa.save(new Post(0L, book, description));
            if (post != null) {
                communicationService.showMessage("inserted: " + post.toString());
            } else {
                communicationService.showMessage("the book was not insert");
            }
        } else {
            communicationService.showMessage("the book was not found");
        }
    }

    @SneakyThrows
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void deletePostsByBook() {
        Book book = getBook();
        if (book != null) {
            List<Post> posts = postRepositoryJpa.findByBook(book);
            posts.forEach(
                postRepositoryJpa::delete
            );
            communicationService.showMessage("deleted posts");
        } else {
            communicationService.showMessage("the book was not found");
        }
    }

    @SneakyThrows
    @Override
    public void getPostsByBook() {
        Book book = getBook();
        if (book != null) {
            List<Post> posts = postRepositoryJpa.findByBook(book);
            if (posts.size() > 0) {
                for (Post post : posts) {
                    communicationService.showMessage(post.toString());
                }
            } else {
                communicationService.showMessage("the posts was not found");
            }
        } else {
            communicationService.showMessage("the book was not found");
        }
    }

    private Book getBook() throws UnsupportedEncodingException {
        long bookId = Long.parseLong(
                communicationService.getUserInputString(
                        "Введите идентификатор книги",
                        "Некорректный идентификатор! Введите еще раз",
                        "[^d]+"
                )
        );
        Optional<Book> optionalBook = bookInfoRepositoryJpa.findById(bookId);
        return optionalBook.orElse(null);
    }
}
