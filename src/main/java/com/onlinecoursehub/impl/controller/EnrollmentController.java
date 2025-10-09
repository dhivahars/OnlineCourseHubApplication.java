package com.onlinecoursehub.impl.controller;

import com.onlinecoursehub.impl.dto.EnrollmentDto;
import com.onlinecoursehub.impl.model.Enrollment;
import com.onlinecoursehub.impl.service.EnrollmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public Object updateProgressByStudentIdAndCourseId(@RequestParam long enrollmentId,
                                                       @RequestParam double progressPercentage) {
        EnrollmentDto enrollmentDto=enrollmentService.updateProgressByEnrollmentId(enrollmentId, progressPercentage);
        if ( enrollmentDto!= null)
            return "Student progress Updated Successfully\n"+enrollmentDto;

        return "The student and course pair doesnt exists";
    }
}
