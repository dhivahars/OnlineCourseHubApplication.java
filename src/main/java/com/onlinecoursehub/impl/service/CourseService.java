package com.onlinecoursehub.impl.service;

import com.onlinecoursehub.impl.model.Course;
import com.onlinecoursehub.impl.model.Student;
import com.onlinecoursehub.impl.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class CourseService {
    @Autowired
    CourseRepository courseRepository;
    public Course addCourse(Course course) {
        return  courseRepository.save(course);
    }

    public List<Course> listCourse() {
        return courseRepository.findAll();
    }

    public Optional<Course> showById(long id) {
        return Optional.ofNullable(courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found With Id:" + id)));
    }

    public Optional<Course> showByName(String name) {
        return Optional.ofNullable(Optional.ofNullable(courseRepository.findByTitle(name))
                .orElseThrow(() -> new RuntimeException("Course not found With Id:" + name)));
    }

    public String updateCourse(long id, Course c) {
        Course existing = courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found with id " + c.getId()));
        if(c.getTitle() != null)
            existing.setTitle(c.getTitle());
        if(c.getDescription() != null)
            existing.setDescription(c.getDescription());

        courseRepository.save(existing);
        return "Course updated successfully";
    }


    public String deleteCourseById(long id) {
        if(courseRepository.existsById(id)){
            courseRepository.deleteById(id);
            return "Course Deleted Successfully";}

        return "Course Not Found";
    }

    public String deleteCourseByTitle(String name) {
        if (courseRepository.findByTitle(name).getTitle().equalsIgnoreCase(name)) {
            courseRepository.deleteByTitle(name);
            return "Course Deleted Successfully";
        }
        return "Course Not Found";
    }
    public String getCourseCapacityById(long courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found with Id: " + courseId));
        int availableSeats = course.getCapacity() - course.getEnrollments().size();
        return "Total Seats: " + course.getCapacity() +
                ", Enrolled: " + course.getEnrollments().size() +
                ", Available: " + availableSeats;
    }

    public String getCourseCapacityByName(String name) {
        Course course = courseRepository.findByTitle(name);
        if (course == null) {
            throw new RuntimeException("Course not found with name: " + name);
        }
        return "Total Seats: " + course.getCapacity() +
                ", Enrolled Students: " + course.getEnrollments().size() +
                ", Available Seats: " + (course.getCapacity() - course.getEnrollments().size());
    }
}
