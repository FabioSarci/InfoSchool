package com.infoschool.infoschool.model;

import java.time.LocalDateTime;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Mail {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String subject;
    private String message;
    private LocalDateTime sentAt;

    private String attachment;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.SET_NULL)
    private User sender;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User receiver;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.SET_NULL)
    private AttachmentType attachmentType;

}
