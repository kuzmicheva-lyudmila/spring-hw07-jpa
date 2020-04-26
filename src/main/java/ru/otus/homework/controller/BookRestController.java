package ru.otus.homework.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.homework.controller.dto.BookDto;
import ru.otus.homework.controller.dto.PostDto;
import ru.otus.homework.model.Book;
import ru.otus.homework.model.Post;
import ru.otus.homework.service.BookInfoService;
import ru.otus.homework.service.PostService;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@RestController
public class BookRestController {
    private final BookInfoService bookInfoService;
    private final PostService postService;

    public BookRestController(
            BookInfoService bookInfoService,
            PostService postService
    ) {
        this.bookInfoService = bookInfoService;
        this.postService = postService;
    }

    @GetMapping("/api/books")
    public List<BookDto> getBooks() {
        List<Book> books = bookInfoService.getAllBooks();
        return getResponseForList(books, BookDto::toDto);
    }

    @GetMapping("/api/books/{id}")
    public BookDto getBookById(@PathVariable("id") Long id) {
        Book book = bookInfoService.getBookById(id);
        return book == null ? new BookDto() : BookDto.toDto(book);
    }

    @GetMapping("/api/books/{id}/posts")
    public List<PostDto> getPostsByBookId(@PathVariable("id") Long id) {
        List<Post> posts = postService.getPostsByBook(id);
        return getResponseForList(posts, PostDto::toDto);
    }

    private <T, R> List<R> getResponseForList(List<T> list, Function<T, R> toDto) {
        if (list != null) {
            return list.stream()
                    .map(toDto)
                    .collect(Collectors.toList());
        }
        return Collections.emptyList();
    }
}
