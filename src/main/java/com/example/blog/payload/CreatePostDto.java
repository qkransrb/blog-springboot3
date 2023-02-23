package com.example.blog.payload;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreatePostDto {

    private Long id;

    @NotBlank(message = "Post title is required")
    @Size(min = 2, message = "Post title must have at least 2 characters")
    private String title;

    @NotBlank(message = "Post description is required")
    @Size(min = 5, message = "Post description must have at least 5 characters")
    private String description;

    @NotBlank(message = "Post content is required")
    private String content;

    @NotNull(message = "Post category is required")
    private Long categoryId;
}
