package com.plutus.system.exception;

import lombok.Value;
import org.springframework.http.HttpStatus;

@Value
public class ErrorInfo {
    int status;
    String error;
    String message;

    public ErrorInfo(HttpStatus status, String message) {
        this.status = status.value();
        error = status.getReasonPhrase();
        this.message = message;
    }
}
