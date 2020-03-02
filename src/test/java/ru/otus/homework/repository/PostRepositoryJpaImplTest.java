package ru.otus.homework.repository;

import lombok.val;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.homework.model.Book;
import ru.otus.homework.model.Post;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий на основе Jpa для работы с комментариями к книгам")
@DataJpaTest
public class PostRepositoryJpaImplTest {
    private static final int EXPECTED_NUMBER_OF_POSTS = 3;
    private static final long BOOK_ID = 1L;
    private static final String NEW_POST_DESCRIPTION = "description";
    private static final long POST_ID = 1L;

    @Autowired
    private PostRepositoryJpa repositoryJpa;

    @Autowired
    private TestEntityManager em;

    @DisplayName("должен загружать информацию о комментариях по книге")
    @Test
    void shouldFindExpectedPostByBook() {
        SessionFactory sessionFactory = em.getEntityManager().getEntityManagerFactory()
                .unwrap(SessionFactory.class);
        sessionFactory.getStatistics().setStatisticsEnabled(true);

        val book = em.find(Book.class, BOOK_ID);
        val posts = repositoryJpa.findByBook(book);

        assertThat(posts).isNotNull().hasSize(EXPECTED_NUMBER_OF_POSTS)
                .allMatch(p -> p.getBook() != null)
                .allMatch(p -> !p.getDescription().equals(""));
    }

    @DisplayName("должен сохранять комментарий к книге")
    @Test
    void shouldSavePost() {
        val book = em.find(Book.class, BOOK_ID);
        val post = repositoryJpa.save(new Post(0L, book, "description"));

        assertThat(post).isNotNull()
                .matches(b -> b.getBook() != null)
                .matches(b -> b.getDescription().equals(NEW_POST_DESCRIPTION));
    }

    @DisplayName("должен удалять комментарии к книге")
    @Test
    void shouldDeletePosts() {
        val post = em.find(Post.class, POST_ID);
        repositoryJpa.delete(post);

        val deletedPost = em.find(Post.class, POST_ID);
        assertThat(deletedPost).isNull();
    }
}

