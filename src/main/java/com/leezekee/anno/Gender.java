package com.leezekee.anno;

import com.leezekee.validation.GenderValidator;
import jakarta.validation.Constraint;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = { GenderValidator.class })
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface Gender {
    String message() default "性别只能是男或女";
    Class<?>[] groups() default { };
    Class<?>[] payload() default { };
}
