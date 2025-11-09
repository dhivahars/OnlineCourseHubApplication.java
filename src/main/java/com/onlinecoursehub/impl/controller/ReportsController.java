package com.onlinecoursehub.impl.controller;

import com.onlinecoursehub.impl.dto.EnrollmentDto;
import com.onlinecoursehub.impl.model.Student;
import com.onlinecoursehub.impl.service.CompletionRecordService;
import com.onlinecoursehub.impl.service.CourseService;
import com.onlinecoursehub.impl.service.EnrollmentService;
import com.onlinecoursehub.impl.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.onlinecoursehub.impl.dto.CompletionRecordDto;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/reports")
public class ReportsController {
    @Autowired
    CompletionRecordService completionRecordService;
    @Autowired
    CourseService courseService;
    @Autowired
    StudentService studentService;

    @GetMapping("/courses/completed")
    public HashMap<String, List<CompletionRecordDto>> getCompletedCourseList(){
        return (HashMap<String, List<CompletionRecordDto>>) completionRecordService.getCompletedCourseHistory();
    }
    @GetMapping("/student/course/{id}")
    public ResponseEntity<String> getStudentPerCourse(@PathVariable long id){
        return ResponseEntity.ok(courseService.studentPerCourse(id));
    }
    @GetMapping("/student/progress/{id}")
    public ResponseEntity<String> getStudentProgress(@PathVariable long id){
        return ResponseEntity.ok(courseService.studentProgress(id));
    }
    @GetMapping("/student/badge/{id}")
    public ResponseEntity<String> getStudentBadges(@PathVariable long id){
        return ResponseEntity.ok(studentService.studentBadges(id));
    }
}
