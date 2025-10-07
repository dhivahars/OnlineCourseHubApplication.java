package com.onlinecoursehub.impl.controller;

import com.onlinecoursehub.impl.model.Student;
import com.onlinecoursehub.impl.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/student")
public class StudentController {
    @Autowired
    StudentService studentService;

    @PostMapping("/enroll")
    public ResponseEntity<Student> addStudent(@RequestBody Student student){
        return new ResponseEntity<>(studentService.addStudent(student), HttpStatus.CREATED);
    }

    @GetMapping("s/enrolled")
    public ResponseEntity<List<Student>> studentsList(){
        return new ResponseEntity<>(studentService.getStudentsList(),HttpStatus.OK);
    }

    @GetMapping("/id/{id}")
    public Student getStudentById(@PathVariable long id){
        return studentService.getStudentById(id).get();
    }
    @GetMapping("name/{name}")
    public Student getStudentByName(@PathVariable String name){
        return studentService.getStudentByName(name).get();
    }

    @PatchMapping("/update")
    public ResponseEntity<String> updateStudent(@RequestBody Student s){
        return new ResponseEntity<>(studentService.updateStudent(s),HttpStatus.OK);
    }
    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteStudent(@RequestParam int id){
        return new ResponseEntity<>(studentService.deleteStudentById(id),HttpStatus.OK);
    }
}
