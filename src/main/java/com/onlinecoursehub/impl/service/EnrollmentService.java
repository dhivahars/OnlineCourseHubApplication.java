package com.onlinecoursehub.impl.service;

import com.onlinecoursehub.impl.dto.EnrollmentDto;
import com.onlinecoursehub.impl.model.*;
import com.onlinecoursehub.impl.repository.*;
import com.onlinecoursehub.impl.utils.Badge;
import com.onlinecoursehub.impl.utils.CompletionRecord;
import com.onlinecoursehub.impl.utils.ErrorMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.onlinecoursehub.impl.model.Status;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.ErrorResponse;

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
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ErrorMessage errorMessage;

    @Transactional
    public ErrorMessage enrollForCourse(String email, Long courseId) {
        if (!studentRepository.existsByEmail(email))
            return errorMessage.builder().success(false).message("Student doesn't exists in the database").build();
        if (!courseRepository.existsById(courseId))
            return errorMessage.builder().success(false).message("course doesn't available in our platform").build();
        Long studentId=studentRepository.findByEmail(email).getId();


        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));
        if (student.getEnrollments().stream().anyMatch(enrollment -> enrollment.getCourse().getId() == course.getId())) {
            return errorMessage.builder().success(false).message("Student already registered for the course").build();
        }
        if (!course.getPrerequisites().isEmpty()) {
            if (student.getSkills().isEmpty() || !student.getSkills().containsAll(course.getPrerequisites())) {
                return errorMessage.builder().success(false).message("Prerequisite doesn't met.......").build();
            }
        }
        if ((course.getCapacity() - course.getEnrollments().size()) > 0) {
            Enrollment e = new Enrollment();
            Badge badge = Badge.builder().name(course.getTitle() + ":Beginner").student(student).build();
            e.setStatus(Status.IN_PROGRESS);
            e.setCourse(course);
            e.setStudent(student);
            student.getEnrollments().add(e);
            course.getEnrollments().add(e);
            enrollmentRepository.save(e);
            studentRepository.save(student);
            courseRepository.save(course);
            badgeRepository.save(badge);
        }
        return errorMessage.builder().success(true).message("Course registration successfull........").build();
    }

    public ErrorMessage updateProgressByEnrollmentId(long enrollmentId, double progressPercentage) {
        if (enrollmentRepository.existsById(enrollmentId)) {
            Enrollment enrollment = enrollmentRepository.findById(enrollmentId).get();
            if (enrollment.getStatus() == Status.COMPLETED) {
                return errorMessage.builder().success(false).message("Cannot update completed enrollment").build();
            }
            if (progressPercentage < enrollment.getProgressPercentage())
                throw new RuntimeException("Error...............");

            Student student = enrollment.getStudent();
            Course course = enrollment.getCourse();
            enrollment.setProgressPercentage(progressPercentage);
            if (progressPercentage == 100) {
                enrollment.setStatus(Status.COMPLETED);
                student.getSkills().add(course.getSkill());
                userRepository.findByEmail(enrollment.getStudent().getEmail()).get().getSkills().add(course.getSkill());

                CompletionRecord completionRecord = CompletionRecord.builder().studentEmail(student.getEmail()).studentName(student.getName()).courseName(course.getTitle()).build();
                completionRecordRepository.save(completionRecord);
                Badge badge = Badge.builder().name(course.getTitle() + ":Master").student(student).build();
                if (badgeRepository.existsByName(badge.getName())) {
                    studentRepository.save(student);
                    courseRepository.save(course);
                    return errorMessage.builder().success(true).message("Student Progress Updated\n" + entityToDto(enrollmentRepository.save(enrollment))).build() ;
                }

                student.getBadges().add(badge);
                studentRepository.save(student);
                courseRepository.save(course);

                return errorMessage.builder().success(true).message("Student Progress Updated\n" + entityToDto(enrollmentRepository.save(enrollment), badge)).build();
            } else if (progressPercentage <= 50) {
                studentRepository.save(student);
                courseRepository.save(course);
                enrollmentRepository.save(enrollment);
                return errorMessage.builder().success(true).message("Student Progress Updated\n" + entityToDto(enrollment)).build();
            } else if (progressPercentage >= 50) {
                enrollment.setStatus(Status.HALF_WAY);
                Badge badge = Badge.builder().name(course.getTitle() + ":Intermmediate").student(student).build();
                if (badgeRepository.existsByName(badge.getName())) {
                    studentRepository.save(student);
                    courseRepository.save(course);
                    return errorMessage.builder().success(true).message("Student Progress Updated\n" + entityToDto(enrollment)).build();
                }
                student.getBadges().add(badge);
                studentRepository.save(student);
                courseRepository.save(course);
                enrollmentRepository.save(enrollment);
                return errorMessage.builder().success(true).message("Student Progress Updated\n" + entityToDto(enrollment, badge)).build();
            }
        }
        return errorMessage.builder().success(false).message("No such enrollment record found").build();
    }


    public EnrollmentDto entityToDto(Enrollment enrollment) {
        return new EnrollmentDto().builder()
                .courseTitle(enrollment.getCourse().getTitle())
                .id(enrollment.getId())
                .courseId(enrollment.getCourse().getId())
                .studentName(enrollment.getStudent().getName())
                .status(enrollment.getStatus())
                .progressPercentage(enrollment.getProgressPercentage())
                .build();
    }

    public EnrollmentDto entityToDto(Enrollment enrollment, Badge badge) {
        return new EnrollmentDto(enrollment.getId(),enrollment.getCourse().getId(), enrollment.getStudent().getName(), enrollment.getCourse().getTitle(), enrollment.getStatus(), enrollment.getProgressPercentage());
    }

    public ErrorMessage unenrollByEnrollmentId(long enrollmentId, long courseId) {
        if (!enrollmentRepository.existsByEnrolmentIdAndCourseId(enrollmentId, courseId))
            return errorMessage.builder().success(false).success(false).message("Enrollments not found").build();

        Enrollment enrollment = enrollmentRepository.findById(enrollmentId).get();
        enrollmentRepository.delete(enrollment);

        return errorMessage.builder().success(true).message(enrollment.getCourse().getTitle() + "Enrollement deleted successfully").build();
    }


    public List<EnrollmentDto> getEnrollmentById(String email) {
        return enrollmentRepository.findByStudentEmail(email).stream().map(this::entityToDto).toList();
    }
}
