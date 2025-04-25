package com.infoschool.infoschool.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MailResponseDto {
    
    private Long id;
    private String subject;
    private String message;
    private String sentAt;

    private Long senderId;
    private String senderEmail;
    private String senderName;
    private String senderSurname;
    private Long receiverId;
    private String receiverEmail;
    private String receiverName;
    private String receiverSurname;

    private String attachment;
    private Long attachmentTypeId;
    private String attachmentTypeName;
}
