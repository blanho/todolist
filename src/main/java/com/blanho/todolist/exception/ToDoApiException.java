package com.blanho.todolist.exception;

import com.blanho.todolist.exception.main.NoStackTraceException;
import org.springframework.http.HttpStatus;

public class ToDoApiException extends NoStackTraceException {
    private final HttpStatus status;
    private final String message;

    public ToDoApiException(HttpStatus status, String message) {
        super(message);
        this.status = status;
        this.message = message;
    }

    public ToDoApiException(String message, HttpStatus status, String additionalMessage) {
        super(message + additionalMessage);
        this.status = status;
        this.message = additionalMessage;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
