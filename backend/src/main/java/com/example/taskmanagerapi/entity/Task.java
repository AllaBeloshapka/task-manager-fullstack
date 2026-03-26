package com.example.taskmanagerapi.entity;

import com.example.taskmanagerapi.enums.TaskStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Task entity — represents a user's task.
 * Each task belongs to a user and has a status.
 */
@Entity
@Table(name = "tasks")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Task title (required).
     */
    @Column(nullable = false)
    private String title;

    /**
     * Optional detailed description (max 1000 chars).
     */
    @Column(length = 1000)
    private String description;

    /**
     * Current task status.
     * Stored as a string in the database.
     */
    @Enumerated(EnumType.STRING)
    private TaskStatus status = TaskStatus.TODO;

    /**
     * Task owner.
     * Many tasks can belong to one user.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;

    /**
     * Timestamp of task creation.
     * Set automatically on persist.
     */
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    /**
     * Lifecycle callback - sets createdAt on insert.
     */
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
