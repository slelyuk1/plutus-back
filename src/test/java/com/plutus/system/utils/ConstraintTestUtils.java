package com.plutus.system.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.validation.ConstraintViolationException;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ConstraintTestUtils {

    // TODO: 10/6/2020 Check if this can be better
    public static boolean hasSpecifiedConstraintViolation(ConstraintViolationException e, String constraintPath) {
        return e.getConstraintViolations().stream()
                .anyMatch(violation -> violation.getPropertyPath().toString().equals(constraintPath));
    }
}
