package com.onlinecoursehub.impl.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;


import java.time.LocalDateTime;
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
    private LocalDateTime registrationDate;


    @OneToMany(mappedBy = "student",cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Enrollment> enrollments=new ArrayList<>();

}
