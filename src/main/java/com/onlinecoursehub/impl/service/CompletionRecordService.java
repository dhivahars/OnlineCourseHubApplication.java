package com.onlinecoursehub.impl.service;

import com.onlinecoursehub.impl.dto.CompletionRecordDto;
import com.onlinecoursehub.impl.model.Enrollment;
import com.onlinecoursehub.impl.model.Status;
import com.onlinecoursehub.impl.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CompletionRecordService {
    @Autowired
    private CompletionRecordRepository completionRecordRepository;
    @Autowired
    private EnrollmentRepository enrollmentRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private BadgeRepository badgeRepository;

    public Map<String, List<CompletionRecordDto>> getCompletedCourseHistory() {
        if(enrollmentRepository.findByStatus(Status.COMPLETED).isEmpty())
            throw new RuntimeException("No records found");
        List<Enrollment> completedEnrollments = enrollmentRepository.findByStatus(Status.COMPLETED);
        List<CompletionRecordDto> dtoList = completedEnrollments.stream()
                .map(e -> CompletionRecordDto.builder()
                        .studentName(e.getStudent().getName())
                        .studentEmail(e.getStudent().getEmail())
                        .courseName(e.getCourse().getTitle())
                        .completionDate(LocalDate.now())
                        .build())
                .toList();
        Map<String, List<CompletionRecordDto>> groupByCourseName = dtoList.stream()
                .collect(Collectors.groupingBy(CompletionRecordDto::getCourseName));

        return groupByCourseName;
    }}

