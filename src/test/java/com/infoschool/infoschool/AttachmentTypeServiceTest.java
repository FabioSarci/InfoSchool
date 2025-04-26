package com.infoschool.infoschool;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import com.infoschool.infoschool.model.AttachmentType;
import com.infoschool.infoschool.repository.AttachmentTypeRepository;
import com.infoschool.infoschool.service.AttachmentTypeService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class AttachmentTypeServiceTest {

    @InjectMocks
    private AttachmentTypeService attachmentTypeService;

    @Mock
    private AttachmentTypeRepository attachmentTypeRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddAttachmentType() {
        AttachmentType attachmentType = new AttachmentType();
        attachmentType.setName("PDF");

        when(attachmentTypeRepository.save(attachmentType)).thenReturn(attachmentType);

        AttachmentType result = attachmentTypeService.add(attachmentType);

        assertNotNull(result);
        verify(attachmentTypeRepository, times(1)).save(attachmentType);
    }

    @Test
    void testGetById() {
        AttachmentType attachmentType = new AttachmentType();
        attachmentType.setId(1L);
        attachmentType.setName("PDF");

        when(attachmentTypeRepository.findById(1L)).thenReturn(Optional.of(attachmentType));

        AttachmentType result = attachmentTypeService.getById(1L);

        assertNotNull(result);
        assertEquals("PDF", result.getName());
        verify(attachmentTypeRepository, times(1)).findById(1L);
    }
}