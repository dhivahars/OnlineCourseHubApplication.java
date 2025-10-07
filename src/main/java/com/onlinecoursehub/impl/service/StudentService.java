package com.onlinecoursehub.impl.service;

import com.onlinecoursehub.impl.model.Student;
import com.onlinecoursehub.impl.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentService {
    @Autowired
    StudentRepository studentRepository;
    public Student addStudent(Student s) {
       return studentRepository.save(s);
    }
}
