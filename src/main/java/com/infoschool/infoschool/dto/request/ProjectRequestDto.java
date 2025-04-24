package com.infoschool.infoschool.dto.request;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectRequestDto {
    
    private Long id;
    private LocalDate startDate;
    private LocalDate endDate;
    private String title;
    private String description;
    private int max_evaluation;
    private long courseId;

}