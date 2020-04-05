package ru.otus.homework.service;

import ru.otus.homework.model.Post;

import java.util.List;

public interface PostService {
    Post insertPostByBook(long bookId, String description);
    boolean deletePostsByBook(long bookId);
    List<Post> getPostsByBook(long bookId);
}
