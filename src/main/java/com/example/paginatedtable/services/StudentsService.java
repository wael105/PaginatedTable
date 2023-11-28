package com.example.paginatedtable.services;

import com.example.paginatedtable.models.entities.Student;
import com.example.paginatedtable.models.StudentChunkLoader;
import com.example.paginatedtable.models.repositories.StudentsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.*;

@Service
@Slf4j
public class StudentsService implements StudentChunkLoader {

    private final StudentsRepository studentsRepository;

    private final StudentTsvWriter studentTsvWriter;

    @Autowired
    public StudentsService(StudentsRepository studentsRepository, StudentTsvWriter studentTsvWriter) {
        this.studentsRepository = studentsRepository;
        this.studentTsvWriter = studentTsvWriter;
    }

    public void addStudent(Student student) {
        log.info("Adding student: " + student.getId());
        studentsRepository.save(student);
    }

    public List<Student> getAllStudents() {
        log.info("Getting all students");
        return studentsRepository.findAll();
    }

    public void deleteStudentsById(List<Long> id) {
        log.info("Deleting students with ids: " + id.toString());
        studentsRepository.deleteAllById(id);
    }

    public String exportAllStudentsConcurrently() {
        log.info("Exporting all students concurrently");
        BlockingQueue<List<Student>> studentQueue = new ArrayBlockingQueue<>(100);
        ExecutorService executorService = Executors.newFixedThreadPool(2);

        executorService.submit(() -> studentTsvWriter.loadAndProduceStudents(studentQueue, this));
        Future<String> future = executorService.submit(() -> studentTsvWriter.consumeAndWriteStudents(studentQueue));

        executorService.shutdown();


        String tsv = "";

        try {
            tsv = future.get();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.error("Thread was interrupted");
        } catch (ExecutionException e) {
            log.error("An error occurred: " + e.getMessage());
        }

        log.info("Finished exporting all students concurrently");
        return tsv;
    }

    public List<Student> loadStudentsInChunks(int slice, int chunkSize) {
        log.info("Loading " + chunkSize + " students");
        Slice<Student> studentSlice;

        PageRequest pageRequest = PageRequest.of(slice, chunkSize);
        studentSlice = studentsRepository.findAll(pageRequest);

        return studentSlice.getContent();
    }
}
