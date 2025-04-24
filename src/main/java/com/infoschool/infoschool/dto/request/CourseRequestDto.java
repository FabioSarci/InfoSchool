package com.infoschool.infoschool.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseRequestDto {
    
    private Long id;
    private String code;
    private int year;
    private String name;
    private String description;
}
