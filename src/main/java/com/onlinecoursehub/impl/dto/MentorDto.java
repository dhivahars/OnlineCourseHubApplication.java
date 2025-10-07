package com.onlinecoursehub.impl.dto;

import com.onlinecoursehub.impl.model.Course;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MentorDto{
    private String name;
    private String email;
}
