package com.onlinecoursehub.impl.dto;

import com.onlinecoursehub.impl.model.Status;
import com.onlinecoursehub.impl.model.Course;
import com.onlinecoursehub.impl.model.Student;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EnrollmentDto {
    private long id;
    private long courseId;
    private String studentName;
    private String courseTitle;
    private Status status;
    private double progressPercentage;
}
