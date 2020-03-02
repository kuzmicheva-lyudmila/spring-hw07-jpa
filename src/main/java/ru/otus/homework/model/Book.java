package ru.otus.homework.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @ManyToOne
    @JoinColumn(name = "genre_id", nullable = false, updatable = false)
    private Genre genre;

    @Fetch(FetchMode.SUBSELECT)
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "book_authors", joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id"))
    private List<Author> authors;

    @Column(name = "book_description")
    private String bookDescription;

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder(
                "Book{" + "id=" + id + ", fullName='" + fullName + '\''
        );
        stringBuilder.append(", genre=");
        stringBuilder.append(genre.getGenre());
        stringBuilder.append(", authors=[");

        authors.forEach(author -> {
                    stringBuilder.append(author.getFullName());
                    stringBuilder.append("; ");
                }
        );
        stringBuilder.append("], bookDescription='");
        stringBuilder.append(bookDescription);
        stringBuilder.append("'}");
        return stringBuilder.toString();
    }
}
