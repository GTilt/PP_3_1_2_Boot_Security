package com.example.obshaga.service.impl;

import com.example.obshaga.dto.StudentDto;
import com.example.obshaga.entity.Student;
import com.example.obshaga.mapper.StudentMapper;
import com.example.obshaga.repository.StudentRepository;
import com.example.obshaga.service.StudentService;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;

    @Override
    public StudentDto getStudentById(long id) {
        return studentMapper.studentToStudentDTO(studentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Student not found")));
    }

    @Override
    public List<StudentDto> getAllStudents() {
        return studentMapper.studentsToStudentDTOs(studentRepository.findAll());
    }

    @Override
    public StudentDto addStudent(StudentDto studentDto) {
        Boolean existingStudent = studentRepository.existsByStudentId(studentDto.getStudentId());
        if (existingStudent) {
            throw new EntityExistsException("Student already exists");
        }
        Student newStudent = studentMapper.studentDTOToStudent(studentDto);
        return studentMapper.studentToStudentDTO(studentRepository.save(newStudent));
    }
}
