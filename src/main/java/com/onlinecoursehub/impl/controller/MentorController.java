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

import java.util.List;

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
}
