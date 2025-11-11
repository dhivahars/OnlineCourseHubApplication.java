package com.onlinecoursehub.impl.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MentorStudentDto {
  private String studentName;
  private String courseName;
  private double progressPercentage;
}
