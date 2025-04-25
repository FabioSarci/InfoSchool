package com.infoschool.infoschool.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MailRequestDto {
    
    private String subject;
    private String message;
    private String senderEmail;
    private String receiverEmail;
    private String attachment;
    private Long attachmentTypeId;

    public MailRequestDto(String subject, String message, String senderEmail, String receiverEmail) {
        this.subject = subject;
        this.message = message;
        this.senderEmail = senderEmail;
        this.receiverEmail = receiverEmail;
    }
}
