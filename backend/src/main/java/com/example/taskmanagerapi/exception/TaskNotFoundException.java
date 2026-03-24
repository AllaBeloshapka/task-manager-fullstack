package com.example.taskmanagerapi.exception;

/**
 * Thrown when a task is not found by its ID.
 */
public class TaskNotFoundException extends RuntimeException {

    public TaskNotFoundException(String message) {
        super(message);
    }
}