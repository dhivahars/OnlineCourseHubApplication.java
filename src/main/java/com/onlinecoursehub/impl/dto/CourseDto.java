package com.onlinecoursehub.impl.dto;

import com.onlinecoursehub.impl.model.Mentor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CourseDto {
    private long id;
    private String title;
    private String description;
    private int capacity;
    private Mentor mentorName;
    private Set<String> prerequisites=new HashSet<>();
    private String url;
    private String skill;
    private int enrolledCount;
    private String mentorEmail;
}
