package com.onlinecoursehub.impl.service;

import com.onlinecoursehub.impl.dto.StudentDto;
import com.onlinecoursehub.impl.model.Enrollment;
import com.onlinecoursehub.impl.model.Student;
import com.onlinecoursehub.impl.repository.StudentRepository;
import com.onlinecoursehub.impl.utils.Badge;
import jakarta.validation.constraints.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {
    @Autowired
    StudentRepository studentRepository;

    public StudentDto addStudent(Student s) {
        if (studentRepository.existsByEmail(s.getEmail())) {
            throw new RuntimeException("Mail already exists");
        }

        studentRepository.save(s);
        return entityToDto(s);
    }

    public List<StudentDto> getStudentsList() {
        return studentRepository.findAll().stream().map(this::entityToDto).toList();
    }

    public String updateStudent(long id, Student s) {
        Student student = studentRepository.findById(id).orElseThrow(() -> new RuntimeException("Student not found with id " + id));

        if (s.getName() != null)
            student.setName(s.getName());
        if (s.getEmail() != null)
            student.setEmail(s.getEmail());
        if (s.getSkills() != null && !s.getSkills().isEmpty())
            student.getSkills().addAll(s.getSkills());

        studentRepository.save(student);
        return "Student updated successfully";
    }

    @Transactional
    public String deleteStudentById(long id) {
        if (studentRepository.existsById(id)) {
            studentRepository.deleteById(id);
            return "Student Deleted Successfully";
        }

        throw new RuntimeException("Student Not Found");
    }


    public StudentDto entityToDto(Student student) {
        return new StudentDto(student.getId(), student.getName(), student.getEnrollments().stream().map(Enrollment::getCourse).map(a -> a.getTitle()).toList());
    }

    public String studentBadges(long id) {
        if (!studentRepository.existsById(id))
            throw new RuntimeException("Student not found..............");
        List<String> badge = studentRepository.getById(id).getBadges().stream().map(Badge::getName).toList();
        if (badge.isEmpty())
            throw new RuntimeException("Student doesn't earned any badges..........");
        return "Badges earned by " + studentRepository.getById(id).getName() + ":\n" +
                badge;
    }

    public StudentDto searchStudent(Long id, String name, @Email String email) {
        if (id != null) {
            Student student = studentRepository.findById(id).orElseThrow(() -> new RuntimeException("Id not found"));
            return entityToDto(student);
        } else if (name != null && !name.isEmpty()) {
            Student student = studentRepository.findByName(name);
            if (student == null)
                throw new RuntimeException("student not found by name");
            return entityToDto(student);
        }
        else if (email != null && !email.isEmpty()) {
            Student student = studentRepository.findByEmail(email);
            if (student == null)
                throw new RuntimeException("Email not found.......");
            return entityToDto(student);
        }
        throw new RuntimeException("No input given........");
    }
}
