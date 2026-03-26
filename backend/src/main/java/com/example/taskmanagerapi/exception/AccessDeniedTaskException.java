package com.example.taskmanagerapi.exception;

/**
 * Thrown when a user tries to access or modify a task they do not own.
 */
public class AccessDeniedTaskException extends RuntimeException {

    /**
     * Constructs an AccessDeniedTaskException with the specified message.
     *
     * @param message the detail message
     */
    public AccessDeniedTaskException(String message) {
        super(message);
    }
}
