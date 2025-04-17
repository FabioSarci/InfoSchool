package com.infoschool.infoschool.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.infoschool.infoschool.model.Mail;

public interface MailReposiroty extends JpaRepository<Mail, Long> {
    
}
