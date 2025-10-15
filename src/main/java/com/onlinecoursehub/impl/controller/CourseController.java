package com.onlinecoursehub.impl.controller;

import com.onlinecoursehub.impl.dto.CourseDto;
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

        @PostMapping("/create")
        public ResponseEntity<CourseDto> createCourse(@RequestBody Course course){
            return new ResponseEntity<>(courseService.addCourse(course), HttpStatus.CREATED);
        }

        @GetMapping("/list")
        public ResponseEntity<List<CourseDto>> showCourse(){
            return new ResponseEntity<>(courseService.listCourse(),HttpStatus.FOUND);
        }
        @GetMapping("/search/id/{id}")
        public ResponseEntity<CourseDto> ShowById(@PathVariable long id){
            return new ResponseEntity<>(courseService.showById(id).get(),HttpStatus.FOUND);
        }
        @GetMapping("/search/name/{name}")
        public ResponseEntity<CourseDto> ShowById(@PathVariable String name){
            return new ResponseEntity<>(courseService.showByName(name).get(),HttpStatus.FOUND);
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
        @PutMapping("/update-mentor")
        public ResponseEntity<Course> assignMentor(@RequestParam("courseId") Long courseId, @RequestParam("mentorId") Long mentorId) {
            return ResponseEntity.ok(courseService.modifyMentorToCourse(courseId, mentorId));
        }
}
