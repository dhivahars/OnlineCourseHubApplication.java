package com.onlinecoursehub.impl.service;

import com.onlinecoursehub.impl.dto.MentorDto;
import com.onlinecoursehub.impl.model.Course;
import com.onlinecoursehub.impl.model.Mentor;
import com.onlinecoursehub.impl.repository.CourseRepository;
import com.onlinecoursehub.impl.repository.MentorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MentorService {

    @Autowired
    MentorRepository mentorRepository;

    @Autowired
    CourseRepository courseRepository;

    public String addMentor(Mentor mentor) {
        if (mentorRepository.existsByEmail(mentor.getEmail()))
            throw new RuntimeException("Mentor already Present");
        mentorRepository.save(mentor);
        return "Mentor added successfully";
    }

    public List<MentorDto> listMentor() {
        if(mentorRepository.findAll().isEmpty())
            throw new RuntimeException("No mentor found..........");
        return mentorRepository.findAll().stream().map(this::entityToDto).toList();
    }

    public Optional<Mentor> showByName(String name) {
        return Optional.ofNullable((Mentor) mentorRepository.findByName(name).orElseThrow(() -> new RuntimeException("Mentor not found With Name:" + name)));
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
        return MentorDto.builder().name(m.getName()).email(m.getEmail()).build();
    }

//    public String assignCourseById(long mentor_id, long course_id) {
//       Mentor mentor= mentorRepository.findById(mentor_id).get();
//       Course course=courseRepository.findById(course_id).get();
//        if(course.getMentor() == null){
//       mentor.getCourseList().add(course);
//       course.setMentor(mentor);
//       courseRepository.save(course);
//       mentorRepository.save(mentor);
//       return "Assigned course for mentor successfully";
//        }
//        return "course already has a mentor";
//    }
}
