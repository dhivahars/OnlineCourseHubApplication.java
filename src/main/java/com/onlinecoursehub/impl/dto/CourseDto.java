package com.onlinecoursehub.impl.dto;

import com.onlinecoursehub.impl.model.Mentor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CourseDto {
    private String title;
    private String description;
    private int capacity;
    private Mentor mentor;
}
