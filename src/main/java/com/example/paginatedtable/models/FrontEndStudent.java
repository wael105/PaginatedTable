package com.example.paginatedtable.models;

import com.example.paginatedtable.annotations.ValidMajor;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class FrontEndStudent {

    private long id;

    @NotBlank(message = "Name is mandatory")
    @Pattern(regexp = "^[A-Za-z]+\\s[A-Za-z]+$", message = "Invalid name")
    private String name;

    @NotBlank(message = "Major is mandatory")
    @ValidMajor
    private String major;

    @NotBlank(message = "Phone number is mandatory")
    @Pattern(regexp = "\\d{2} \\d{4} \\d{4}", message = "Invalid phone number")
    private String phoneNumber;

    @DecimalMin(value = "0.0", inclusive = true, message = "GPA must be a positive number")
    @DecimalMax(value = "4.0", inclusive = true, message = "GPA must not exceed 4.0")
    private double gpa;
}