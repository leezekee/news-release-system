package com.leezekee.anno;

import com.leezekee.validation.StatusValidator;
import jakarta.validation.Constraint;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = { StatusValidator.class })
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface Status {
    String message() default "审核状态只能是-2、-1、0、1";
    Class<?>[] groups() default { };
    Class<?>[] payload() default { };
}
