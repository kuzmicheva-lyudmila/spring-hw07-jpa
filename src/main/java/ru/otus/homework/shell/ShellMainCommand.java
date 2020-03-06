package ru.otus.homework.shell;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.homework.model.Author;
import ru.otus.homework.model.Book;
import ru.otus.homework.model.Genre;
import ru.otus.homework.model.Post;
import ru.otus.homework.service.BookInfoService;
import ru.otus.homework.service.CommunicationService;
import ru.otus.homework.service.DictionaryService;
import ru.otus.homework.service.PostService;

import java.io.UnsupportedEncodingException;
import java.util.List;

@ShellComponent
public class ShellMainCommand {
    private final BookInfoService bookInfoService;
    private final DictionaryService dictionaryService;
    private final PostService postService;
    private final CommunicationService communicationService;

    public ShellMainCommand(
            BookInfoService bookInfoService,
            DictionaryService dictionaryService,
            PostService postService,
            CommunicationService communicationService
    ) {
        this.bookInfoService = bookInfoService;
        this.dictionaryService = dictionaryService;
        this.postService = postService;
        this.communicationService = communicationService;
    }

    @ShellMethod(value = "InsertBook", key = {"insertBook", "ib"})
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void insertBook() throws UnsupportedEncodingException {
        String title = communicationService.getUserInputString(
                "Введите наименование книги",
                "Некорректное наименование! Введите наименование еще раз",
                "[^.]+"
        );

        String description = communicationService.getUserInputString(
                "Введите описание книги",
                "Некорректное описание! Введите описание еще раз",
                "[^.]+"
        );

        String authors = communicationService.getUserInputString(
                "Введите авторов книги (разделитель ';')",
                "Некорректный автор! Введите авторов еще раз",
                "[^.]+"
        );

        String genre = communicationService.getUserInputString(
                "Введите жанр книги",
                "Некорректный жанр книги! Введите жанр еще раз",
                dictionaryService.getGenreNames()
        );

        Book book = bookInfoService.insertBook(title, authors, genre, description);
        communicationService.showResultMessage(
                book != null,
                "inserted: " + book,
                "error on inserting book"
        );
    }

    @ShellMethod(value = "UpdateBook", key = {"updateBook", "ub"})
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void updateBook() throws UnsupportedEncodingException {
        long id = Long.parseLong(
                communicationService.getUserInputString(
                        "Введите идентификатор книги",
                        "Некорректный идентификатор! Введите еще раз",
                        "[^d]+"
                )
        );

        String title = communicationService.getUserInputString(
                "Введите наименование книги",
                "Некорректное наименование! Введите еще раз",
                "[^.]+"
        );

        Book book = bookInfoService.updateTitleBookById(id, title);
        communicationService.showResultMessage(
                book != null,
                "updated book: " + book,
                "error on updating book"
        );
     }

    @ShellMethod(value = "DeleteBook", key = {"deleteBook", "db"})
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void deleteBook() throws UnsupportedEncodingException {
        long id = Long.parseLong(
                communicationService.getUserInputString(
                        "Введите идентификатор книги",
                        "Некорректный идентификатор! Введите еще раз",
                        "[^d]+"
                )
        );

        boolean result = bookInfoService.deleteBookById(id);
        communicationService.showResultMessage(
                result,
                "deleted book with id = " + id,
                "error on updating book"
        );
    }

    @ShellMethod(value = "AllBooks", key = {"showBooks", "sb"})
    @Transactional(readOnly = true)
    public void showBooks() throws UnsupportedEncodingException {
        List<Book> books = bookInfoService.getAllBooks();
        communicationService.showResultMessageList(books);
    }

    @ShellMethod(value = "AllGenres", key = {"showGenres", "sg"})
    @Transactional(readOnly = true)
    public void getGenres() throws UnsupportedEncodingException {
        List<Genre> genres = dictionaryService.getGenres();
        communicationService.showResultMessageList(genres);
    }

    @ShellMethod(value = "AllAuthors", key = {"showAuthors", "sa"})
    @Transactional(readOnly = true)
    public void getAuthors() throws UnsupportedEncodingException {
        List<Author> authors = dictionaryService.getAuthors();
        communicationService.showResultMessageList(authors);
    }

    @ShellMethod(value = "SetPostOnBook", key = {"setPost", "sp"})
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void setPost() throws UnsupportedEncodingException {
        long id = Long.parseLong(
                communicationService.getUserInputString(
                "Введите идентификатор книги",
                "Некорректный идентификатор! Введите еще раз",
                "[^d]+"
                )
        );
        String description = communicationService.getUserInputString(
                "Введите комментарий к книге",
                "Некорректный комментарий! Введите еще раз",
                "[^.]+"
        );

        Post post = postService.insertPostByBook(id, description);
        communicationService.showResultMessage(
                post != null,
                "inserted post: " + post,
                "error on inserting post"
        );
    }

    @ShellMethod(value = "DeletePostsOnBook", key = {"deletePosts", "dp"})
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void deletePosts() throws UnsupportedEncodingException {
        long id = Long.parseLong(
                communicationService.getUserInputString(
                        "Введите идентификатор книги",
                        "Некорректный идентификатор! Введите еще раз",
                        "[^d]+"
                )
        );
        boolean result = postService.deletePostsByBook(id);
        communicationService.showResultMessage(
                result,
                "deleted posts for book with id = " + id,
                "error on deleting posts"
        );
    }

    @ShellMethod(value = "GetPostsOnBook", key = {"getPosts", "gp"})
    @Transactional(readOnly = true)
    public void getPosts() throws UnsupportedEncodingException {
        long id = Long.parseLong(
                communicationService.getUserInputString(
                        "Введите идентификатор книги",
                        "Некорректный идентификатор! Введите еще раз",
                        "[^d]+"
                )
        );
        List<Post> posts = postService.getPostsByBook(id);
        communicationService.showResultMessageList(posts);
    }
}
