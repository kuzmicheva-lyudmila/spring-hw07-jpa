package ru.otus.homework.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.homework.model.Author;
import ru.otus.homework.model.Book;
import ru.otus.homework.model.Genre;
import ru.otus.homework.model.Post;
import ru.otus.homework.service.BookInfoService;
import ru.otus.homework.service.DictionaryService;
import ru.otus.homework.service.PostService;

import java.util.List;
import java.util.stream.Collectors;


@Controller
public class BookController {

    private static final String HAS_ERROR = "HAS ERROR!!!";

    private final BookInfoService bookInfoService;
    private final PostService postService;
    private final DictionaryService dictionaryService;

    public BookController(
            BookInfoService bookInfoService,
            PostService postService,
            DictionaryService dictionaryService
    ) {
        this.bookInfoService = bookInfoService;
        this.postService = postService;
        this.dictionaryService = dictionaryService;
    }

    @GetMapping({"/", "/list"})
    public String listBooks(Model model) {
        List<Book> books = bookInfoService.getAllBooks();
        model.addAttribute("books", books);
        return "list";
    }

    @GetMapping("/delete")
    public String deleteBook(
            @RequestParam("id") long id,
            ModelMap model
    ) {
        bookInfoService.deleteBookById(id);
        model.clear();
        return "redirect:/list";
    }

    @GetMapping("/view")
    public String viewBook(
            @RequestParam("id") long id,
            ModelMap model
    ) {
        Book book = bookInfoService.getBookById(id);
        List<Post> posts = postService.getPostsByBook(id);

        model.addAttribute("book", book);
        model.addAttribute("posts", posts);
        return "view";
    }

    @PostMapping("/posts")
    public String addPost(
            @RequestParam("id") long id,
            @ModelAttribute("post") String description,
            ModelMap model
    ) {
        postService.insertPostByBook(id, description);
        model.clear();
        return "redirect:/view?id=" + id;
    }

    @DeleteMapping("/posts")
    public String deletePosts(
            @RequestParam("id") long id,
            ModelMap model
    ) {
        postService.deletePostsByBook(id);
        model.clear();
        return "redirect:/view?id=" + id;
    }

    @GetMapping("/add")
    public String _addBook(
            ModelMap model
    ) {
        model.clear();
        return "add";
    }

    @PostMapping("/add")
    public String addBook(
            @ModelAttribute("fullName") String fullName,
            @ModelAttribute("authors") String authors,
            @ModelAttribute("genre") String genre,
            @ModelAttribute("description") String description,
            ModelMap model
    ) {
        if (dictionaryService.getGenreByName(genre) == null) {
            model.addAttribute("fullName", fullName);
            model.addAttribute("authors", authors);
            model.addAttribute("genre", HAS_ERROR);
            model.addAttribute("description", description);
            return "add";
        }

        bookInfoService.insertBook(fullName, authors, genre, description);
        model.clear();
        return "redirect:/list";
    }

    @GetMapping("/update")
    public String getBookForUpdate(
            @RequestParam("id") long id,
            ModelMap model
    ) {
        Book book = bookInfoService.getBookById(id);
        String authors = book.getAuthors().stream()
                .map(Author::getFullName)
                .collect(Collectors.joining());

        model.addAttribute("bookId", book.getId());
        model.addAttribute("bookDto", new BookDto(book.getFullName(), book.getGenre().getName(), authors, book.getBookDescription()));
        return "update";
    }

    @PostMapping("/update")
    public String updateBook(
            @RequestParam("id") long id,
            @ModelAttribute BookDto bookDto,
            ModelMap model
    ) {
        Genre newGenre = dictionaryService.getGenreByName(bookDto.getGenre());
        if (newGenre == null) {
            BookDto newBookDto = new BookDto(bookDto.getFullname(), HAS_ERROR, bookDto.getAuthors(), bookDto.getDescription());
            model.addAttribute("bookDto", newBookDto);
            model.addAttribute("bookId", id);
            return "update";
        }
        bookInfoService.updateBookById(
                id,
                bookDto.getFullname(),
                bookDto.getAuthors(),
                newGenre,
                bookDto.getDescription()
        );
        model.clear();
        return "redirect:/list";
    }
}
