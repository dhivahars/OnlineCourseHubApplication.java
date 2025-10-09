package com.onlinecoursehub.impl.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudentDto {
    private String name;
    private String email;
    private List<String> enrollments=new ArrayList<>();
}
