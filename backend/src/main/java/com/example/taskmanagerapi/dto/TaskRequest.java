package com.example.taskmanagerapi.dto;

import com.example.taskmanagerapi.enums.TaskStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * DTO for creating or updating a task.
 * Contains only fields that come from client (request body).
 */
@Data
public class TaskRequest {

    /**
     * Short title of the task.
     * Required field.
     */
    @NotBlank(message = "Task title is required")
    @Size(min = 3, max = 100, message = "Task title must be between 3 and 100 characters")
    private String title;

    /**
     * Detailed description of the task.
     * Optional field.
     */
    @Size(max = 500, message = "Task description must not exceed 500 characters")
    private String description;

    /**
     * Current task status.
     * Stored as a string in the database.
     */

    /* */
    @Data
    public class TaskUpdateRequest {

        private String title;
        private String description;
        private TaskStatus status;
    }
}
