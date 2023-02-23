package com.example.blog.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(
        name = "categories",
        indexes = {
                @Index(columnList = "name", unique = true)
        },
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "category_name_unique_constraint",
                        columnNames = "name"
                )
        }
)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(exclude = { "posts" })
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Column(nullable = false, unique = true)
    private String name;

    @Setter
    private String description;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Post> posts = new ArrayList<>();

    public Category(String name, String description) {
        this.name = Objects.requireNonNull(name);
        this.description = description;
    }

    public static Category of(String name, String description) {
        return new Category(name, description);
    }

    public Category update(String name, String description) {
        this.name = Objects.requireNonNull(name);
        this.description = Objects.requireNonNull(description);
        return this;
    }
}
