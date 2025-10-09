package com.onlinecoursehub.impl.service;

import com.onlinecoursehub.impl.dto.EnrollmentDto;
import com.onlinecoursehub.impl.model.*;
import com.onlinecoursehub.impl.repository.BadgeRepository;
import com.onlinecoursehub.impl.repository.CourseRepository;
import com.onlinecoursehub.impl.repository.EnrollmentRepository;
import com.onlinecoursehub.impl.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.onlinecoursehub.impl.model.Status;

import java.util.Optional;


@Service
public class EnrollmentService {
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private BadgeRepository badgeRepository;
    @Autowired
    private EnrollmentRepository enrollmentRepository;


    public String enrollForCourse(Long studentId, Long courseId) {
        if (!studentRepository.existsById(studentId))
            return "Student doesn't exists in the database";
        if (!courseRepository.existsById(courseId))
            return "course doesn't available in our platform";

        if (studentRepository.findById(studentId).get().getEnrollments().contains(courseId) || courseRepository.findById(courseId).get().getEnrollments().contains(studentId))
            return "Student already registered for the course";

        if ((courseRepository.findById(courseId).get().getCapacity() - courseRepository.findById(courseId).get().getEnrollments().size()) > 0) {
            Course course = courseRepository.findById(courseId).get();
            Student student = studentRepository.findById(studentId).get();
            Enrollment e = new Enrollment();
            e.setStatus(Status.IN_PROGRESS);
            e.setCourse(course);
            e.setStudent(student);
            student.getEnrollments().add(e);
            course.getEnrollments().add(e);
            enrollmentRepository.save(e);
            studentRepository.save(student);
            courseRepository.save(course);
            Badge badge = Badge.builder().name(course.getTitle() + ":Begginer").student(student).build();
        }
        return "Course registration successfull........";
    }

    public EnrollmentDto updateProgressByEnrollmentId(long enrollmentId, double progressPercentage) {
        if (enrollmentRepository.existsById(enrollmentId)) {
            if (enrollmentRepository.findById(enrollmentId).isEmpty()) {
                return null;
            }
            if (enrollmentRepository.findById(enrollmentId).isEmpty())
                return null;

            Enrollment enrollment=enrollmentRepository.findById(enrollmentId).get();
            Student student=enrollment.getStudent();
            Course course=enrollment.getCourse();
            enrollment.setProgressPercentage(progressPercentage);
            if (progressPercentage==100){
                enrollment.setStatus(Status.COMPLETED);
                Badge badge = Badge.builder().name(course.getTitle() + ":Master").student(student).build();
                if (badgeRepository.existsByName(badge.getName())){
                    studentRepository.save(student);
                    courseRepository.save(course);
                    return entityToDto(enrollmentRepository.save(enrollment));
                }

                student.getBadges().add(badge);
                studentRepository.save(student);
                courseRepository.save(course);

                return entityToDto(enrollmentRepository.save(enrollment),badge);
            }
            else if (progressPercentage>=50){
                enrollment.setStatus(Status.HALF_WAY);
                Badge badge = Badge.builder().name(course.getTitle() + ":Intermmediate").student(student).build();
                if (badgeRepository.existsByName(badge.getName())){
                    studentRepository.save(student);
                    courseRepository.save(course);
                    return entityToDto(enrollment);
                }
                student.getBadges().add(badge);
                studentRepository.save(student);
                courseRepository.save(course);
                enrollmentRepository.save(enrollment);
                return entityToDto(enrollment,badge);
            }
        }
        return null;
    }


    public EnrollmentDto entityToDto(Enrollment enrollment) {
        return new EnrollmentDto()
                .builder()
                .courseTitle(enrollment.getCourse().getTitle())
                .studentName(enrollment.getStudent().getName())
                .status(enrollment.getStatus())
                .build();
    }

    public EnrollmentDto entityToDto(Enrollment enrollment,Badge badge) {
        return new EnrollmentDto(enrollment.getStudent().getName(), enrollment.getCourse().getTitle(), enrollment.getStatus(), enrollment.getProgressPercentage(),badge.getName());
    }

}
