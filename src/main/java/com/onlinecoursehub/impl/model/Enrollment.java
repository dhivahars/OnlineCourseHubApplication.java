package com.onlinecoursehub.impl.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

enum Status{IN_PROGRESS,HALF_WAY,COMPLETED;}

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Enrollment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="Enrollment_id")
    private long id;

    @ManyToOne
    @JoinColumn(name = "student_id",nullable = false)
    private Student student;

    @ManyToOne
    @JoinColumn(name = "course_id",nullable = false)
    private Course course;

   @Enumerated(EnumType.STRING)
    private Status status;

    @Column(name = "progress_percentage")
    private int progressPercentage=0;

    @Column(name = "enrollment_date",nullable = false)
    @CreationTimestamp
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDateTime enrollmentDate;
}
