package com.blanho.todolist.dto.exception;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class ErrorDetail {
    private Date timestamp;
    private String message;
    private String detail;
    private Map<String, String> errors;

    private ErrorDetail(
            final Date timestamp,
            final String message,
            final String detail,
            final Map<String, String> errors
    ) {
        this.timestamp = timestamp;
        this.message = message;
        this.detail = detail;
        this.errors = errors;
    }

    public ErrorDetail(
            final Date timestamp,
            final String message,
            final String detail
    ) {
        this.timestamp = timestamp;
        this.message = message;
        this.detail = detail;
        this.errors = new HashMap<>();
    }

    public static ErrorDetail with(
            final String message,
            final String detail,
            final Map<String, String> errors) {
        return new ErrorDetail(new Date(), message, detail, errors);
    }

    public static ErrorDetail with(
            final String message,
            final String detail
    ) {
        return new ErrorDetail(new Date(), message, detail, new HashMap<>());
    }
}
