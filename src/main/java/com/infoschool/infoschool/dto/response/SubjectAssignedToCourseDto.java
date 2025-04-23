package com.infoschool.infoschool.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubjectAssignedToCourseDto {
    
    private Long id;
    private String name;
    private String description;
    private long courseId;
    private int courseYear;
    private String courseName;
    private String courseCode;
    private String courseDescription;
}
