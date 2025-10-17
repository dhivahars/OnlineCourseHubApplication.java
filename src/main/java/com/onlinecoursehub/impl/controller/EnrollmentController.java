package com.onlinecoursehub.impl.controller;

import com.onlinecoursehub.impl.dto.EnrollmentDto;
import com.onlinecoursehub.impl.model.Enrollment;
import com.onlinecoursehub.impl.model.Student;
import com.onlinecoursehub.impl.service.EnrollmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/enrollment")
public class EnrollmentController {
    @Autowired
    private EnrollmentService enrollmentService;

    @PostMapping("/enroll")
    public ResponseEntity<String> addStudent(@RequestParam Long studentId, @RequestParam Long courseId) {
        return ResponseEntity.ok(enrollmentService.enrollForCourse(studentId, courseId));
    }
    @PatchMapping("/update/progress/id")
    public Object updateProgressByEnrollmentId(@RequestParam long enrollmentId,
                                                       @RequestParam double progressPercentage) {
        return enrollmentService.updateProgressByEnrollmentId(enrollmentId, progressPercentage);
    }
    @DeleteMapping("/unenroll/id")
    public Object unenrollByEnrollmentId(@RequestParam long enrollmentId,
                                         @RequestParam long courseId){
        return enrollmentService.unenrollByEnrollmentId(enrollmentId, courseId);
    }
    @GetMapping("/search/{id}")
    public EnrollmentDto getEnrollmemtById(@PathVariable long id){
        return enrollmentService.getEnrollmentById(id);
    }
}
