package com.onlinecoursehub.impl.controller;

import com.onlinecoursehub.impl.service.CompletionRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.onlinecoursehub.impl.dto.CompletionRecordDto;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/report/courses")
public class Reports {
    @Autowired
    CompletionRecordService completionRecordService;

    @GetMapping("/completed")
    public HashMap<String, List<CompletionRecordDto>> getCompletedCourseList(){
        return (HashMap<String, List<CompletionRecordDto>>) completionRecordService.getCompletedCourseHistory();
    }
}
