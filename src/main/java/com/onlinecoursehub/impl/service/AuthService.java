package com.onlinecoursehub.impl.service;

import com.onlinecoursehub.impl.model.Mentor;
import com.onlinecoursehub.impl.model.Student;
import com.onlinecoursehub.impl.model.User;
import com.onlinecoursehub.impl.repository.MentorRepository;
import com.onlinecoursehub.impl.repository.StudentRepository;
import com.onlinecoursehub.impl.repository.UserRepository;
import com.onlinecoursehub.impl.utils.JwtUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private MentorRepository mentorRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtils jwtUtils;

    // Register user
    public User addUser(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("User already exists");
        }

        // Encode password
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        switch (user.getRole().toLowerCase()) {
            case "student": {
                Student student = Student.builder()
                        .name(user.getName())
                        .email(user.getEmail())
                        .password(user.getPassword())
                        .skills(user.getSkills())
                        .build();
                studentRepository.save(student);
                return userRepository.save(user);
            }
            case "mentor": {
                Mentor mentor = Mentor.builder()
                        .name(user.getName())
                        .email(user.getEmail())
                        .password(user.getPassword())
                        .build();
                mentorRepository.save(mentor);
                return userRepository.save(user);
            }
            default:
                throw new RuntimeException("User registration unsuccessful");
        }
    }

    // Login user and generate JWT
    public String loginUser(User user) {
        User dbUser = userRepository.findByEmail(user.getEmail())
                .orElseThrow(() -> new RuntimeException("User does not exist"));
        if (!passwordEncoder.matches(user.getPassword(), dbUser.getPassword())) {
            throw new RuntimeException("Invalid password");
        }
        return jwtUtils.generateToken(dbUser.getEmail());
    }
}
