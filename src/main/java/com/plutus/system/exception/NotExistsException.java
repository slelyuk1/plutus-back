package com.plutus.system.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotExistsException extends RuntimeException {
    private static final String MESSAGE = "%s with parameter %s doesn't exist!";

    public NotExistsException(String entityName, Object parameter) {
        this(entityName, parameter, null);
    }

    public NotExistsException(String entityName, Object parameter, Throwable cause) {
        super(String.format(MESSAGE, entityName, parameter), cause);
    }
}
