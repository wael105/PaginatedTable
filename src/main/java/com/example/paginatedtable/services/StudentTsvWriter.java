package com.example.paginatedtable.services;

import com.example.paginatedtable.models.entities.Student;
import com.example.paginatedtable.models.Major;
import com.example.paginatedtable.models.StudentChunkLoader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.BlockingQueue;

@Service
@Slf4j
public class StudentTsvWriter {

    public String convertStudentsToTsv(List<Student> data, String oldTsv) {
        log.info("converting students to tsv");
        StringBuilder line = new StringBuilder(oldTsv);
        for (Student student : data) {
                long id = student.getId();
                String name = student.getName();
                String major = Major.getStringValueOfMajor(student.getMajor());
                String phoneNumber = student.getPhoneNumber();
                double gpa = student.getGpa();

                line.append(id).append("\t");
                line.append(name).append("\t");
                line.append(major).append("\t");
                line.append(phoneNumber).append("\t");
                line.append(gpa);

                line.append("\n");
        }
        return line.toString();
    }

    public void loadAndProduceStudents(BlockingQueue<List<Student>> studentQueue, StudentChunkLoader chunkLoader) {
        log.info("Student producer starting");
        int slice = 0;
        while (true) {
            List<Student> students = chunkLoader.loadStudentsInChunks(slice, 5);
            slice++;
            if (students.isEmpty()) {
                while(!studentQueue.offer(Collections.emptyList()));
                break;
            }

            try {
                studentQueue.put(students);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                log.error("Student producer interrupted");
            }
        }
        log.info("Student producer exiting");
    }

    public String consumeAndWriteStudents(BlockingQueue<List<Student>> studentQueue) {
        log.info("Student consumer starting");
        String tsv = "";
        try {
            while (true) {
                List<Student> students = studentQueue.take();
                if (students.isEmpty()) {
                    break;
                }

                tsv = convertStudentsToTsv(students, tsv); // Implement your writing logic.
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.error("Student consumer interrupted");
        }

        log.info("Student consumer exiting");
        return tsv;
    }
}