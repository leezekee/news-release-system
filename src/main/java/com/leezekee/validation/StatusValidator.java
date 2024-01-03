package com.leezekee.validation;

import com.leezekee.anno.Status;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class StatusValidator implements ConstraintValidator<Status, Integer> {

    @Override
    public boolean isValid(Integer integer, ConstraintValidatorContext constraintValidatorContext) {
        if (integer == null) {
            return false;
        }
        return integer == 0 || integer == 1 || integer == -1 || integer == 2;
    }
}
