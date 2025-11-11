package com.onlinecoursehub.impl.repository;

import com.onlinecoursehub.impl.model.Enrollment;
import com.onlinecoursehub.impl.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment,Long> {

    boolean existsById(long enrollmentId);

    List<Enrollment> findByStatus(Status status);
    List<Enrollment> findByStudentId(Long studentId);

    @Query("SELECT COUNT(e) > 0 FROM Enrollment e WHERE e.id = :enrollmentId AND e.course.id = :courseId")
    boolean existsByEnrolmentIdAndCourseId(@Param("enrollmentId") Long enrollmentId, @Param("courseId") Long courseId);

    List<Enrollment> findByStudentEmail(String email);
}
