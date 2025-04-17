package com.infoschool.infoschool.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.infoschool.infoschool.model.Mail;

public interface MailRepository extends JpaRepository<Mail, Long> {
    
    List<Mail> findBySenderId(Long senderId);
    List<Mail> findByReceiverId(Long receiverId);

    List<Mail> findBySenderIdAndReceiverId(Long senderId, Long receiverId);
    
}
