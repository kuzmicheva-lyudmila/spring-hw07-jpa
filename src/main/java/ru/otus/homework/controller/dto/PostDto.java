package ru.otus.homework.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.otus.homework.model.Post;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostDto {
    private String description;

    public static PostDto toDto(Post post) {
        return new PostDto(post.getDescription());
    }
}
