package com.plutus.system.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;

@ControllerAdvice
public class CustomRestControllerExceptionHandler extends ResponseEntityExceptionHandler {
    private static final String FIELD_VALIDATION_MESSAGE_TEMPLATE = "Field '%s' %s";

    @ExceptionHandler(TransactionSystemException.class)
    public ResponseEntity<ErrorInfo> handleTransactionException(TransactionSystemException e) {
        Throwable maybeConstraintViolation = e.getOriginalException();
        ErrorInfo errorInfo;
        if (maybeConstraintViolation != null && maybeConstraintViolation.getCause() instanceof ConstraintViolationException) {
            ConstraintViolationException constraintViolationException = (ConstraintViolationException) maybeConstraintViolation.getCause();
            String message = constraintViolationException.getConstraintViolations().stream()
                    .map(violation -> String.format(FIELD_VALIDATION_MESSAGE_TEMPLATE, violation.getPropertyPath().toString(), violation.getMessage()))
                    .findAny()
                    .orElse("Unknown constraint violation!");
            errorInfo = new ErrorInfo(HttpStatus.BAD_REQUEST, message);
        } else {
            errorInfo = new ErrorInfo(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
        return ResponseEntity.status(errorInfo.getStatus()).body(errorInfo);
    }

    @Override
    @NonNull
    protected ResponseEntity<Object> handleMethodArgumentNotValid(@NonNull MethodArgumentNotValidException e,
                                                                  @NonNull HttpHeaders headers,
                                                                  @NonNull HttpStatus status,
                                                                  @NonNull WebRequest request) {
        return e.getBindingResult().getFieldErrors().stream().findAny()
                .map(fieldError -> String.format(FIELD_VALIDATION_MESSAGE_TEMPLATE, fieldError.getField(), fieldError.getDefaultMessage()))
                .map(message -> handleExceptionInternal(e, new ErrorInfo(status, message), headers, status, request))
                .orElseGet(() -> super.handleMethodArgumentNotValid(e, headers, status, request));
    }
}
