//package com.onlinecoursehub.impl.service;
//
//import com.onlinecoursehub.impl.model.*;
//import com.onlinecoursehub.impl.repository.*;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.util.*;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//class EnrollmentServiceTest {
//
//    @Mock
//    private StudentRepository studentRepository;
//    @Mock
//    private CourseRepository courseRepository;
//    @Mock
//    private EnrollmentRepository enrollmentRepository;
//    @Mock
//    private BadgeRepository badgeRepository;
//
//    @InjectMocks
//    private EnrollmentService enrollmentService;
//
//    private Student student;
//    private Course course;
//    private Enrollment enrollment;
//
//    @BeforeEach
//    void setup() {
//        student = new Student();
//        student.setId(1L);
//        student.setName("John");
//        student.setEmail("john@example.com");
//        student.setSkills(new HashSet<>(List.of("Java Basics")));
//        student.setEnrollments(new ArrayList<>());
//        student.setBadges(new ArrayList<>());
//
//        course = new Course();
//        course.setId(1L);
//        course.setTitle("Java Basics");
//        course.setSkill("Advanced Java");
//        course.setCapacity(10);
//        course.setPrerequisites(new HashSet<>());
//        course.setEnrollments(new ArrayList<>());
//
//        enrollment = new Enrollment();
//        enrollment.setId(1L);
//        enrollment.setStudent(student);
//        enrollment.setCourse(course);
//        enrollment.setStatus(Status.IN_PROGRESS);
//    }
//
//    @Test
//    void testEnrollForCourse_Success() {
//        when(studentRepository.existsById(1L)).thenReturn(true);
//        when(courseRepository.existsById(1L)).thenReturn(true);
//        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));
//        when(courseRepository.findById(1L)).thenReturn(Optional.of(course));
//        when(enrollmentRepository.save(any(Enrollment.class))).thenReturn(enrollment);
//
//        String result = enrollmentService.enrollForCourse(1L, 1L);
//
//        assertTrue(result.toLowerCase().contains("success"));
//        verify(enrollmentRepository, times(1)).save(any(Enrollment.class));
//    }
//
//    @Test
//    void testEnrollForCourse_StudentNotFound() {
//        when(studentRepository.existsById(1L)).thenReturn(false);
//
//        RuntimeException ex = assertThrows(RuntimeException.class,
//                () -> enrollmentService.enrollForCourse(1L, 1L));
//
//        assertEquals("Student doesn't exists in the database", ex.getMessage());
//        verify(enrollmentRepository, never()).save(any());
//    }
//
//    @Test
//    void testEnrollForCourse_CourseNotFound() {
//        when(studentRepository.existsById(1L)).thenReturn(true);
//        when(courseRepository.existsById(1L)).thenReturn(false);
//
//        RuntimeException ex = assertThrows(RuntimeException.class,
//                () -> enrollmentService.enrollForCourse(1L, 1L));
//
//        assertEquals("course doesn't available in our platform", ex.getMessage());
//    }
//
//    @Test
//    void testUpdateProgress_SuccessHalfway() {
//        enrollment.setProgressPercentage(20);
//        when(enrollmentRepository.existsById(1L)).thenReturn(true);
//        when(enrollmentRepository.findById(1L)).thenReturn(Optional.of(enrollment));
//        when(enrollmentRepository.save(any())).thenReturn(enrollment);
//        when(badgeRepository.existsByName(anyString())).thenReturn(false);
//
//        Object result = enrollmentService.updateProgressByEnrollmentId(1L, 60.0);
//
//        assertTrue(result.toString().contains("Student Progress Updated"));
//        verify(enrollmentRepository, times(1)).save(any());
//    }
//
//    @Test
//    void testUpdateProgress_DecreaseError() {
//        enrollment.setProgressPercentage(80);
//        when(enrollmentRepository.existsById(1L)).thenReturn(true);
//        when(enrollmentRepository.findById(1L)).thenReturn(Optional.of(enrollment));
//
//        RuntimeException ex = assertThrows(RuntimeException.class,
//                () -> enrollmentService.updateProgressByEnrollmentId(1L, 50.0));
//
//        assertEquals("Error...............", ex.getMessage());
//    }
//
//    @Test
//    void testUpdateProgress_AlreadyCompleted() {
//        enrollment.setStatus(Status.COMPLETED);
//        when(enrollmentRepository.existsById(1L)).thenReturn(true);
//        when(enrollmentRepository.findById(1L)).thenReturn(Optional.of(enrollment));
//
//        RuntimeException ex = assertThrows(RuntimeException.class,
//                () -> enrollmentService.updateProgressByEnrollmentId(1L, 100.0));
//
//        assertEquals("Cannot update completed enrollment", ex.getMessage());
//    }
//
//    @Test
//    void testUnenroll_Success() {
//        when(enrollmentRepository.existsByEnrolmentIdAndCourseId(1L, 1L)).thenReturn(true);
//        when(enrollmentRepository.findById(1L)).thenReturn(Optional.of(enrollment));
//
//        Object result = enrollmentService.unenrollByEnrollmentId(1L, 1L);
//
//        assertTrue(result.toString().contains("Enrollement deleted successfully"));
//        verify(enrollmentRepository, times(1)).delete(enrollment);
//    }
//
//    @Test
//    void testUnenroll_NotFound() {
//        when(enrollmentRepository.existsByEnrolmentIdAndCourseId(1L, 1L)).thenReturn(false);
//
//        RuntimeException ex = assertThrows(RuntimeException.class,
//                () -> enrollmentService.unenrollByEnrollmentId(1L, 1L));
//
//        assertEquals("Enrollments not found", ex.getMessage());
//        verify(enrollmentRepository, never()).delete(any());
//    }
//}
