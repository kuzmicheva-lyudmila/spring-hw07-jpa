package ru.otus.homework.repository;

import lombok.val;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.homework.model.Author;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий на основе Jpa для работы с авторами")
@DataJpaTest
class AuthorRepositoryJpaImplTest {

    private static final int EXPECTED_NUMBER_OF_AUTHORS = 2;
    private static final String FIRST_AUTHOR_FULLNAME = "author1";
    private static final long FIRST_AUTHOR_ID = 1L;
    private static final long NEW_AUTHOR_ID = 3L;
    private static final String NEW_AUTHOR_FULLNAME = "author3";
    private static final String NEW_AUTHOR_DESCRIPTION = "description";

    @Autowired
    private AuthorRepositoryJpa repositoryJpa;

    @Autowired
    private TestEntityManager em;

    @DisplayName("должен загружать информацию о нужном авторе по его полному имени")
    @Test
    void shouldFindExpectedStudentByFullname() {
        val author = repositoryJpa.findByFullName(FIRST_AUTHOR_FULLNAME);
        val expectedAuthor = em.find(Author.class, FIRST_AUTHOR_ID);
        assertThat(author).isEqualToComparingFieldByField(expectedAuthor);
    }

    @DisplayName("должен загружать список всех авторов")
    @Test
    void shouldReturnCorrectAuthorsListWithAllInfo() {
        SessionFactory sessionFactory = em.getEntityManager().getEntityManagerFactory()
                .unwrap(SessionFactory.class);
        sessionFactory.getStatistics().setStatisticsEnabled(true);

        val authors = repositoryJpa.findAll();

        assertThat(authors).isNotNull().hasSize(EXPECTED_NUMBER_OF_AUTHORS)
                .allMatch(a -> !a.getFullName().equals(""))
                .allMatch(a -> !a.getDescription().equals(""));
    }

    @DisplayName("должен сохранять нового автора")
    @Test
    void shouldSaveAuthor() {
        SessionFactory sessionFactory = em.getEntityManager().getEntityManagerFactory()
                .unwrap(SessionFactory.class);
        sessionFactory.getStatistics().setStatisticsEnabled(true);

        val author = repositoryJpa.save(new Author(0L, NEW_AUTHOR_FULLNAME, NEW_AUTHOR_DESCRIPTION, null));

        assertThat(author).isNotNull()
                .matches(a -> a.getId() == NEW_AUTHOR_ID)
                .matches(a -> a.getFullName().equals(NEW_AUTHOR_FULLNAME))
                .matches(a -> a.getDescription().equals(NEW_AUTHOR_DESCRIPTION));
    }
}
