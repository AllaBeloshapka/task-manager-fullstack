package com.example.taskmanagerapi.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * User entity — represents application user.
 * Stores authentication data and owns tasks.
 */
@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Unique username used for login.
     */
    @Column(unique = true, nullable = false)
    private String username;

    /**
     * Hashed password (BCrypt).
     */
    @Column(nullable = false)
    private String password;

    /**
     * User role (e.g. ROLE_USER, ROLE_ADMIN).
     */
    @Column(nullable = false)
    private String role = "ROLE_USER";

    /**
     * Indicates whether the account is active.
     */
    @Column(nullable = false)
    private Boolean enabled = true;

    /**
     * One user can have multiple tasks.
     * Task.owner is the owning side of the relationship.
     */
    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Task> tasks = new ArrayList<>();

    /**
     * Timestamp of user creation.
     * Set automatically on persist.
     */
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}