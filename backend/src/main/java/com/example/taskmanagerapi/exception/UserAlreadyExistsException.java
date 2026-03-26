package com.example.taskmanagerapi.exception;

/**
 * Thrown when trying to register a user with an existing username.
 */
public class UserAlreadyExistsException extends RuntimeException {

    /**
     * Constructs a UserAlreadyExistsException with the specified message.
     *
     * @param message the detail message
     */
    public UserAlreadyExistsException(String message) {
        super(message);
    }
}