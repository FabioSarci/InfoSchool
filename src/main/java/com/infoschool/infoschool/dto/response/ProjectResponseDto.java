package com.infoschool.infoschool.dto.response;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectResponseDto {
    
    private Long id;
    private LocalDate startDate;
    private LocalDate endDate;

    private String title;
    private String description;
    private int max_evaluation;

    private Long courseId;
    private String courseName;
    private String courseDescription;
    private String courseCode;
    private int courseYear;
}
