package com.onlinecoursehub.impl.service;

import com.onlinecoursehub.impl.model.Mentor;
import com.onlinecoursehub.impl.model.Student;
import com.onlinecoursehub.impl.repository.MentorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MentorService {

    @Autowired
    MentorRepository mentorRepository;

    public String addMentor(Mentor mentor) {
        if(mentorRepository.existsByEmail(mentor.getEmail()))
            return "Mentor already Present";
        mentorRepository.save(mentor);
        return "Mentor added successfully";
    }
    public List<Mentor> listMentor() {
        return mentorRepository.findAll();
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
        if(m.getName() != null)
            existing.setName(m.getName());
        if(m.getEmail() != null)
            existing.setEmail(m.getEmail());

        mentorRepository.save(existing);
        return "Mentor profile updated Successfully";
    }

    public String deleteMentorById(long id) {
        if(mentorRepository.existsById(id)){
            mentorRepository.deleteById(id);
            return "Mentor Deleted Successfully";}
        return "Mentor Not Found";
    }
}
