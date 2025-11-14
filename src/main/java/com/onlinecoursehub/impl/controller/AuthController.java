package com.onlinecoursehub.impl.controller;

import com.onlinecoursehub.impl.model.User;
import com.onlinecoursehub.impl.service.AuthService;
import com.onlinecoursehub.impl.utils.ApiError;
import com.onlinecoursehub.impl.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@CrossOrigin("http://localhost:4200/")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody User user) {
        return ResponseEntity.ok(authService.addUser(user));
    }

    // Login endpoint returns JWT
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody User user) {
      ApiResponse response=(ApiResponse) authService.loginUser(user);
      if(!response.isSuccess())
          return new ResponseEntity<>(response.getError(),HttpStatus.BAD_REQUEST);

      return new ResponseEntity<>(response,HttpStatus.OK);
    }
}