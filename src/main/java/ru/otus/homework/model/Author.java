package ru.otus.homework.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "authors")
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(name = "description")
    private String description;

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "authors")
    private Set<Book> books;

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Author{ id=");
        stringBuilder.append(id);
        stringBuilder.append(", fullName='");
        stringBuilder.append(fullName);
        stringBuilder.append("', description='");
        stringBuilder.append(description);
        stringBuilder.append("', books=[");

        books.forEach(book -> {
                    stringBuilder.append(book.getFullName());
                    stringBuilder.append("; ");
                }
        );

        return stringBuilder.append("]}").toString();
    }
}
