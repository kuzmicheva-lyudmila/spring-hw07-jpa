package ru.otus.homework.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
class BookDto {
    private String fullname;
    private String genre;
    private String authors;
    private String description;
}
