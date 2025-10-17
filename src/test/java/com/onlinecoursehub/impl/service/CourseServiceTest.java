package com.onlinecoursehub.impl.service;

import com.onlinecoursehub.impl.dto.CourseDto;
import com.onlinecoursehub.impl.model.Course;
import com.onlinecoursehub.impl.model.Mentor;
import com.onlinecoursehub.impl.repository.CourseRepository;
import com.onlinecoursehub.impl.repository.MentorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CourseServiceTest {

    @Mock
    private CourseRepository courseRepository;
    @Mock
    private MentorRepository mentorRepository;

    @InjectMocks
    private CourseService courseService;

    private Course course;
    private Mentor mentor;

    @BeforeEach
    void setup() {
        mentor = Mentor.builder()
                .id(1L)
                .name("Leo")
                .email("leo@gmail.com")
                .build();

        course = Course.builder()
                .id(1L)
                .title("Java")
                .description("Java course")
                .capacity(10)
                .mentor(mentor)
                .enrollments(new ArrayList<>())
                .build();
    }


    @Test
    void testAddCourse_AlreadyExists() {
        when(courseRepository.existsByTitle(course.getTitle())).thenReturn(true);

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> courseService.addCourse(course));

        assertEquals("Course already exists with title: " + course.getTitle(), ex.getMessage());
        verify(courseRepository, never()).save(any());
    }




    @Test
    void testUpdateCourse_Success() {
        Course updatedCourse = Course.builder()
                .title("Advanced Java")
                .description("Updated Description")
                .build();

        when(courseRepository.findById(1L)).thenReturn(Optional.of(course));
        when(courseRepository.save(any(Course.class))).thenReturn(course);

        CourseDto result = courseService.updateCourse(1L, updatedCourse);

        assertEquals("Java", result.getTitle());
        verify(courseRepository, times(1)).save(any(Course.class));
    }

    @Test
    void testUpdateCourse_NotFound() {
        when(courseRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> courseService.updateCourse(1L, course));

        assertTrue(ex.getMessage().contains("Student not found"));
    }

    @Test
    void testDeleteCourseById_Success() {
        when(courseRepository.existsById(1L)).thenReturn(true);

        String result = courseService.deleteCourseById(1L);

        assertEquals("Course Deleted Successfully", result);
        verify(courseRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteCourseById_NotFound() {
        when(courseRepository.existsById(1L)).thenReturn(false);

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> courseService.deleteCourseById(1L));

        assertEquals("Course Not Found", ex.getMessage());
        verify(courseRepository, never()).deleteById(anyLong());
    }

    @Test
    void testCreateCourseWithMentor_Success() {
        when(mentorRepository.findById(1L)).thenReturn(Optional.of(mentor));
        when(courseRepository.save(any(Course.class))).thenReturn(course);

        Course result = courseService.createCourseWithMentor(course, 1L);

        assertEquals(course.getTitle(), result.getTitle());
        verify(courseRepository, times(1)).save(any(Course.class));
    }

}
