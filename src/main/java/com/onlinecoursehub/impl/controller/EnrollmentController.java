package com.onlinecoursehub.impl.controller;

import com.onlinecoursehub.impl.dto.EnrollmentDto;
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
    @GetMapping("/search/{id}")
    public List<EnrollmentDto> getEnrollmentById(@PathVariable long id){
        return enrollmentService.getEnrollmentById(id);
    }
}
