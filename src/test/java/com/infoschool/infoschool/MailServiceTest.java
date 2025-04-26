package com.infoschool.infoschool;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.infoschool.infoschool.dto.request.MailRequestDto;
import com.infoschool.infoschool.dto.response.MailResponseDto;
import com.infoschool.infoschool.mapper.MailMapper;
import com.infoschool.infoschool.model.Mail;
import com.infoschool.infoschool.repository.MailRepository;
import com.infoschool.infoschool.service.MailService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class MailServiceTest {

    @InjectMocks
    private MailService mailService;

    @Mock
    private MailRepository mailRepository;

    @Mock
    private MailMapper mailMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSendMail() {
        MailRequestDto mailRequest = new MailRequestDto();
        mailRequest.setSubject("Test Mail");

        Mail mail = new Mail();
        when(mailMapper.requestToEntity(mailRequest)).thenReturn(mail);
        when(mailRepository.save(mail)).thenReturn(mail);

        MailResponseDto response = new MailResponseDto();
        when(mailMapper.entityToResponse(mail)).thenReturn(response);

        MailResponseDto result = mailService.send(mailRequest);

        assertNotNull(result);
        verify(mailRepository, times(1)).save(mail);
    }
}