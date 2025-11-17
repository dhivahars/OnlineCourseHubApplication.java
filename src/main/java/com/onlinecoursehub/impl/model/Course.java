package com.onlinecoursehub.impl.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_id")
    private long id;

    @NotBlank
    @Column(name = "title",nullable = false)
    private String title;

    @Column(name ="description",nullable = false)
    private String description;

    @Column(name="capacity")
    private int capacity;


    @ManyToOne
    @JoinColumn(name = "mentor_id", nullable = false)
    @ToString.Exclude
    @JsonIgnore
    private Mentor mentor;



    @OneToMany(mappedBy = "course",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JsonIgnoreProperties("student")
    @ToString.Exclude
    private List<Enrollment> enrollments=new ArrayList<>();


//    @ManyToMany
//    @JoinTable(
//            name = "course_prerequisite",
//            joinColumns = @JoinColumn(name = "course_id"),
//            inverseJoinColumns = @JoinColumn(name = "prerequisite_course_id")
//    )
//    @JsonIgnore
    private Set<String> prerequisites=new HashSet<>();

    @Column(name="skill")
    private String skill;

    @JoinColumn(name = "thumbnail")
    private String url;
}
