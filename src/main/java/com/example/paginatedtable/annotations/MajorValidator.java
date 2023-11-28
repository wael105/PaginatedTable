package com.example.paginatedtable.annotations;

import com.example.paginatedtable.models.Major;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class MajorValidator implements ConstraintValidator<ValidMajor, String> {
    private Set<String> allowedValues;

    @Override
    public void initialize(ValidMajor constraint) {
        allowedValues = Arrays.stream(Major.values())
                .map(Enum::name)
                .map(major -> major.replace("_", " "))
                .map(major -> major.charAt(0) + major.substring(1).toLowerCase())
                .collect(Collectors.toSet());
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }
        return allowedValues.contains(value);
    }
}