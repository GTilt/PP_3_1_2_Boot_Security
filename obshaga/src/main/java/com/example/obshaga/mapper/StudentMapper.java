package com.example.obshaga.mapper;

import com.example.obshaga.dto.StudentDto;
import com.example.obshaga.entity.Student;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface StudentMapper {
    StudentDto studentToStudentDTO(Student student);
    Student studentDTOToStudent(StudentDto studentDTO);
    List<StudentDto> studentsToStudentDTOs(List<Student> students);
}
