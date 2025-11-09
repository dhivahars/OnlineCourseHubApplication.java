package com.onlinecoursehub.impl.service;

import com.onlinecoursehub.impl.dto.MentorDto;
import com.onlinecoursehub.impl.model.Mentor;
import com.onlinecoursehub.impl.repository.MentorRepository;
import com.onlinecoursehub.impl.repository.CourseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
class MentorServiceTest {

    @Mock
    private MentorRepository mentorRepository;

    @Mock
    private CourseRepository courseRepository;

    @InjectMocks
    private MentorService mentorService;

    private Mentor mentor;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mentor = Mentor.builder()
                .id(1L)
                .name("Leo")
                .email("Leo@gmail.com")
                .build();
    }

//    @Test
//    void testAddMentor_Success() {
//        when(mentorRepository.existsByEmail(mentor.getEmail())).thenReturn(false);
//        when(mentorRepository.save(mentor)).thenReturn(mentor);
//
//        Mentor result = mentorService.addMentor(mentor);
//
//        assertEquals(mentor, result);
//        verify(mentorRepository, times(1)).save(mentor);
//    }

    @Test
    void testAddMentor_AlreadyExists() {
        when(mentorRepository.existsByEmail(mentor.getEmail())).thenReturn(true);

        RuntimeException ex = assertThrows(RuntimeException.class, () -> mentorService.addMentor(mentor));
        assertEquals("Mentor already Present", ex.getMessage());
        verify(mentorRepository, never()).save(any());
    }

    @Test
    void testListMentor_Success() {
        List<Mentor> mentorList = List.of(mentor);
        when(mentorRepository.findAll()).thenReturn(mentorList);

        List<MentorDto> result = mentorService.listMentor();

        assertEquals(1, result.size());
        assertEquals("Leo", result.get(0).getName());
        verify(mentorRepository,times(2)).findAll();
    }

    @Test
    void testListMentor_NoMentorFound() {
        when(mentorRepository.findAll()).thenReturn(Collections.emptyList());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> mentorService.listMentor());
        assertEquals("No mentor found..........", ex.getMessage());
        verify(mentorRepository).findAll();
    }




    @Test
    void testShowById_Success() {
        when(mentorRepository.findById(1L)).thenReturn(Optional.of(mentor));

        Optional<Mentor> result = mentorService.showById(1L);

        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
        verify(mentorRepository, times(1)).findById(1L);
    }

    @Test
    void testShowById_NotFound() {
        when(mentorRepository.findById(99L)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> mentorService.showById(99L));
        assertTrue(ex.getMessage().contains("Mentor not found With Id"));
    }

    @Test
    void testUpdateMentor_Success() {
        Mentor updated = Mentor.builder().name("Updated").email("updated@gmail.com").build();
        when(mentorRepository.findById(1L)).thenReturn(Optional.of(mentor));
        when(mentorRepository.save(any(Mentor.class))).thenReturn(mentor);

        String result = mentorService.updateMentor(1L, updated);

        assertEquals("Mentor profile updated Successfully", result);
        verify(mentorRepository, times(1)).save(any(Mentor.class));
    }

    @Test
    void testUpdateMentor_NotFound() {
        when(mentorRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> mentorService.updateMentor(1L, mentor));
        assertTrue(ex.getMessage().contains("Mentor not found"));
    }

    @Test
    void testDeleteMentorById_Success() {
        when(mentorRepository.existsById(1L)).thenReturn(true);

        String result = mentorService.deleteMentorById(1L);

        assertEquals("Mentor Deleted Successfully", result);
        verify(mentorRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteMentorById_NotFound() {
        when(mentorRepository.existsById(1L)).thenReturn(false);

        RuntimeException ex = assertThrows(RuntimeException.class, () -> mentorService.deleteMentorById(1L));
        assertEquals("Mentor Not Found", ex.getMessage());
        verify(mentorRepository, never()).deleteById(anyLong());
    }

    @Test
    void testEntityToDto() {
        MentorDto dto = mentorService.entityToDto(mentor);

        assertEquals(mentor.getId(), dto.getId());
        assertEquals(mentor.getName(), dto.getName());
        assertEquals(mentor.getEmail(), dto.getEmail());
    }
}
