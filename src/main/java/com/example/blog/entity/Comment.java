package com.example.blog.entity;

import lombok.*;

import jakarta.persistence.*;
import java.util.Objects;

@Entity
@Table(
        name = "comments",
        indexes = {
                @Index(columnList = "name")
        }
)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String body;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", referencedColumnName = "id", nullable = false)
    private Post post;

    // constructor
    public Comment(String name, String email, String body, Post post) {
        this.name = Objects.requireNonNull(name, "Comment name is null");
        this.email = Objects.requireNonNull(email, "Comment email is null");
        this.body = Objects.requireNonNull(body, "Comment body is null");
        this.post = Objects.requireNonNull(post, "Comment post is null");
    }

    // generate static method
    public static Comment of(String name, String email, String body, Post post) {
        return new Comment(name, email, body, post);
    }

    public Comment update(String name, String email, String body) {
        this.name = Objects.requireNonNull(name, "Comment name is null");;
        this.email = Objects.requireNonNull(email, "Comment email is null");;
        this.body = Objects.requireNonNull(body, "Comment body is null");
        return this;
    }
}
