package com.onlinecoursehub.impl.controller;

import com.onlinecoursehub.impl.model.Course;
import com.onlinecoursehub.impl.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/course")
public class CourseController {
        @Autowired
        CourseService courseService;

        //Create courses
        @PostMapping("/create")
        public ResponseEntity<Course> createCourse(@RequestBody Course course){
            return new ResponseEntity<>(courseService.addCourse(course), HttpStatus.CREATED);
        }

        @GetMapping("/list")
        public ResponseEntity<List<Course>> showCourse(){
            return new ResponseEntity<>(courseService.listCourse(),HttpStatus.CREATED);
        }
        @GetMapping("/id/{id}")
        public ResponseEntity<Course> ShowById(@PathVariable long id){
            return new ResponseEntity<>(courseService.showById(id).get(),HttpStatus.CREATED);
        }
        @GetMapping("/name/{name}")
        public ResponseEntity<Course> ShowById(@PathVariable String name){
            return new ResponseEntity<>(courseService.showByName(name).get(),HttpStatus.CREATED);
        }
}
