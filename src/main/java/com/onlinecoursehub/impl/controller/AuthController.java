package com.onlinecoursehub.impl.controller;

import com.onlinecoursehub.impl.model.User;
import com.onlinecoursehub.impl.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody User user) {
        return ResponseEntity.ok(authService.addUser(user));
    }

    // Login endpoint returns JWT
    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody User user) {
        String token = authService.loginUser(user);
        return ResponseEntity.ok(token);
    }
}
