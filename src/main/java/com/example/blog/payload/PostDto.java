package com.example.blog.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostDto {

    private Long id;

    @NotBlank(message = "Post title is required")
    @Size(min = 2, message = "Post title must have at least 2 characters")
    private String title;

    @NotBlank(message = "Post description is required")
    @Size(min = 5, message = "Post description must have at least 5 characters")
    private String description;

    @NotBlank(message = "Post content is required")
    private String content;

    private List<CommentDto> comments;
}
