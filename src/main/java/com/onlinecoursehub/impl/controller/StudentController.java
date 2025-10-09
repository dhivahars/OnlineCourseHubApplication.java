package com.onlinecoursehub.impl.controller;

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

    @PostMapping("/enroll")
    public ResponseEntity<Object> addStudent(@RequestBody Student student) {
        if (studentService.addStudent(student) != null) {
            return ResponseEntity.ok(new StudentDto(student.getName(), student.getEmail(), student.getEnrollments().stream().map(Enrollment::getCourse).map(a -> a.getTitle()).toList()));
        }
        return ResponseEntity.badRequest().body("Email Already Exists");
    }

    @GetMapping("/enrolled")
    public ResponseEntity<List<StudentDto>> studentsList() {
        return ResponseEntity.ok(studentService.getStudentsList());
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<StudentDto> getStudentById(@PathVariable long id) {
        return ResponseEntity.ok(studentService.entityToDto(studentService.getStudentById(id).get()));
    }

    @GetMapping("name/{name}")
    public ResponseEntity<StudentDto> getStudentByName(@PathVariable String name) {
        return ResponseEntity.ok(studentService.entityToDto(studentService.getStudentByName(name).get()));
    }

    @PatchMapping("/update/{id}")
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
