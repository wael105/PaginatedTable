package com.example.paginatedtable.annotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Constraint(validatedBy = MajorValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidMajor {
    String message() default "Invalid major";
    // a way to group constraints and apply them on the annotated element
    Class<?>[] groups() default {};
    // a payload to pass metadata between validation and constraint
    Class<? extends Payload>[] payload() default {};
}