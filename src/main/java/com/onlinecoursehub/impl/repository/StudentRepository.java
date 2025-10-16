package com.onlinecoursehub.impl.repository;

import com.onlinecoursehub.impl.model.Student;
import jakarta.validation.constraints.Email;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.logging.Level;

@Repository
public interface StudentRepository extends JpaRepository<Student,Long> {
   Student findByName(String name);

    void deleteByName(String name);

    boolean existsByEmail(@Email String email);

    boolean existsByName(String name);

    Student findByEmail(@Email String email);
}
