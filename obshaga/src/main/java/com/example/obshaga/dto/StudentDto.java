package com.example.obshaga.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
public class StudentDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String roomNumber;
    private String studentId;
    private boolean isPresent;
}

