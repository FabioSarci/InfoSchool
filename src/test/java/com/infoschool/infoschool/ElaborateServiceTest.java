package com.infoschool.infoschool;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import com.infoschool.infoschool.dto.request.ElaborateRequestDto;
import com.infoschool.infoschool.dto.response.ElaborateResponseDto;
import com.infoschool.infoschool.mapper.ElaborateMapper;
import com.infoschool.infoschool.model.Elaborate;
import com.infoschool.infoschool.repository.ElaborateRepository;
import com.infoschool.infoschool.service.ElaborateService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class ElaborateServiceTest {

    @InjectMocks
    private ElaborateService elaborateService;

    @Mock
    private ElaborateRepository elaborateRepository;

    @Mock
    private ElaborateMapper elaborateMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddElaborate() {
        ElaborateRequestDto request = new ElaborateRequestDto();
        request.setTitle("Test Elaborate");

        Elaborate elaborate = new Elaborate();
        when(elaborateMapper.requestToEntity(request)).thenReturn(elaborate);
        when(elaborateRepository.save(elaborate)).thenReturn(elaborate);

        ElaborateResponseDto response = new ElaborateResponseDto();
        when(elaborateMapper.entityToResponse(elaborate)).thenReturn(response);

        ElaborateResponseDto result = elaborateService.add(request);

        assertNotNull(result);
        verify(elaborateRepository, times(1)).save(elaborate);
    }

    @Test
    void testGetById() {
        Elaborate elaborate = new Elaborate();
        elaborate.setId(1L);

        when(elaborateRepository.findById(1L)).thenReturn(Optional.of(elaborate));

        ElaborateResponseDto response = new ElaborateResponseDto();
        when(elaborateMapper.entityToResponse(elaborate)).thenReturn(response);

        ElaborateResponseDto result = elaborateService.getById(1L);

        assertNotNull(result);
        verify(elaborateRepository, times(1)).findById(1L);
    }
}