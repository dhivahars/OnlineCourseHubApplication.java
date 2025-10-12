package com.onlinecoursehub.impl.utils;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.onlinecoursehub.impl.model.Student;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Badge {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "badge_id",unique = true)
    private long id;

    @Column(name ="badge_name",nullable = false)
    @NotBlank
    private String name;

    @Column(name="assigned_date")
    @CreationTimestamp
    @JsonFormat(pattern="dd/MM/yyyy")
    private LocalDateTime assignedDate;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    @JsonIgnore
    @ToString.Exclude
    private Student student;
}
