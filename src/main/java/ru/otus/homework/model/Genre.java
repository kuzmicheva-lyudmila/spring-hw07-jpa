package ru.otus.homework.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "book_genres")
public class Genre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String genre;

    @OneToMany(mappedBy = "genre", fetch = FetchType.LAZY)
    public List<Book> books;

    @Override
    public String toString() {
        return "Genre{" + "id=" + id + ", genre='" + genre + "\'}";
    }
}
