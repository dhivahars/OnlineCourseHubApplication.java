package com.onlinecoursehub.impl.controller;

import com.onlinecoursehub.impl.dto.EnrollmentDto;
import com.onlinecoursehub.impl.dto.StudentDto;
import com.onlinecoursehub.impl.model.Enrollment;
import com.onlinecoursehub.impl.model.Student;
import com.onlinecoursehub.impl.service.EnrollmentService;
import com.onlinecoursehub.impl.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/enrollment")
public class EnrollmentController{
    @Autowired
    private EnrollmentService enrollmentService;

    @PostMapping("/enroll")
    public ResponseEntity<String> addStudent(@RequestBody Long studentId,@RequestBody Long  courseId){
        return ResponseEntity.ok(enrollmentService.enrollForCourse(studentId,courseId));
    }
}
