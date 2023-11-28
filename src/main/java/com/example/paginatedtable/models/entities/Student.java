package com.example.paginatedtable.models.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "students")
@Data
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "student_name", nullable = false)
    private String name;

    @Column(name = "student_major", nullable = false)
    private int major;

    @Column(name = "student_phone_number", nullable = false)
    private String phoneNumber;

    @Column(name = "student_gpa", nullable = false)
    private double gpa;
}
