package ru.otus.homework.repository;

import lombok.val;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.homework.model.Author;
import ru.otus.homework.model.Book;
import ru.otus.homework.model.Genre;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий на основе Jpa для работы с книгами")
@DataJpaTest
public class BookInfoRepositoryJpaImplTest {
    private static final int EXPECTED_NUMBER_OF_BOOKS = 2;
    private static final long FIRST_BOOK_ID = 1L;
    private static final long GENRE_ID = 2L;
    private static final long AUTHOR_ID = 1L;
    private static final long NEW_BOOK_ID = 3L;
    private static final String NEW_BOOK_FULLNAME = "new book3";
    private static final String NEW_BOOK_DESCRIPTION = "description";
    private static final long DELETE_BOOK_ID = 2L;

    @Autowired
    private BookInfoRepositoryJpa repositoryJpa;

    @Autowired
    private TestEntityManager em;

    @DisplayName("должен загружать информацию о книге по ID")
    @Test
    void shouldFindExpectedBookById() {
        val book = repositoryJpa.findById(FIRST_BOOK_ID);
        val expectedBook = em.find(Book.class, FIRST_BOOK_ID);
        assertThat(book).isPresent().get()
            .isEqualToComparingFieldByField(expectedBook);
    }

    @DisplayName("должен загружать список всех книг")
    @Test
    void shouldReturnCorrectBooksListWithAllInfo() {
        SessionFactory sessionFactory = em.getEntityManager().getEntityManagerFactory()
                .unwrap(SessionFactory.class);
        sessionFactory.getStatistics().setStatisticsEnabled(true);

        val books = repositoryJpa.findAll();

        assertThat(books).isNotNull().hasSize(EXPECTED_NUMBER_OF_BOOKS)
                .allMatch(b -> !b.getFullName().equals(""))
                .allMatch(b -> b.getGenre() != null)
                .allMatch(b -> b.getAuthors() != null)
                .allMatch(b -> !b.getBookDescription().equals(""));
    }

    @DisplayName("должен сохранять новую книгу")
    @Test
    void shouldSaveBook() {
        Genre genre = em.find(Genre.class, GENRE_ID);
        List<Author> authors = List.of(em.find(Author.class, AUTHOR_ID));

        Book book = new Book(0L, "new book3", genre, authors, "description");
        val newBook = repositoryJpa.save(book);

        assertThat(newBook).isNotNull()
                .matches(b -> b.getId() == NEW_BOOK_ID)
                .matches(b -> b.getFullName().equals(NEW_BOOK_FULLNAME))
                .matches(b -> b.getBookDescription().equals(NEW_BOOK_DESCRIPTION))
                .matches(b -> b.getGenre() != null)
                .matches(b -> b.getAuthors() != null && b.getAuthors().size() > 0);
    }

    @DisplayName("должен удалять книгу")
    @Test
    void shouldDeleteBook() {
        val book = em.find(Book.class, DELETE_BOOK_ID);
        repositoryJpa.delete(book);

        val deletedBook = em.find(Book.class, DELETE_BOOK_ID);
        assertThat(deletedBook).isNull();
    }
}
