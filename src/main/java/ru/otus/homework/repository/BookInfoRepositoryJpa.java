package ru.otus.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.homework.model.Book;

import java.util.List;
import java.util.Optional;

public interface BookInfoRepositoryJpa extends JpaRepository<Book, Long> {
    @SuppressWarnings("unchecked")
    Book save(Book book);

    Optional<Book> findById(long id);
    List<Book> findAll();

    void delete(Book book);
}
