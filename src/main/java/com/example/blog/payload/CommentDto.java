package com.example.blog.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {

    private Long id;

    @NotBlank(message = "Comment name is required")
    private String name;

    @NotBlank(message = "Comment email is required")
    @Email(message = "Email must be valid address format")
    private String email;

    @NotBlank(message = "Comment body is required")
    @Size(min = 5, message = "Comment body must have at least 5 characters")
    private String body;
}
