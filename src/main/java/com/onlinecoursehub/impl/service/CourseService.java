package com.onlinecoursehub.impl.service;

import com.onlinecoursehub.impl.model.Course;
import com.onlinecoursehub.impl.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseService {
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
        return Optional.ofNullable(Optional.ofNullable(courseRepository.findByName(name))
                .orElseThrow(() -> new RuntimeException("Course not found With Id:" + name)));
    }


}
