package com.example.blog.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(
        name = "users",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "user_username_unique_constraint",
                        columnNames = "username"
                ),
                @UniqueConstraint(
                        name = "user_email_unique_constraint",
                        columnNames = "email"
                )
        }
)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id")
    )
    private List<Role> roles = new ArrayList<>();

    public User(String username, String password, String name, String email, List<Role> roles) {
        this.username = Objects.requireNonNull(username, "User username is null");
        this.password = Objects.requireNonNull(password, "User password is null");
        this.name = Objects.requireNonNull(name, "User name is null");
        this.email = Objects.requireNonNull(email, "User email is null");
        this.roles.addAll(Objects.requireNonNull(roles, "User roles is null"));
    }

    public static User of(String username, String password, String name, String email, List<Role> roles) {
        return new User(username, password, name, email, roles);
    }
}
