package com.infoschool.infoschool.dto.response;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeachedSubjectResponseDto {
    
    private Long id;
    private LocalDate startDate;
    private LocalDate endDate;
    private String teacherName;
    private String subjectName;
}
