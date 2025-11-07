package com.onlinecoursehub.impl.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatusCode;

import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "User_table")
public class User {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long id;

    @Column(name = "username",nullable = false)
    private String name;

    @Column(name="email",nullable = false)
    @Email
    private String email;

    @Column(name="password",nullable = false)
    @Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!*()]).{8,}$")
    private String password;

    @Column(name = "role",nullable = false)
    private String role;

    @Column(name = "skills",nullable

            = true)
    private Set<String> skills=new HashSet<>();
}
