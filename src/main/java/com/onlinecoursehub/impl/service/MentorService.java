package com.onlinecoursehub.impl.service;

import com.onlinecoursehub.impl.dto.MentorDto;
import com.onlinecoursehub.impl.model.Course;
import com.onlinecoursehub.impl.model.Mentor;
import com.onlinecoursehub.impl.repository.CourseRepository;
import com.onlinecoursehub.impl.repository.MentorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class MentorService {

    @Autowired
    MentorRepository mentorRepository;

    @Autowired
    CourseRepository courseRepository;

    public MentorDto addMentor(Mentor mentor) {
        if (mentorRepository.existsByEmail(mentor.getEmail()))
            throw new RuntimeException("Mentor already Present");
        return entityToDto(mentorRepository.save(mentor));
    }

    public List<MentorDto> listMentor() {
        if(mentorRepository.findAll().isEmpty())
            throw new RuntimeException("No mentor found..........");
        return mentorRepository.findAll().stream().map(this::entityToDto).toList();
    }
    public Optional<Mentor> showById(long id) {
        return Optional.ofNullable((Mentor) mentorRepository.findById(id).orElseThrow(() -> new RuntimeException("Mentor not found With Id:" + id)));
    }

    public String updateMentor(long id, Mentor m) {
        Mentor existing = mentorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Mentor not found with id " + m.getId()));
        if (m.getName() != null)
            existing.setName(m.getName());
        if (m.getEmail() != null)
            existing.setEmail(m.getEmail());

        mentorRepository.save(existing);
        return "Mentor profile updated Successfully";
    }

    public String deleteMentorById(long id) {
        if (mentorRepository.existsById(id)) {
            mentorRepository.deleteById(id);
            return "Mentor Deleted Successfully";
        }
        throw new RuntimeException("Mentor Not Found");
    }
    public MentorDto entityToDto(Mentor m){
        return MentorDto.builder().name(m.getName()).email(m.getEmail()).id(m.getId())
        .build();
    }
    public Course createCourseWithMentor(Course course, String email) {
        Mentor mentor =  mentorRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Mentor not found with ID: " + email));

        course.setMentor(mentor);
        return courseRepository.save(course);
    }
    public String getAbout(String email) {
        Mentor mentor= mentorRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("Mentor not found with email:" + email));
        if(mentor.getAbout()==null){
            throw new RuntimeException("Mentor Not Found");}

        return mentor.getAbout();

    }

    public MentorDto getMentorByEmail(String email) {
        Mentor mentor=mentorRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("Mentor not found"));
        return this.entityToDto(mentor);
    }
}
