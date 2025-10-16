package com.onlinecoursehub.impl.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;



@Entity
@Data
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
    @JsonIgnore
    @ToString.Exclude
    private Student student;

     @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
     @JsonIgnore
     @ToString.Exclude
    private Course course;

   @Enumerated(EnumType.STRING)
    private Status status;

    @Column(name = "progress_percentage")
    private double progressPercentage=0;

    @Column(name = "enrollment_date",nullable = false)
    @CreationTimestamp
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate enrollmentDate;
}
