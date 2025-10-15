package com.onlinecoursehub.impl.controller;

import com.onlinecoursehub.impl.dto.CourseDto;
import com.onlinecoursehub.impl.model.Course;
import com.onlinecoursehub.impl.service.CourseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CourseControllerTest {

    @Mock
    private CourseService courseService;

    @InjectMocks
    private CourseController courseController;

    private Course course;
    private CourseDto courseDto;

    @BeforeEach
    void setUp() {
        course = new Course();
        course.setId(1);
        course.setTitle("Java");
        course.setDescription("Core Java course");
        course.setCapacity(50);
        course.setSkill("Java Basics");

        courseDto = new CourseDto();
        courseDto.setId(1);
        courseDto.setTitle("Java");
        courseDto.setDescription("Core Java course");
        courseDto.setCapacity(50);
    }

    @Test
    void testCreateCourse() {
        when(courseService.addCourse(course)).thenReturn(courseDto);

        ResponseEntity<CourseDto> response = courseController.createCourse(course);

        assertEquals(201, response.getStatusCodeValue());
        assertEquals("Java", response.getBody().getTitle());
        verify(courseService, times(1)).addCourse(course);
    }

    @Test
    void testListCourses() {
        when(courseService.listCourse()).thenReturn(List.of(courseDto));

        ResponseEntity<List<CourseDto>> response = courseController.showCourse();

        assertEquals(302, response.getStatusCodeValue()); // HttpStatus.FOUND
        assertEquals(1, response.getBody().size());
        verify(courseService, times(1)).listCourse();
    }

    @Test
    void testShowById() {
        when(courseService.showById(1L)).thenReturn(Optional.of(courseDto));

        ResponseEntity<CourseDto> response = courseController.ShowById(1);

        assertEquals(302, response.getStatusCodeValue());
        assertEquals("Java", response.getBody().getTitle());
        verify(courseService, times(1)).showById(1);
    }

    @Test
    void testShowByName() {
        when(courseService.showByName("Java")).thenReturn(Optional.of(courseDto));

        ResponseEntity<CourseDto> response = courseController.ShowById("Java");

        assertEquals(302, response.getStatusCodeValue());
        assertEquals("Java", response.getBody().getTitle());
        verify(courseService, times(1)).showByName("Java");
    }

    @Test
    void testUpdateCourse() {
        when(courseService.updateCourse(1, course)).thenReturn("Updated Successfully");

        ResponseEntity<String> response = courseController.updateCourse(1, course);

        assertEquals("Updated Successfully", response.getBody());
        verify(courseService, times(1)).updateCourse(1, course);
    }

    @Test
    void testDeleteCourseById() {
        when(courseService.deleteCourseById(1)).thenReturn("Course Deleted");

        ResponseEntity<String> response = courseController.deleteCourseById(1);

        assertEquals("Course Deleted", response.getBody());
        verify(courseService, times(1)).deleteCourseById(1);
    }

    @Test
    void testDeleteCourseByTitle() {
        when(courseService.deleteCourseByTitle("Java")).thenReturn("Course Deleted by Title");

        ResponseEntity<String> response = courseController.deleteCourseByName("Java");

        assertEquals("Course Deleted by Title", response.getBody());
        verify(courseService, times(1)).deleteCourseByTitle("Java");
    }

    @Test
    void testGetCourseCapacityById() {
        when(courseService.getCourseCapacityById(1)).thenReturn("Capacity is 50");

        ResponseEntity<String> response = courseController.getCourseCapacityById(1);

        assertEquals("Capacity is 50", response.getBody());
        verify(courseService, times(1)).getCourseCapacityById(1);
    }

    @Test
    void testGetCourseCapacityByName() {
        when(courseService.getCourseCapacityByName("Java")).thenReturn("Capacity is 50");

        ResponseEntity<String> response = courseController.getCourseCapacityByName("Java");

        assertEquals("Capacity is 50", response.getBody());
        verify(courseService, times(1)).getCourseCapacityByName("Java");
    }

    @Test
    void testAssignMentor() {
        when(courseService.assignMentorToCourse(1L, 10L)).thenReturn(course);

        ResponseEntity<Course> response = courseController.assignMentor(1L, 10L);

        assertEquals("Java", response.getBody().getTitle());
        verify(courseService, times(1)).assignMentorToCourse(1L, 10L);
    }

    @Test
    void testCreateCourseWithMentor() {
        when(courseService.createCourseWithMentor(course, 10L)).thenReturn(course);

        ResponseEntity<Course> response = courseController.createCourseWithMentor(10L, course);

        assertEquals("Java", response.getBody().getTitle());
        verify(courseService, times(1)).createCourseWithMentor(course, 10L);
    }
}
