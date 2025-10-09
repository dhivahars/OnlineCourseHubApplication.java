package com.onlinecoursehub.impl.repository;

import com.onlinecoursehub.impl.model.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment,Long> {

    boolean existsById(long enrollmentId);

}
