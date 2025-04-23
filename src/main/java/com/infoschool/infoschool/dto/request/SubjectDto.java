package com.infoschool.infoschool.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubjectDto {
    
    private long id;
    private String name;
    private String description;

    public SubjectDto(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
