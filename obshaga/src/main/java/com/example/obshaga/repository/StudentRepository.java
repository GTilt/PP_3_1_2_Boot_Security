package com.example.obshaga.repository;

import com.example.obshaga.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    Boolean existsByStudentId(String studentId);
}
