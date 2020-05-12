package ru.otus.homework.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.otus.homework.model.Author;
import ru.otus.homework.model.Book;

import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookDto {
    private Long id;
    private String fullname;
    private String genre;
    private String authors;
    private String description;

    public static BookDto toDto(Book book) {
        return new BookDto(
                book.getId(),
                book.getFullName(),
                book.getGenre() != null ? book.getGenre().getGenre() : "",
                book.getAuthors() != null
                        ? book.getAuthors().stream().map(Author::getFullName).collect(Collectors.joining())
                        : "",
                book.getBookDescription()
        );
    }
}
