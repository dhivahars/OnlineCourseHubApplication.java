package com.onlinecoursehub.impl.controller;

import static org.junit.jupiter.api.Assertions.*;

import com.onlinecoursehub.impl.dto.MentorDto;
import com.onlinecoursehub.impl.model.Mentor;
import com.onlinecoursehub.impl.service.MentorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MentorControllerTest {

    @Mock
    private MentorService mentorService;

    @InjectMocks
    private MentorController mentorController;

    private Mentor mentor;
    private MentorDto mentorDto;

    @BeforeEach
    void setUp() {
        mentor = new Mentor();
        mentor.setId(1L);
        mentor.setName("Satoru");
        mentor.setEmail("satoru@gmail.com");


        mentorDto = new MentorDto();
        mentorDto.setId(1L);
        mentorDto.setName("Satoru");
        mentorDto.setEmail("satoru@gmail.com");
    }

    @Test
    void testCreateMentor() {
        when(mentorService.addMentor(mentor)).thenReturn(mentorDto);

        ResponseEntity<MentorDto> response = mentorController.createMentor(mentor);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Mentor Created", response.getBody());
        verify(mentorService, times(1)).addMentor(mentor);
    }

    @Test
    void testShowMentorList() {
        when(mentorService.listMentor()).thenReturn(List.of(mentorDto));

        ResponseEntity<List<MentorDto>> response = mentorController.ShowMentorList();

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals("Satoru", response.getBody().get(0).getName());
        verify(mentorService, times(1)).listMentor();
    }


    @Test
    void testListById() {
        when(mentorService.showById(1L)).thenReturn(Optional.of(mentor));

        ResponseEntity<Mentor> response = mentorController.listById(1L);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(1L, response.getBody().getId());
        verify(mentorService, times(1)).showById(1L);
    }

    @Test
    void testUpdateMentor() {
        when(mentorService.updateMentor(1L, mentor)).thenReturn("Mentor Updated");

        ResponseEntity<String> response = mentorController.updateMentor(1L, mentor);

        assertEquals("Mentor Updated", response.getBody());
        verify(mentorService, times(1)).updateMentor(1L, mentor);
    }

    @Test
    void testDeleteMentorById() {
        when(mentorService.deleteMentorById(1L)).thenReturn("Mentor Deleted");

        ResponseEntity<String> response = mentorController.deleteMentorById(1L);

        assertEquals("Mentor Deleted", response.getBody());
        verify(mentorService, times(1)).deleteMentorById(1L);
    }
}
