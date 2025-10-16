package com.onlinecoursehub.impl.controller;

import com.onlinecoursehub.impl.dto.StudentDto;
import com.onlinecoursehub.impl.model.Student;
import com.onlinecoursehub.impl.service.StudentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudentControllerTest {

    @Mock
    private StudentService studentService;

    @InjectMocks
    private StudentController studentController;

    private Student student;
    private StudentDto studentDto;

    @BeforeEach
    void setup() {
        student = new Student();
        student.setId(1L);
        student.setName("Tony");
        student.setEmail("tony@gmail.com");

        studentDto = new StudentDto();
        studentDto.setName("Tony");
    }

    @Test
    void testAddStudent() {
        when(studentService.addStudent(any(Student.class))).thenReturn(studentDto);

        ResponseEntity<StudentDto> response = studentController.addStudent(student);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Tony", response.getBody().getName());
        verify(studentService, times(1)).addStudent(any(Student.class));
    }

    @Test
    void testStudentsList() {
        when(studentService.getStudentsList()).thenReturn(List.of(studentDto));

        ResponseEntity<List<StudentDto>> response = studentController.studentsList();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals("Tony", response.getBody().get(0).getName());
        verify(studentService, times(1)).getStudentsList();
    }



    @Test
    void testUpdateStudent() {
        when(studentService.updateStudent(anyLong(), any(Student.class)))
                .thenReturn("Student updated successfully");

        ResponseEntity<String> response = studentController.updateStudent(1L, student);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Student updated successfully", response.getBody());
        verify(studentService, times(1)).updateStudent(1L, student);
    }

    @Test
    void testDeleteStudentById() {
        when(studentService.deleteStudentById(anyLong())).thenReturn("Deleted successfully");

        ResponseEntity<String> response = studentController.deleteStudentById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Deleted successfully", response.getBody());
        verify(studentService, times(1)).deleteStudentById(1L);
    }
}
