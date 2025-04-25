package com.infoschool.infoschool.mapper;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.infoschool.infoschool.dto.request.MailRequestDto;
import com.infoschool.infoschool.dto.response.MailResponseDto;
import com.infoschool.infoschool.model.AttachmentType;
import com.infoschool.infoschool.model.Mail;
import com.infoschool.infoschool.model.User;
import com.infoschool.infoschool.repository.AttachmentTypeRepository;
import com.infoschool.infoschool.repository.UserRepository;

@Component
public class MailMapper {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AttachmentTypeRepository attachmentTypeRepository;
    
    public Mail requestToEntity(MailRequestDto mailRequestDto) {

        User sender = userRepository.findByEmail(mailRequestDto.getSenderEmail())
                .orElseThrow(() -> new RuntimeException("Sender not found"));
        User receiver = userRepository.findByEmail(mailRequestDto.getReceiverEmail())
                .orElseThrow(() -> new RuntimeException("Receiver not found"));

        Mail mail = new Mail();
        mail.setSubject(mailRequestDto.getSubject());
        mail.setMessage(mailRequestDto.getMessage());
        mail.setSender(sender);
        mail.setReceiver(receiver);
        mail.setSentAt(LocalDateTime.now());
        mail.setAttachment(mailRequestDto.getAttachment());

        if(mailRequestDto.getAttachmentTypeId() != null) {
            AttachmentType attachmentType = attachmentTypeRepository.findById(mailRequestDto.getAttachmentTypeId())
                    .orElseThrow(() -> new RuntimeException("Attachment type not found"));
                    
            mail.setAttachmentType(attachmentType);
        }

        return mail;
    }

    public MailResponseDto entityToResponse(Mail mail) {
        MailResponseDto response = new MailResponseDto();
        response.setId(mail.getId());
        response.setSubject(mail.getSubject());
        response.setMessage(mail.getMessage());
        response.setSentAt(mail.getSentAt().toString());

        if (mail.getSender() != null) {
            response.setSenderId(mail.getSender().getId());
            response.setSenderEmail(mail.getSender().getEmail());
            response.setSenderName(mail.getSender().getName());
            response.setSenderSurname(mail.getSender().getSurname());
        }

        if (mail.getReceiver() != null) {
            response.setReceiverId(mail.getReceiver().getId());
            response.setReceiverEmail(mail.getReceiver().getEmail());
            response.setReceiverName(mail.getReceiver().getName());
            response.setReceiverSurname(mail.getReceiver().getSurname());
        }

        if (mail.getAttachmentType() != null) {
            response.setAttachmentTypeId(mail.getAttachmentType().getId());
            response.setAttachmentTypeName(mail.getAttachmentType().getName());
        }

        return response;
    }
}
