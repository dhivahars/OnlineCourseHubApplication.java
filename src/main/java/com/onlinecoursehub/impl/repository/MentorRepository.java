package com.onlinecoursehub.impl.repository;

import com.onlinecoursehub.impl.model.Mentor;
import jakarta.validation.constraints.Email;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MentorRepository extends JpaRepository<Mentor,Long> {
    Optional<Mentor> findByName(String name);
    Optional<Mentor> findById (Long id);

    boolean existsByEmail(@Email String email);
}
