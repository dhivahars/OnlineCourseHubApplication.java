package com.onlinecoursehub.impl.dto;

import ch.qos.logback.core.status.Status;
import com.onlinecoursehub.impl.model.Course;
import com.onlinecoursehub.impl.model.Student;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EnrollmentDto {
    private Student student;
    private Course course;
    private Status status;
    private int progressPercentage;
}
