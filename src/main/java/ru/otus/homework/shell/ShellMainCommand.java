package ru.otus.homework.shell;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.homework.service.BookInfoService;
import ru.otus.homework.service.DictionaryService;
import ru.otus.homework.service.PostService;

import java.io.UnsupportedEncodingException;

@ShellComponent
public class ShellMainCommand {
    private final BookInfoService bookInfoService;
    private final DictionaryService dictionaryService;
    private final PostService postService;

    public ShellMainCommand(
            BookInfoService bookInfoService,
            DictionaryService dictionaryService,
            PostService postService
    ) {
        this.bookInfoService = bookInfoService;
        this.dictionaryService = dictionaryService;
        this.postService = postService;
    }

    @ShellMethod(value = "InsertBook", key = {"insertBook", "ib"})
    public void insertBook() {
        bookInfoService.insertBook();
    }

    @ShellMethod(value = "UpdateBook", key = {"updateBook", "ub"})
    public void updateBook() {
        bookInfoService.updateTitleBookById();
    }

    @ShellMethod(value = "DeleteBook", key = {"deleteBook", "db"})
    public void deleteBook() {
        bookInfoService.deleteBookById();
    }

    @ShellMethod(value = "AllBooks", key = {"showBooks", "sb"})
    public void showBooks() {
        bookInfoService.getAllBooks();
    }

    @ShellMethod(value = "AllGenres", key = {"showGenres", "sg"})
    public void getGenres() {
        dictionaryService.showBookGenres();
    }

    @ShellMethod(value = "AllAuthors", key = {"showAuthors", "sa"})
    public void getAuthors() { dictionaryService.showAuthors(); }

    @ShellMethod(value = "SetPostOnBook", key = {"setPost", "sp"})
    public void setPost() throws UnsupportedEncodingException {
        postService.insertPostByBook();
    }

    @ShellMethod(value = "DeletePostsOnBook", key = {"deletePosts", "dp"})
    public void deletePosts() { postService.deletePostsByBook(); }

    @ShellMethod(value = "GetPostsOnBook", key = {"getPosts", "gp"})
    public void getPosts() { postService.getPostsByBook(); }
}
