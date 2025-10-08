package com.onlinecoursehub.impl.controller;

import com.onlinecoursehub.impl.model.Course;
import com.onlinecoursehub.impl.model.Mentor;
import com.onlinecoursehub.impl.service.MentorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/mentor")
public class MentorController {
    @Autowired
    MentorService mentorService;
    @PostMapping("/create")
    public ResponseEntity<Mentor> createMentor(@RequestBody Mentor mentor){
        return new ResponseEntity<>(mentorService.addMentor(mentor), HttpStatus.CREATED);
    }
    @GetMapping("/list")
    public ResponseEntity<List<Mentor>> ShowMentorList(){
        return new ResponseEntity<>(mentorService.listMentor(),HttpStatus.CREATED);
    }
    @GetMapping("/name/{name}")
    public  ResponseEntity<Mentor> listByName(@PathVariable String name){
        return new ResponseEntity<>(mentorService.showByName(name).get(),HttpStatus.CREATED);
    }
    @GetMapping("/id/{id}")
    public  ResponseEntity<Mentor> listById(@PathVariable long id){
        return new ResponseEntity<>(mentorService.showById(id).get(),HttpStatus.CREATED);
    }
    @PatchMapping("/update/id/{id}")
    public ResponseEntity<String> updateMentor(@PathVariable long id,Mentor m){
        return ResponseEntity.ok(mentorService.updateMentor(id,m));
    }
    @DeleteMapping("/delete/id/{id}")
    public ResponseEntity<String> deleteMentorById(@PathVariable long id){
        return ResponseEntity.ok(mentorService.deleteMentorById(id));
    }
}
