package com.infoschool.infoschool.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ElaborateRequestDto {
    
    private Long id;
    private String title;
    private String comment;
    private int evaluation;
    private Long projectId;
    private Long studentId;

    public ElaborateRequestDto(String title, Long projectId, Long studentId) {
        this.title = title;
        this.projectId = projectId;
        this.studentId = studentId;
    }

    public ElaborateRequestDto(int evaluation, String comment, Long projectId, Long studentId) {
        this.evaluation = evaluation;
        this.comment = comment;
    }

    public ElaborateRequestDto(Long id, String title, String comment, int evaluation) {
        this.id = id;
        this.title = title;
        this.comment = comment;
        this.evaluation = evaluation;
    }
}
