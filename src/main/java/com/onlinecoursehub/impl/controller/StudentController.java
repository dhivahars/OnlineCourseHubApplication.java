package com.onlinecoursehub.impl.controller;

import com.onlinecoursehub.impl.dto.EnrollmentDto;
import com.onlinecoursehub.impl.dto.StudentDto;
import com.onlinecoursehub.impl.model.Enrollment;
import com.onlinecoursehub.impl.model.Student;
import com.onlinecoursehub.impl.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/student")
public class StudentController {
    @Autowired
    StudentService studentService;

    @PostMapping("/add")
    public ResponseEntity<Object> addStudent(@RequestBody Student student) {
        if (studentService.addStudent(student) != null) {
            return ResponseEntity.ok(new StudentDto(student.getName(), student.getEmail(), student.getEnrollments().stream().map(Enrollment::getCourse).map(a -> a.getTitle()).toList()));
        }
        return ResponseEntity.badRequest().body("Email Already Exists");
    }

    @GetMapping("/list")
    public ResponseEntity<List<StudentDto>> studentsList() {
        return ResponseEntity.ok(studentService.getStudentsList());
    }

    @GetMapping("/search/id/{id}")
    public ResponseEntity<StudentDto> getStudentById(@PathVariable long id) {
        return ResponseEntity.ok(StudentService.entityToDto(studentService.getStudentById(id).get()));
    }

    @GetMapping("/search/name/{name}")
    public ResponseEntity<StudentDto> getStudentByName(@PathVariable String name) {
        return ResponseEntity.ok(studentService.entityToDto(studentService.getStudentByName(name).get()));
    }

    @PatchMapping("/update/id/{id}")
    public ResponseEntity<String> updateStudent(@PathVariable long id, @RequestBody Student s) {
        return ResponseEntity.ok(studentService.updateStudent(id, s));
    }

    @DeleteMapping("/delete/id/{id}")
    public ResponseEntity<String> deleteStudentById(@PathVariable long id) {
        return ResponseEntity.ok(studentService.deleteStudentById(id));
    }

    @DeleteMapping("/delete/name/{name}")
    public ResponseEntity<String> deleteStudentByName(@PathVariable String name) {
        return ResponseEntity.ok(studentService.deleteStudentByName(name));
    }
}
