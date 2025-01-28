package com.example.obshaga.service;

import com.example.obshaga.dto.StudentDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface StudentService {
    StudentDto getStudentById(long id);
    List<StudentDto> getAllStudents();
    StudentDto addStudent(StudentDto studentDto);
}
