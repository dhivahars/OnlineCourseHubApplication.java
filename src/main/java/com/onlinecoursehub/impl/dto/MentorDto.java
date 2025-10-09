package com.onlinecoursehub.impl.dto;

import com.onlinecoursehub.impl.model.Course;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MentorDto{
    private String name;
    private String email;
}
