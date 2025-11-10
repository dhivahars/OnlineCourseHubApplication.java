package com.onlinecoursehub.impl.controller;

import com.onlinecoursehub.impl.dto.EnrollmentDto;
import com.onlinecoursehub.impl.service.EnrollmentService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
@ExtendWith(MockitoExtension.class)
class EnrollmentControllerTest {

    @Mock
    private EnrollmentService enrollmentService;

    @InjectMocks
    private EnrollmentController enrollmentController;

//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }

    @Test
    void testAddStudent() {
        when(enrollmentService.enrollForCourse(1L, 2L)).thenReturn("Student enrolled successfully");

        ResponseEntity<String> response = enrollmentController.addStudent(1L, 2L);

        assertEquals("Student enrolled successfully", response.getBody());
    }


    @Test
    void testUpdateProgressByEnrollmentId() {
        when(enrollmentService.updateProgressByEnrollmentId(anyLong(), anyDouble()))
                .thenReturn("Progress updated successfully");

        Object result = enrollmentController.updateProgressByEnrollmentId(1L, 80.0);

        assertEquals("Progress updated successfully", result);
    }

    @Test
    void testUnenrollByEnrollmentId() {
        when(enrollmentService.unenrollByEnrollmentId(1L, 2L)).thenReturn("Unenrolled successfully");

        Object result = enrollmentController.unenrollByEnrollmentId(1L, 2L);

        assertEquals("Unenrolled successfully", result);
    }


//    @Test
//    void testGetEnrollmentById() {
//        EnrollmentDto dto = EnrollmentDto.builder()
//                .studentName("Jane")
//                .courseTitle("Spring Boot")
//                .build();
//
//        when(enrollmentService.getEnrollmentById(1L)).thenReturn(dto);
//
//        EnrollmentDto result = enrollmentController.getEnrollmentById(1L);
//
//        assertEquals("Jane", result.getStudentName());
//        assertEquals("Spring Boot", result.getCourseTitle());
//    }
}
