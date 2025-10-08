package com.onlinecoursehub.impl.model;

import jakarta.persistence.*;
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

    @Column(name = "title",nullable = false)
    private String title;

    @Column(name ="description",nullable = false)
    private String description;

    @Column(name="capacity",nullable = false)
    private int capacity;

    @ManyToOne
    @JoinColumn(name = "mentor_id",nullable = false)
    private Mentor mentor;


    @OneToMany(mappedBy = "course",cascade = CascadeType.ALL)
    private List<Enrollment> enrollments=new ArrayList<>();


    @ManyToMany
    @JoinTable(
            name = "course_prerequisite",
            joinColumns = @JoinColumn(name = "course_id"),
            inverseJoinColumns = @JoinColumn(name = "prerequisite_course_id")
    )
    private Set<Course> prerequisites=new HashSet<>();
}
