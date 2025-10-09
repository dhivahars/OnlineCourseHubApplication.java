package com.onlinecoursehub.impl.controller;

import com.onlinecoursehub.impl.service.EnrollmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/enrollment")
public class EnrollmentController{
    @Autowired
    private EnrollmentService enrollmentService;

    @PostMapping("/enroll")
    public ResponseEntity<String> addStudent(@RequestParam Long studentId,@RequestParam Long  courseId){
        return ResponseEntity.ok(enrollmentService.enrollForCourse(studentId,courseId));
    }
}
