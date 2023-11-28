package com.example.paginatedtable.controllers;

import com.example.paginatedtable.models.FrontEndStudent;
import com.example.paginatedtable.models.Major;
import com.example.paginatedtable.models.entities.Student;
import com.example.paginatedtable.services.StudentConverter;
import com.example.paginatedtable.services.StudentsService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController("/paginated-table")
@Slf4j
public class PaginatedTableController {

    private final StudentsService studentsService;

    private final StudentConverter studentConverter;

    @Autowired
    public PaginatedTableController(StudentsService studentsService, StudentConverter studentConverter) {
        this.studentsService = studentsService;
        this.studentConverter = studentConverter;
    }

    @PutMapping("/add-student")
    public ResponseEntity<FrontEndStudent> addStudent(@Valid @RequestBody FrontEndStudent requestBody) {
        log.info("Adding student endpoint hit");

        Student student = studentConverter.getStudent(requestBody);
        studentsService.addStudent(student);

        requestBody.setId(student.getId());
        return ResponseEntity.ok(requestBody);
    }

    @GetMapping("/load-all-students")
    public ResponseEntity<List<FrontEndStudent>> loadAllStudents() {
        log.info("Loading all students endpoint hit");
        return ResponseEntity.ok(studentConverter.getFrontEndStudents(studentsService.getAllStudents()));
    }

    @DeleteMapping("/delete-students")
    public ResponseEntity<String> deleteStudents(@RequestBody Map<String, List<Long>> body) {
        log.info("Deleting students endpoint hit");
        List<Long> ids = body.get("ids");
        studentsService.deleteStudentsById(ids);

        return ResponseEntity.ok("Students deleted");
    }

    @GetMapping("/export-all-students")
    public ResponseEntity<byte[]> exportAllStudentsToTsv() {
        log.info("Exporting all students endpoint hit");
        byte[] tsvContentBytes = studentsService.exportAllStudentsConcurrently().getBytes();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_PLAIN);
        headers.setContentDispositionFormData("attachment", "exported_data.tsv");

        return ResponseEntity.ok()
                .headers(headers)
                .body(tsvContentBytes);
    }

    @GetMapping("/get-all-majors")
    public ResponseEntity<List<String>> getMajors() {
        log.info("Getting majors");
        List<String> majors = Arrays.stream(Major.values())
                .map(Major::name)
                .map(major -> major.replace("_", " "))
                .map(major -> major.charAt(0) + major.substring(1).toLowerCase())
                .toList();
        return ResponseEntity.ok(majors);
    }

    @GetMapping("/get-headers")
    public ResponseEntity<List<String>> getHeaders() {
        log.info("Getting headers");
        List<String> headers = Arrays.asList("ID", "Name", "Major", "Phone Number", "GPA");
        return ResponseEntity.ok(headers);
    }
}
