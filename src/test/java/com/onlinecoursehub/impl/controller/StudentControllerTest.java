package com.onlinecoursehub.impl.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.onlinecoursehub.impl.dto.StudentDto;
import com.onlinecoursehub.impl.model.Student;
import com.onlinecoursehub.impl.service.StudentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(StudentController.class)
class StudentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentService studentService;

    @Autowired
    private ObjectMapper objectMapper;

    private Student student;
    private StudentDto studentDto;

    @BeforeEach
    void setup() {
        student = new Student();
        student.setId(1);
        student.setName("Tony");
        student.setEmail("tony@gmail.com");

        studentDto = new StudentDto();
        studentDto.setName("Tony");
        studentDto.setEmail("tony@gmail.com");
    }

    @Test
    void testAddStudent() throws Exception {
        Mockito.when(studentService.addStudent(any(Student.class))).thenReturn(studentDto);

        mockMvc.perform(post("/student/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(student)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Tony"))
                .andExpect(jsonPath("$.email").value("tony@gmail.com"));
    }

    @Test
    void testStudentsList() throws Exception {
        Mockito.when(studentService.getStudentsList()).thenReturn(List.of(studentDto));

        mockMvc.perform(get("/student/list"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Tony"));
    }

    @Test
    void testGetStudentById() throws Exception {
        Mockito.when(studentService.getStudentById(anyLong())).thenReturn(Optional.of(student));
        Mockito.when(studentService.entityToDto(any(Student.class))).thenReturn(studentDto);

        mockMvc.perform(get("/student/search/id/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Tony"));
    }

    @Test
    void testGetStudentByName() throws Exception {
        Mockito.when(studentService.getStudentByName(anyString())).thenReturn(Optional.of(student));
        Mockito.when(studentService.entityToDto(any(Student.class))).thenReturn(studentDto);

        mockMvc.perform(get("/student/search/name/Tony"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("tony@gmail.com"));
    }

    @Test
    void testUpdateStudent() throws Exception {
        Mockito.when(studentService.updateStudent(anyLong(), any(Student.class)))
                .thenReturn("Student updated successfully");

        mockMvc.perform(patch("/student/update/id/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(student)))
                .andExpect(status().isOk())
                .andExpect(content().string("Student updated successfully"));
    }

    @Test
    void testDeleteStudentById() throws Exception {
        Mockito.when(studentService.deleteStudentById(anyLong())).thenReturn("Deleted successfully");

        mockMvc.perform(delete("/student/delete/id/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Deleted successfully"));
    }

}
