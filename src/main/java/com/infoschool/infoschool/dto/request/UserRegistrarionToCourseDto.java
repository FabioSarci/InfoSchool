package com.infoschool.infoschool.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserRegistrarionToCourseDto {
    
    private long userId;
    private long courseId;
}
