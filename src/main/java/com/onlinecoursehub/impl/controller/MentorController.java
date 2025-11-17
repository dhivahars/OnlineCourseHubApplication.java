package com.onlinecoursehub.impl.controller;

import com.onlinecoursehub.impl.dto.MentorDto;
import com.onlinecoursehub.impl.model.Course;
import com.onlinecoursehub.impl.model.Mentor;
import com.onlinecoursehub.impl.service.MentorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/mentor")
public class MentorController {
    @Autowired
    MentorService mentorService;
    @PostMapping("/create")
    public ResponseEntity<MentorDto> createMentor(@Valid @RequestBody Mentor mentor){
        return new ResponseEntity<>(mentorService.addMentor(mentor), HttpStatus.CREATED);
    }
    @GetMapping("/list")
    public ResponseEntity<List<MentorDto>> showMentorList(){
        return new ResponseEntity<>(mentorService.listMentor(),HttpStatus.OK);
    }
    @GetMapping("/search/{id}")
    public  ResponseEntity<Mentor> listById(@PathVariable long id){
        return new ResponseEntity<>(mentorService.showById(id).orElseThrow(()->new RuntimeException("Not found....")),HttpStatus.OK);
    }
    @PatchMapping("/update/{id}")
    public ResponseEntity<String> updateMentor(@PathVariable long id,@RequestBody Mentor m){
        return ResponseEntity.ok(mentorService.updateMentor(id,m));
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteMentorById(@PathVariable long id){
        return ResponseEntity.ok(mentorService.deleteMentorById(id));
    }
    @PostMapping("/create/course")
    public ResponseEntity<Course> createCourse(@Valid @RequestBody Course course,@RequestParam String email){
        return new ResponseEntity<>(mentorService.createCourseWithMentor(course,email),HttpStatus.OK);
    }
    @GetMapping("/about/{email}")
    public ResponseEntity<Map<String,String>> aboutMentors(@PathVariable String email){
        Map<String,String> map = new HashMap<>();
        map.put("about",mentorService.getAbout(email));
        return new ResponseEntity<>(map,HttpStatus.OK);
    }
    @GetMapping("/{email}/details")
    public ResponseEntity<MentorDto> mentorDetails(@PathVariable String email){
        return ResponseEntity.ok(mentorService.getMentorByEmail(email));
    }
}
