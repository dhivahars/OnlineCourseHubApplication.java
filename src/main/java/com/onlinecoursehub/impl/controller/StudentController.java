package com.onlinecoursehub.impl.controller;

import com.onlinecoursehub.impl.dto.StudentDto;
import com.onlinecoursehub.impl.model.Student;
import com.onlinecoursehub.impl.service.StudentService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
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
    public ResponseEntity<StudentDto> addStudent(@Valid @RequestBody Student student) {
        return ResponseEntity.ok(studentService.addStudent(student));
    }

    @GetMapping("/list")
    public ResponseEntity<List<StudentDto>> studentsList() {
        return ResponseEntity.ok(studentService.getStudentsList());
    }
    @PatchMapping("/update/{id}")
    public ResponseEntity<String> updateStudent(@PathVariable long id, @RequestBody Student s) {
        return ResponseEntity.ok(studentService.updateStudent(id, s));
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteStudentById(@PathVariable long id) {
        return ResponseEntity.ok(studentService.deleteStudentById(id));
    }
    @GetMapping("/search")
    public ResponseEntity<StudentDto> searchStudent(@RequestParam(required = false) Long id,@RequestParam(required = false) String name,@Email @RequestParam(required = false) String email){
        return ResponseEntity.ok(studentService.searchStudent(id,name,email));
    }
}
