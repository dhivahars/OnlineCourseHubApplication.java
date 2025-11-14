package com.onlinecoursehub.impl.controller;

import com.onlinecoursehub.impl.dto.UserDto;
import com.onlinecoursehub.impl.model.User;
import com.onlinecoursehub.impl.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;
    @GetMapping("/me")
    public ResponseEntity<UserDto> getCurrentUser(Authentication authentication) {
        return userService.getCurrentUser(authentication);
    }
}
