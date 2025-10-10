package com.onlinecoursehub.impl.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CompletionRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "student_name")
    private String studentName;

    @Column(name="email")
    @Email
    private String studentEmail;

    @Column(name="completion_date")
    private LocalDate completionDate;

    @Column(name = "course_name")
    private String courseName;
}
