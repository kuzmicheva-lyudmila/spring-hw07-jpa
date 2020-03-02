package ru.otus.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.homework.model.Book;
import ru.otus.homework.model.Post;

import java.util.List;

public interface PostRepositoryJpa extends JpaRepository<Post, Long> {
    Post save(Post post);
    List<Post> findByBook(Book book);
    void delete(Post post);
}
