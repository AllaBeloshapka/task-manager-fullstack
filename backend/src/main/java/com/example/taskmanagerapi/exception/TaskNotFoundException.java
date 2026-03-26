package com.example.taskmanagerapi.exception;

/**
 * Thrown when a task is not found by its ID.
 */
public class TaskNotFoundException extends RuntimeException {

    /**
     * Constructs a TaskNotFoundException with the specified message.
     *
     * @param message the detail message
     */
    public TaskNotFoundException(String message) {
        super(message);
    }
}