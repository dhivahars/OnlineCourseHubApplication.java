package com.onlinecoursehub.impl.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.onlinecoursehub.impl.utils.Badge;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Student{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "student_id")
    private long id;

    @NotBlank
    @Column(name="student_name",nullable = false)
    private String name;

    @Column(name="student_email",nullable = false,unique = true)
    @Email
    private String email;

    @Column(name="student_password",nullable = false)
    @Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!*()]).{8,}$")

    private String password;

    @Column(name="student_registrationDate",nullable =false,updatable = false)
    @CreationTimestamp
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate registrationDate;


    @OneToMany(mappedBy = "student",cascade = CascadeType.ALL)
    @JsonIgnore
    @ToString.Exclude
    private List<Enrollment> enrollments=new ArrayList<>();

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    @JsonIgnore
    @ToString.Exclude
    private List<Badge> badges = new ArrayList<>();

}
