package com.onlinecoursehub.impl.service;

import com.onlinecoursehub.impl.dto.UserDto;
import com.onlinecoursehub.impl.model.User;
import com.onlinecoursehub.impl.repository.UserRepository;
import com.onlinecoursehub.impl.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    JwtUtils jwtUtils;


    public ResponseEntity<UserDto> getCurrentUser(Authentication authentication) {
        String email = authentication.getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return ResponseEntity.ok(UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .role(user.getRole()).build());
    }
}
