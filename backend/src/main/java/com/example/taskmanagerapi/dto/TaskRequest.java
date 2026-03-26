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
     * DTO for updating an existing task.
     * <p>
     * All fields are optional.
     * Only the provided fields should be updated.
     */
    @Data
    public class TaskUpdateRequest {

        /**
         * Updated task title.
         */
        private String title;

        /**
         * Updated task description.
         */
        private String description;

        /**
         * Updated task status.
         * Stored as a string in the database.
         */
        private TaskStatus status;
    }
}
