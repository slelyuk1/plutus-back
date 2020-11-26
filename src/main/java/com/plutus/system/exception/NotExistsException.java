package com.plutus.system.exception;

import java.math.BigInteger;

public class NotExistsException extends Exception {
    private static final String MESSAGE = "%s with id %d doesn't exist!";

    public NotExistsException(String entityName, BigInteger id) {
        this(entityName, id, null);
    }

    public NotExistsException(String entityName, BigInteger id, Throwable cause) {
        super(String.format(MESSAGE, entityName, id), cause);
    }
}
