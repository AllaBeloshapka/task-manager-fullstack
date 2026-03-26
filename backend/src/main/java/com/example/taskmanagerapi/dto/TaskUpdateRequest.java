package com.example.taskmanagerapi.dto;

import com.example.taskmanagerapi.enums.TaskStatus;
import lombok.Data;

/**
 * DTO for updating an existing task.
 *
 * Represents partial update payload.
 * Any field can be null - only provided fields should be updated.
 */
@Data
public class TaskUpdateRequest {

    /**
     * New title of the task.
     */
    private String title;

    /**
     * New description of the task.
     */
    private String description;

    /**
     * New status of the task.
     *
     * Note:
     * - Enum is used to ensure only valid statuses are allowed
     * - Stored as a string in the database
     */
    private TaskStatus status;
}