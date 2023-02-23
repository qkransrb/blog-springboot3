package com.example.blog.entity;

import com.example.blog.payload.PostDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(
        name = "posts",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "title_unique_constraint",
                        columnNames = "title"
                )
        }
)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(exclude = { "comments" })
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    private Category category;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Comment> comments = new ArrayList<>();

    // constructor
    public Post(String title, String description, String content, Category category) {
        this.title = Objects.requireNonNull(title, "Post title is null");
        this.description = Objects.requireNonNull(description, "Post description is null");
        this.content = Objects.requireNonNull(content, "Post content is null");
        this.category = Objects.requireNonNull(category, "Post category is null");
    }

    // generate static method
    public static Post of(String title, String description, String content, Category category) {
        return new Post(title, description, content, category);
    }

    public Post update(PostDto postDto) {
        this.title = Objects.requireNonNull(postDto.getTitle(), "Post title is null");
        this.description = Objects.requireNonNull(postDto.getDescription(), "Post description is null");
        this.content = Objects.requireNonNull(postDto.getContent(), "Post content is null");
        return this;
    }
}
