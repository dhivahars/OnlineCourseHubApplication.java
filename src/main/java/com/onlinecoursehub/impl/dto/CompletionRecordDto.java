package com.onlinecoursehub.impl.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompletionRecordDto {
    private String studentName;
    private String studentEmail;
    private LocalDate completionDate;
    private String courseName;
}
