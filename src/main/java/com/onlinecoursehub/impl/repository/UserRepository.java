package com.onlinecoursehub.impl.repository;

import com.onlinecoursehub.impl.model.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    boolean existsByEmail(@Email String email);

   Optional<User> existsByEmailAndPassword(@Email String email, @Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!*()]).{8,}$") String password);

    Optional<User> findByEmail(@Email String email);
}
