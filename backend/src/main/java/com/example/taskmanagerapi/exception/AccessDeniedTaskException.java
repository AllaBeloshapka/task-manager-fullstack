package com.example.taskmanagerapi.exception;

/**
 * Thrown when a user tries to access or modify a task they do not own.
 */
public class AccessDeniedTaskException extends RuntimeException {

    public AccessDeniedTaskException(String message) {
        super(message);
    }
}
