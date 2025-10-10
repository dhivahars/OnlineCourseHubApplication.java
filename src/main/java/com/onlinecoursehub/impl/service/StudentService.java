package com.onlinecoursehub.impl.service;

import com.onlinecoursehub.impl.dto.StudentDto;
import com.onlinecoursehub.impl.model.Enrollment;
import com.onlinecoursehub.impl.model.Student;
import com.onlinecoursehub.impl.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {
    @Autowired
    StudentRepository studentRepository;

    public Object addStudent(Student s) {
        if (studentRepository.existsByEmail(s.getEmail())) {
            return "Mail already exists";
        }

        studentRepository.save(s);
        return entityToDto(s);
    }

    public List<StudentDto> getStudentsList() {
        return studentRepository.findAll().stream().map(StudentService::entityToDto).toList();
    }

    public Optional<Student> getStudentById(long id) {
        return Optional.ofNullable(studentRepository.findById(id).orElseThrow(() -> new RuntimeException("Student not found With Id:" + id)));
    }

    public Optional<Student> getStudentByName(String name) {
        return Optional.ofNullable(Optional.ofNullable(studentRepository.findByName(name)).orElseThrow(() -> new RuntimeException("Student with name \"" + name + "\" doesn't exist ")));
    }

    public String updateStudent(long id, Student s) {
        Student student = studentRepository.findById(id).orElseThrow(() -> new RuntimeException("Student not found with id " + s.getId()));

        if (s.getName() != null) student.setName(s.getName());
        if (s.getEmail() != null) student.setEmail(s.getEmail());

        studentRepository.save(student);
        return "Student updated successfully";
    }

    public String deleteStudentById(long id) {
        if (studentRepository.existsById(id)) {
            studentRepository.deleteById(id);
            return "Student Deleted Successfully";
        }

        return "Student Not Found";
    }

    public String deleteStudentByName(String name) {
        if (studentRepository.existsByName(name)) {
            studentRepository.deleteByName(name);
            return "Student Deleted Successfully";
        }

        return "Student Not Found";
    }

    public StudentDto entityToDto(Student student) {
        return new StudentDto(student.getName(), student.getEmail(), student.getEnrollments().stream().map(Enrollment::getCourse).map(a -> a.getTitle()).toList());
    }


}
