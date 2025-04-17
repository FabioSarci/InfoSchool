package com.infoschool.infoschool.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.infoschool.infoschool.model.Mail;
import com.infoschool.infoschool.repository.MailRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MailService {
    
    private final MailRepository mailRepository;

    public MailService(MailRepository mailRepository) {
        this.mailRepository = mailRepository;
    }

    public Mail send(Mail mail) {
        try {
            log.info("Sending mail: {}", mail);
            return mailRepository.save(mail);
        } catch (Exception e) {
            log.error("Error sending mail: {}", mail, e);
            throw new RuntimeException("Error sending mail: " + e.getMessage(), e);
        }
    }

    public Mail getById(Long id) {
        try {
            log.info("Fetching mail by ID: {}", id);
            return mailRepository.findById(id).orElse(null);
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

    public List<Mail> getBySenderId(Long senderId) {
        try {
            log.info("Fetching mails by sender ID: {}", senderId);
            return mailRepository.findBySenderId(senderId);
        } catch (Exception e) {
            log.error("Error fetching mails by sender ID: {}", senderId, e);
            throw new RuntimeException("Error fetching mails by sender ID: " + e.getMessage(), e);
        }
    }

    public List<Mail> getByReceiverId(Long receiverId) {
        try {
            log.info("Fetching mails by receiver ID: {}", receiverId);
            return mailRepository.findByReceiverId(receiverId);
        } catch (Exception e) {
            log.error("Error fetching mails by receiver ID: {}", receiverId, e);
            throw new RuntimeException("Error fetching mails by receiver ID: " + e.getMessage(), e);
        }
    }

    public List<Mail> getAll() {
        try {
            log.info("Fetching all mails");
            return mailRepository.findAll();
        } catch (Exception e) {
            log.error("Error fetching all mails", e);
            throw new RuntimeException("Error fetching all mails: " + e.getMessage(), e);
        }
    }

    public List<Mail> getBySenderIdAndReceiverId(Long senderId, Long receiverId) {
        try {
            log.info("Fetching mails by sender ID: {} and receiver ID: {}", senderId, receiverId);
            return mailRepository.findBySenderIdAndReceiverId(senderId, receiverId);
        } catch (Exception e) {
            log.error("Error fetching mails by sender ID: {} and receiver ID: {}", senderId, receiverId, e);
            throw new RuntimeException("Error fetching mails by sender ID and receiver ID: " + e.getMessage(), e);
        }
    }
}
