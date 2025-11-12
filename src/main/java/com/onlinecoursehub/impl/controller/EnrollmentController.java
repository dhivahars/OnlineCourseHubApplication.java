package com.onlinecoursehub.impl.controller;

import com.onlinecoursehub.impl.dto.EnrollmentDto;
import com.onlinecoursehub.impl.service.EnrollmentService;
import com.onlinecoursehub.impl.utils.ErrorMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/enroll")
public class EnrollmentController {
    @Autowired
    private EnrollmentService enrollmentService;

    @PostMapping("/student/{email}/course/{courseId}")
    public ResponseEntity<?> addStudent(@PathVariable String email, @PathVariable Long courseId) {
        ErrorMessage errorMessage=enrollmentService.enrollForCourse(email, courseId);
        if(!errorMessage.isSuccess())
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(errorMessage);
        return new ResponseEntity<>(errorMessage,HttpStatus.OK);
    }
    @PatchMapping("/update/progress")
    public ResponseEntity<?> updateProgressByEnrollmentId(@RequestParam long enrollmentId,
                                                       @RequestParam double progressPercentage) {
        ErrorMessage errorMessage=enrollmentService.updateProgressByEnrollmentId(enrollmentId, progressPercentage);
        if(!errorMessage.isSuccess())
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
        return new ResponseEntity<>(errorMessage,HttpStatus.OK);
    }
    @DeleteMapping("/unenroll/{enrollmentId}/{courseId}")
    public Object unenrollByEnrollmentId(@PathVariable("enrollmentId") long enrollmentId,
                                         @PathVariable("courseId") long courseId){
        ErrorMessage errorMessage=enrollmentService.unenrollByEnrollmentId(enrollmentId, courseId);
        if(!errorMessage.isSuccess())
        return ResponseEntity.badRequest().body(errorMessage);
        return new ResponseEntity<>(errorMessage.getMessage(),HttpStatus.OK);
    }
    @GetMapping("/search/{email}")
    public ResponseEntity<List<EnrollmentDto>> getEnrollmentById(@PathVariable String email){
        return new ResponseEntity<>(enrollmentService.getEnrollmentById(email), HttpStatus.OK);
    }
}
