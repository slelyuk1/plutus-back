package com.plutus.system.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class InsufficientBalanceException extends RuntimeException {
    private static final String MESSAGE = "Insufficient balance!";

    public InsufficientBalanceException() {
        super(MESSAGE);
    }
}
