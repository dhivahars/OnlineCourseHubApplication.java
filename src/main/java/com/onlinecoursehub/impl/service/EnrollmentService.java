package com.onlinecoursehub.impl.service;

import com.onlinecoursehub.impl.model.Course;
import com.onlinecoursehub.impl.model.Enrollment;
import com.onlinecoursehub.impl.model.Student;
import com.onlinecoursehub.impl.repository.CourseRepository;
import com.onlinecoursehub.impl.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class EnrollmentService {
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private CourseRepository courseRepository;


    public String enrollForCourse(Long studentId, Long courseId) {
        if(!studentRepository.existsById(studentId))
            return "Student doesn't exists in the database";
        if (!courseRepository.existsById(courseId))
            return "course doesn't available in our platform";

        if(studentRepository.findById(studentId).get().getEnrollments().contains(courseId) || courseRepository.findById(courseId).get().getEnrollments().contains(studentId))
            return "Student already registered for the course";

        if((courseRepository.findById(courseId).get().getCapacity()-courseRepository.findById(courseId).get().getEnrollments().size())>0)
        {
            Course course=courseRepository.findById(courseId).get();
            Student student=studentRepository.findById(studentId).get();
            Enrollment e=new Enrollment();
            e.setCourse(course);
            e.setStudent(student);
            student.getEnrollments().add(e);
            course.getEnrollments().add(e);
           studentRepository.save(student);
           courseRepository.save(course);
        }
        return "Course registration successfull........";

    }

}
