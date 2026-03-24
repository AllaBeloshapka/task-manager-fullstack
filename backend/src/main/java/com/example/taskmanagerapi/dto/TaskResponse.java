package com.example.taskmanagerapi.dto;

import com.example.taskmanagerapi.enums.TaskStatus;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * DTO for sending task data to the client.
 * Used in responses (what frontend receives).
 */
@Data
public class TaskResponse {

    /**
     * Unique identifier of the task.
     */
    private Long id;

    /**
     * Short title of the task.
     */
    private String title;

    /**
     * Detailed description of the task.
     */
    private String description;

    /**
     * Current status of the task.
     */
    private TaskStatus status;

    /**
     * Timestamp when the task was created.
     */
    private LocalDateTime createdAt;
}
