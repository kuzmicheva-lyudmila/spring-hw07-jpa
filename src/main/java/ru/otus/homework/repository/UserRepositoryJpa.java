package ru.otus.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.homework.model.User;

import java.util.Optional;

public interface UserRepositoryJpa extends JpaRepository<User, Long> {

    Optional<User> findByUserName(String userName);
}
