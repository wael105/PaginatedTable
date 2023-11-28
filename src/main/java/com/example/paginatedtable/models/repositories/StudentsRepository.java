package com.example.paginatedtable.models.repositories;

import com.example.paginatedtable.models.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentsRepository extends JpaRepository<Student, Long> {

}
