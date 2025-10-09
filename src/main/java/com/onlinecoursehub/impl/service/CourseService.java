package com.onlinecoursehub.impl.service;

import com.onlinecoursehub.impl.dto.CourseDto;
import com.onlinecoursehub.impl.model.Course;
import com.onlinecoursehub.impl.model.Mentor;
import com.onlinecoursehub.impl.model.Enrollment;
import com.onlinecoursehub.impl.model.Student;
import com.onlinecoursehub.impl.repository.CourseRepository;
import com.onlinecoursehub.impl.repository.MentorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;


@Service
public class CourseService {
    @Autowired
    CourseRepository courseRepository;
    @Autowired
    MentorRepository mentorRepository;
    public CourseDto addCourse(Course course) {
        Set<Course> prerequisites = new HashSet<>();
        Course inputCourse = courseRepository.save(course);

        if (prerequisites != null && !prerequisites.isEmpty()) {
            Set<Course> addingPrequisites = prerequisites.stream()
                    .map(c -> courseRepository.findById(c.getId()).get())
                    .collect(Collectors.toSet());
            inputCourse.setPrerequisites(addingPrequisites);
        }

        return entityToDto(courseRepository.save(inputCourse));
    }


    public List<Course> listCourse() {
        return courseRepository.findAll();
    }

    public Optional<CourseDto> showById(long id) {
        return Optional.ofNullable(entityToDto(courseRepository.findById(id).get()));
    }

    public Optional<CourseDto> showByName(String name) {
        return Optional.ofNullable(entityToDto(Optional.ofNullable(courseRepository.findByTitle(name)).get()));
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
    public Course createCourseWithMentor(Course course, Long mentorId) {
        Mentor mentor = mentorRepository.findById(mentorId)
                .orElseThrow(() -> new RuntimeException("Mentor not found with ID: " + mentorId));

        course.setMentor(mentor);
        return courseRepository.save(course);
    }
    public Course assignMentorToCourse(Long courseId, Long mentorId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found with ID: " + courseId));

        Mentor mentor = mentorRepository.findById(mentorId)
                .orElseThrow(() -> new RuntimeException("Mentor not found with ID: " + mentorId));

        course.setMentor(mentor);
        return courseRepository.save(course);

    public CourseDto entityToDto(Course course){
        CourseDto courseDto=new CourseDto();
        courseDto.setTitle(course.getTitle());
        courseDto.setDescription(course.getDescription());
        courseDto.setCapacity(course.getCapacity());
        courseDto.setMentorName(mentorRepository.findById(course.getMentor().getId()).get());
        Set<String> prerequisitesName=courseDto.getPrerequisiteTitles();
        courseDto.setPrerequisiteTitles(course.getPrerequisites().stream().map(Course::getTitle).collect(Collectors.toSet()));
        return courseDto;
    }
}
