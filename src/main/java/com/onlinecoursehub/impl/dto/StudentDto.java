package com.onlinecoursehub.impl.dto;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class StudentDto {
    private String name;
    private String email;
    private List<Long> enrollmentIds=new ArrayList<>();
}
