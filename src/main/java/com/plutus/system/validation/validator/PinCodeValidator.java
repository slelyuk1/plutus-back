package com.plutus.system.validation.validator;

import com.plutus.system.validation.annotation.PinCode;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PinCodeValidator implements ConstraintValidator<PinCode, String> {


    @Override
    public boolean isValid(String pinCode, ConstraintValidatorContext context) {
        if (pinCode != null) {
            if (pinCode.length() == 4) {
                for (int i = 0; i < pinCode.length(); ++i) {
                    if (!Character.isDigit(pinCode.charAt(i))) {
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }
}
