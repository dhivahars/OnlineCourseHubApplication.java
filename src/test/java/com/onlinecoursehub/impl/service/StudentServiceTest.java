package com.onlinecoursehub.impl.service;

import com.onlinecoursehub.impl.dto.StudentDto;
import com.onlinecoursehub.impl.model.Student;
import com.onlinecoursehub.impl.repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class StudentServiceTest {

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentService studentService;

    private Student student;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        student = new Student();
        student.setId(1L);
        student.setName("John");
        student.setEmail("john@gmail.com");
    }


    @Test
    void testAddStudent_Success() {
        when(studentRepository.existsByEmail(student.getEmail())).thenReturn(false);
        when(studentRepository.save(any(Student.class))).thenReturn(student);

        StudentDto dto = studentService.addStudent(student);

        assertEquals("John", dto.getName());
        assertEquals("john@gmail.com", dto.getEmail());
    }

    @Test
    void testAddStudent_EmailExists_ThrowsException() {
        when(studentRepository.existsByEmail(student.getEmail())).thenReturn(true);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            studentService.addStudent(student);
        });

        assertEquals("Mail already exists", exception.getMessage());
    }

    @Test
    void testGetStudentsList() {
        when(studentRepository.findAll()).thenReturn(List.of(student));

        List<StudentDto> result = studentService.getStudentsList();

        assertEquals(1, result.size());
        assertEquals("John", result.get(0).getName());
    }

    @Test
    void testGetStudentById_Found() {
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));

        Optional<Student> result = studentService.getStudentById(1L);

        assertTrue(result.isPresent());
        assertEquals("John", result.get().getName());
    }

    @Test
    void testGetStudentById_NotFound() {
        when(studentRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            studentService.getStudentById(1L);
        });

        assertEquals("Student not found With Id:1", ex.getMessage());
    }

    @Test
    void testGetStudentByName_Found() {
        when(studentRepository.findByName("John")).thenReturn(student);

        Optional<Student> result = studentService.getStudentByName("John");

        assertTrue(result.isPresent());
        assertEquals("John", result.get().getName());
    }

    @Test
    void testGetStudentByName_NotFound() {
        when(studentRepository.findByName("John")).thenReturn(null);

        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            studentService.getStudentByName("John");
        });

        assertTrue(ex.getMessage().contains("doesn't exist"));
    }

    @Test
    void testUpdateStudent_Success() {
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));
        when(studentRepository.save(any(Student.class))).thenReturn(student);

        Student update = new Student();
        update.setName("Updated John");
        update.setEmail("updated@gmail.com");
        update.setSkills(Set.of("Java", "Spring"));

        String result = studentService.updateStudent(1L, update);

        assertEquals("Student updated successfully", result);
        verify(studentRepository, times(1)).save(any(Student.class));
    }

    @Test
    void testUpdateStudent_NotFound() {
        when(studentRepository.findById(1L)).thenReturn(Optional.empty());

        Student update = new Student();

        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            studentService.updateStudent(1L, update);
        });

        assertTrue(ex.getMessage().contains("Student not found"));
    }

    @Test
    void testDeleteStudentById_Success() {
        when(studentRepository.existsById(1L)).thenReturn(true);

        String result = studentService.deleteStudentById(1L);

        assertEquals("Student Deleted Successfully", result);
        verify(studentRepository).deleteById(1L);
    }

    @Test
    void testDeleteStudentById_NotFound() {
        when(studentRepository.existsById(1L)).thenReturn(false);

        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            studentService.deleteStudentById(1L);
        });

        assertEquals("Student Not Found", ex.getMessage());
    }

    // 7️⃣ Delete Student by Name
    @Test
    void testDeleteStudentByName_Success() {
        when(studentRepository.existsByName("John")).thenReturn(true);

        String result = studentService.deleteStudentByName("John");

        assertEquals("Student Deleted Successfully", result);
        verify(studentRepository).deleteByName("John");
    }

    @Test
    void testDeleteStudentByName_NotFound() {
        when(studentRepository.existsByName("John")).thenReturn(false);

        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            studentService.deleteStudentByName("John");
        });

        assertEquals("Student Not Found", ex.getMessage());
    }
}
