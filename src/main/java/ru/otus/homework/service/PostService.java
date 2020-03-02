package ru.otus.homework.service;

import java.io.UnsupportedEncodingException;

public interface PostService {
    void insertPostByBook() throws UnsupportedEncodingException;
    void deletePostsByBook();
    void getPostsByBook();
}
