package com.infoschool.infoschool.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseResponseDto {
    
    private Long id;
    private String code;
    private int year;
    private String name;
    private String description;
}
