package com.infoschool.infoschool;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.infoschool.infoschool.dto.request.SubjectDto;
import com.infoschool.infoschool.mapper.SubjectMapper;
import com.infoschool.infoschool.model.Subject;
import com.infoschool.infoschool.repository.SubjectRepository;
import com.infoschool.infoschool.service.SubjectService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

class SubjectServiceTest {

    @InjectMocks
    private SubjectService subjectService;

    @Mock
    private SubjectRepository subjectRepository;

    @Mock
    private SubjectMapper subjectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddSubject() {
        SubjectDto subjectDto = new SubjectDto();
        subjectDto.setName("Math");

        Subject subject = new Subject();
        when(subjectMapper.dtoToSubject(subjectDto)).thenReturn(subject);
        when(subjectRepository.save(subject)).thenReturn(subject);

        SubjectDto response = new SubjectDto();
        when(subjectMapper.subjectToDto(subject)).thenReturn(response);

        SubjectDto result = subjectService.add(subjectDto);

        assertNotNull(result);
        verify(subjectRepository, times(1)).save(subject);
    }

    @Test
    void testFindById() {
        Subject subject = new Subject();
        subject.setId(1L);

        when(subjectRepository.findById(1L)).thenReturn(Optional.of(subject));

        SubjectDto response = new SubjectDto();
        when(subjectMapper.subjectToDto(subject)).thenReturn(response);

        SubjectDto result = subjectService.findById(1L);

        assertNotNull(result);
        verify(subjectRepository, times(1)).findById(1L);
    }
}