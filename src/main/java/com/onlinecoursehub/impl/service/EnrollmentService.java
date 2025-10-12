package com.onlinecoursehub.impl.service;

import com.onlinecoursehub.impl.dto.EnrollmentDto;
import com.onlinecoursehub.impl.model.*;
import com.onlinecoursehub.impl.repository.*;
import com.onlinecoursehub.impl.utils.Badge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.onlinecoursehub.impl.model.Status;

import java.util.List;


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
    @Autowired
    private CompletionRecordRepository completionRecordRepository;


    public String enrollForCourse(Long studentId, Long courseId) {
        if (!studentRepository.existsById(studentId))
            throw new RuntimeException( "Student doesn't exists in the database");
        if (!courseRepository.existsById(courseId))
           throw new RuntimeException("course doesn't available in our platform");

        if (studentRepository.findById(studentId).get().getEnrollments().contains(courseId) || courseRepository.findById(courseId).get().getEnrollments().contains(studentId))
            throw new RuntimeException("Student already registered for the course");
        if (studentRepository.findById(studentId).get().getEnrollments().size()>5)
            throw new RuntimeException("Student enrolment limit exceeded............");

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

    public Object updateProgressByEnrollmentId(long enrollmentId, double progressPercentage) {
        if (enrollmentRepository.existsById(enrollmentId)) {
            Enrollment enrollment=enrollmentRepository.findById(enrollmentId).get();
            if (enrollment.getStatus() == Status.COMPLETED) {
                throw new RuntimeException("Cannot update completed enrollment");
            }

            Student student=enrollment.getStudent();
            Course course=enrollment.getCourse();
            enrollment.setProgressPercentage(progressPercentage);
            if (progressPercentage==100){
                enrollment.setStatus(Status.COMPLETED);
                CompletionRecord completionRecord=CompletionRecord.builder().studentEmail(student.getEmail()).studentName(student.getName()).courseName(course.getTitle()).build();
                completionRecordRepository.save(completionRecord);
                Badge badge = Badge.builder().name(course.getTitle() + ":Master").student(student).build();
                if (badgeRepository.existsByName(badge.getName())){
                    studentRepository.save(student);
                    courseRepository.save(course);
                    return "Student Progress Updated\n"+entityToDto(enrollmentRepository.save(enrollment));
                }

                student.getBadges().add(badge);
                studentRepository.save(student);
                courseRepository.save(course);

                return "Student Progress Updated\n"+entityToDto(enrollmentRepository.save(enrollment),badge);
            }
            else if (progressPercentage>=50){
                enrollment.setStatus(Status.HALF_WAY);
                Badge badge = Badge.builder().name(course.getTitle() + ":Intermmediate").student(student).build();
                if (badgeRepository.existsByName(badge.getName())){
                    studentRepository.save(student);
                    courseRepository.save(course);
                    return "Student Progress Updated\n"+entityToDto(enrollment);
                }
                student.getBadges().add(badge);
                studentRepository.save(student);
                courseRepository.save(course);
                enrollmentRepository.save(enrollment);
                return "Student Progress Updated\n"+entityToDto(enrollment,badge);
            }
        }
        throw new RuntimeException("No such enrollment record found");
    }


    public EnrollmentDto entityToDto(Enrollment enrollment) {
        return new EnrollmentDto().builder()
                .courseTitle(enrollment.getCourse().getTitle())
                .studentName(enrollment.getStudent().getName())
                .status(enrollment.getStatus())
                .build();
    }
    public EnrollmentDto entityToDto(Enrollment enrollment,Badge badge) {
        return new EnrollmentDto(enrollment.getStudent().getName(), enrollment.getCourse().getTitle(), enrollment.getStatus(), enrollment.getProgressPercentage(),badge.getName());
    }

    public Object unenrollByEnrollmentId(long enrollmentId, long courseId) {
            if(!enrollmentRepository.existsByEnrolmentIdAndCourseId(enrollmentId, courseId))
                throw new RuntimeException("Enrollments not found");

            Enrollment enrollment=enrollmentRepository.findById(enrollmentId).get();
            enrollmentRepository.delete(enrollment);

            return enrollment.getCourse().getTitle()+"Enrollement deleted successfully";
    }

    public List<EnrollmentDto> getEnrollmentList() {
        return enrollmentRepository.findAll().stream().map(this::entityToDto).toList();
    }

    public EnrollmentDto getEnrollmentById(long id){
        return entityToDto(enrollmentRepository.getById(id));
    }
}
