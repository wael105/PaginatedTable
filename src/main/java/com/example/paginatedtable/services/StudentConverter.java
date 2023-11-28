package com.example.paginatedtable.services;

import com.example.paginatedtable.models.FrontEndStudent;
import com.example.paginatedtable.models.Major;
import com.example.paginatedtable.models.entities.Student;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class StudentConverter {

    public Student getStudent(FrontEndStudent frontEndStudent) {
        log.info("converting front end student to student");
        Student student = new Student();
        student.setName(frontEndStudent.getName());
        student.setMajor(Major.valueOf(frontEndStudent.getMajor().replace(" ", "_").toUpperCase()).getValue());
        student.setPhoneNumber(frontEndStudent.getPhoneNumber());
        student.setGpa(frontEndStudent.getGpa());
        return student;
    }

    public List<FrontEndStudent> getFrontEndStudents(List<Student> students) {
        // wrote multiple to differentiate between this and the singular version
        log.info("converting multiple students to front end students");
        List<FrontEndStudent> frontEndStudents = new ArrayList<>();
        for(Student student : students) {
            FrontEndStudent frontEndStudent = new FrontEndStudent();
            frontEndStudent.setId(student.getId());
            frontEndStudent.setName(student.getName());
            // if this produces an exception, then the major exists in the database but not in the enum and that means it was added by hand in the database
            frontEndStudent.setMajor(Major.getStringValueOfMajor(student.getMajor()));
            frontEndStudent.setPhoneNumber(student.getPhoneNumber());
            frontEndStudent.setGpa(student.getGpa());
            frontEndStudents.add(frontEndStudent);
        }
        return frontEndStudents;
    }
}
