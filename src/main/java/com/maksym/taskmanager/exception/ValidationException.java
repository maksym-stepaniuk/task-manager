package com.maksym.taskmanager.exception;

public class ValidationException extends RuntimeException {
    public ValidationException (String message) {
        super(message);
    }
}
