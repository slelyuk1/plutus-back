package com.plutus.system.validation.validator;

import com.plutus.system.validation.annotation.Email;
import org.apache.commons.validator.routines.EmailValidator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EmailConstraintValidator implements ConstraintValidator<Email, String> {

    private final EmailValidator validator = EmailValidator.getInstance();

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        return validator.isValid(email);
    }
}
