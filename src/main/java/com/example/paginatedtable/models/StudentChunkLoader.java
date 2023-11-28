package com.example.paginatedtable.models;

import com.example.paginatedtable.models.entities.Student;

import java.util.List;

public interface StudentChunkLoader {
    List<Student> loadStudentsInChunks(int slice, int chunkSize);
}
