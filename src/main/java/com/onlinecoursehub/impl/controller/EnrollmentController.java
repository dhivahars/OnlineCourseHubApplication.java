package com.onlinecoursehub.impl.controller;

import com.onlinecoursehub.impl.dto.EnrollmentDto;
import com.onlinecoursehub.impl.service.EnrollmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/enroll")
public class EnrollmentController {
    @Autowired
    private EnrollmentService enrollmentService;

    @PostMapping("/student/{email}/course/{courseId}")
    public ResponseEntity<String> addStudent(@PathVariable String email, @PathVariable Long courseId) {
        return ResponseEntity.ok(enrollmentService.enrollForCourse(email, courseId));
    }
    @PatchMapping("/update/progress")
    public Object updateProgressByEnrollmentId(@RequestParam long enrollmentId,
                                                       @RequestParam double progressPercentage) {
        return enrollmentService.updateProgressByEnrollmentId(enrollmentId, progressPercentage);
    }
    @DeleteMapping("/unenroll/{enrollmentId}/{courseId}")
    public Object unenrollByEnrollmentId(@PathVariable("enrollmentId") long enrollmentId,
                                         @PathVariable("courseId") long courseId){
        return enrollmentService.unenrollByEnrollmentId(enrollmentId, courseId);
    }
    @GetMapping("/search/{email}")
    public List<EnrollmentDto> getEnrollmentById(@PathVariable String email){
        return enrollmentService.getEnrollmentById(email);
    }
}
