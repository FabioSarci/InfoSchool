package com.infoschool.infoschool.dto.request;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeachedSubjectRequestDto {
    
    private Long id;
    private Long subjectId;
    private Long teacherId;
    private LocalDate startDate;
    private LocalDate endDate;

    public TeachedSubjectRequestDto(Long subjectId, Long teacherId, LocalDate startDate, LocalDate endDate) {
        this.subjectId = subjectId;
        this.teacherId = teacherId;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
