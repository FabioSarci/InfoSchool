package com.infoschool.infoschool.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infoschool.infoschool.dto.request.MailRequestDto;
import com.infoschool.infoschool.dto.response.MailResponseDto;
import com.infoschool.infoschool.mapper.MailMapper;
import com.infoschool.infoschool.model.Mail;
import com.infoschool.infoschool.repository.MailRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MailService {
    
    @Autowired
    private MailRepository mailRepository;
    @Autowired
    private MailMapper mailMapper;

    public MailResponseDto send(MailRequestDto mail) {
        try {
            log.info("Sending mail: {}", mail);
            Mail mailEntity = mailMapper.requestToEntity(mail);
            mailRepository.save(mailEntity);
            log.info("Mail sent successfully: {}", mailEntity);
            return mailMapper.entityToResponse(mailEntity);
        } catch (Exception e) {
            log.error("Error sending mail: {}", mail, e);
            throw new RuntimeException("Error sending mail: " + e.getMessage(), e);
        }
    }

    public MailResponseDto getById(Long id) {
        try {
            log.info("Fetching mail by ID: {}", id);
            Mail mail = mailRepository.findById(id).orElse(null);
            if (mail == null) {
                log.warn("Mail not found for ID: {}", id);
                return null;
            }
            log.info("Mail fetched successfully: {}", mail);
            return mailMapper.entityToResponse(mail);
        } catch (Exception e) {
            log.error("Error fetching mail by ID: {}", id, e);
            throw new RuntimeException("Error fetching mail by ID: " + e.getMessage(), e);
        }
    }

    public void deleteById(Long id) {
        try {
            log.info("Deleting mail by ID: {}", id);
            mailRepository.deleteById(id);
        } catch (Exception e) {
            log.error("Error deleting mail by ID: {}", id, e);
            throw new RuntimeException("Error deleting mail by ID: " + e.getMessage(), e);
        }
    }

    public List<MailResponseDto> getBySenderId(Long senderId) {
        try {
            log.info("Fetching mails by sender ID: {}", senderId);
            List<Mail> mails =  mailRepository.findBySenderId(senderId);
            if (mails.isEmpty()) {
                log.warn("No mails found for sender ID: {}", senderId);
                return List.of();
            }
            log.info("Mails fetched successfully for sender ID: {}", senderId);
            return mails.stream()
                    .map(mailMapper::entityToResponse)
                    .toList();
        } catch (Exception e) {
            log.error("Error fetching mails by sender ID: {}", senderId, e);
            throw new RuntimeException("Error fetching mails by sender ID: " + e.getMessage(), e);
        }
    }

    public List<MailResponseDto> getByReceiverId(Long receiverId) {
        try {
            log.info("Fetching mails by receiver ID: {}", receiverId);
            List<Mail> mails =  mailRepository.findByReceiverId(receiverId);
            if (mails.isEmpty()) {
                log.warn("No mails found for receiver ID: {}", receiverId);
                return List.of();
            }
            log.info("Mails fetched successfully for receiver ID: {}", receiverId);
            return mails.stream()
                    .map(mailMapper::entityToResponse)
                    .toList();
        } catch (Exception e) {
            log.error("Error fetching mails by receiver ID: {}", receiverId, e);
            throw new RuntimeException("Error fetching mails by receiver ID: " + e.getMessage(), e);
        }
    }

    public List<MailResponseDto> getAll() {
        try {
            log.info("Fetching all mails");
            List<Mail> mails = mailRepository.findAll();
            if (mails.isEmpty()) {
                log.warn("No mails found");
                return List.of();
            }
            log.info("All mails fetched successfully");
            return mails.stream()
                    .map(mailMapper::entityToResponse)
                    .toList();
        } catch (Exception e) {
            log.error("Error fetching all mails", e);
            throw new RuntimeException("Error fetching all mails: " + e.getMessage(), e);
        }
    }

    public List<MailResponseDto> getBySenderIdAndReceiverId(Long senderId, Long receiverId) {
        try {
            log.info("Fetching mails by sender ID: {} and receiver ID: {}", senderId, receiverId);
            List<Mail> mails = mailRepository.findBySenderIdAndReceiverId(senderId, receiverId);
            if (mails.isEmpty()) {
                log.warn("No mails found for sender ID: {} and receiver ID: {}", senderId, receiverId);
                return List.of();
            }
            log.info("Mails fetched successfully for sender ID: {} and receiver ID: {}", senderId, receiverId);
            return mails.stream()
                    .map(mailMapper::entityToResponse)
                    .toList();
        } catch (Exception e) {
            log.error("Error fetching mails by sender ID: {} and receiver ID: {}", senderId, receiverId, e);
            throw new RuntimeException("Error fetching mails by sender ID and receiver ID: " + e.getMessage(), e);
        }
    }
}
