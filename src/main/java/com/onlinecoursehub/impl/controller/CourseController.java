package com.onlinecoursehub.impl.controller;

import com.onlinecoursehub.impl.model.Course;
import com.onlinecoursehub.impl.model.Student;
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
        @PatchMapping("/update/{id}")
        public ResponseEntity<String> updateCourse(@PathVariable long id,@RequestBody Course c){
            return ResponseEntity.ok(courseService.updateCourse(id,c));
        }
        @DeleteMapping("/delete/id/{id}")
        public ResponseEntity<String> deleteCourseById(@PathVariable long id){
            return ResponseEntity.ok(courseService.deleteCourseById(id));
        }
        @DeleteMapping("/delete/title/{title}")
        public ResponseEntity<String> deleteCourseByName(@PathVariable String name){
            return ResponseEntity.ok(courseService.deleteCourseByTitle(name));
        }
        @GetMapping("/capacity/id/{id}")
        public ResponseEntity<String> getCourseCapacityById(@PathVariable long id) {
            return ResponseEntity.ok(courseService.getCourseCapacityById(id));
        }
        @GetMapping("/capacity/name/{name}")
        public ResponseEntity<String> getCourseCapacityByName(@PathVariable String name) {
            return ResponseEntity.ok(courseService.getCourseCapacityByName(name));
        }
}
