package com.onlinecoursehub.impl.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Mentor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="mentor_id")
    private long id;

    @Column(name = "mentor_name",nullable = false)
    @NotBlank
    private String name;

    @Column(name = "mentor_mail",nullable = false,unique = true)
    @Email
    private String email;

    @OneToMany(mappedBy = "mentor",cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Course> courseList=new ArrayList<>();
}
