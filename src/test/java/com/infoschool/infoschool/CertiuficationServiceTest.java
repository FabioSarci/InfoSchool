package com.infoschool.infoschool;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.infoschool.infoschool.model.Certification;
import com.infoschool.infoschool.repository.CertificationRepository;
import com.infoschool.infoschool.service.CertificationService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class CertificationServiceTest {

    @InjectMocks
    private CertificationService certificationService;

    @Mock
    private CertificationRepository certificationRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddCertification() {
        Certification certification = new Certification();
        certification.setName("Java Certification");

        when(certificationRepository.save(certification)).thenReturn(certification);

        Certification result = certificationService.add(certification);

        assertNotNull(result);
        verify(certificationRepository, times(1)).save(certification);
    }

    @Test
    void testGetById() {
        Certification certification = new Certification();
        certification.setId(1L);
        certification.setName("Java Certification");

        when(certificationRepository.findById(1L)).thenReturn(java.util.Optional.of(certification));

        Certification result = certificationService.getById(1L);

        assertNotNull(result);
        assertEquals("Java Certification", result.getName());
        verify(certificationRepository, times(1)).findById(1L);
    }
}