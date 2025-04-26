package com.infoschool.infoschool.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ElaborateResponseDto {
    
    private Long id;
    private String title;
    private String postedAt;
    private String comment;
    private int evaluation;
    private Long projectId;
    private String projectTitle;
    private Long studentId;
    private String studentName;
}
