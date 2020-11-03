package com.plutus.system.validation.annotation;

import com.plutus.system.validation.validator.PinCodeValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PinCodeValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface PinCode {
    String message() default "Invalid PIN format";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
