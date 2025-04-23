package com.infoschool.infoschool.mapper;

import org.springframework.stereotype.Component;

import com.infoschool.infoschool.dto.request.TeachedSubjectRequestDto;
import com.infoschool.infoschool.dto.response.TeachedSubjectResponseDto;
import com.infoschool.infoschool.model.TeachedSubject;

@Component
public class TeachedSubjectMapper {
    
    public TeachedSubject toEntity(TeachedSubjectRequestDto teachedSubjectRequestDto) {
        TeachedSubject teachedSubject = new TeachedSubject();
        teachedSubject.setStartDate(teachedSubjectRequestDto.getStartDate());
        teachedSubject.setEndDate(teachedSubjectRequestDto.getEndDate());

        return teachedSubject;
    }

    public TeachedSubjectResponseDto toDto(TeachedSubject teachedSubject) {
        TeachedSubjectResponseDto teachedSubjectResponseDto = new TeachedSubjectResponseDto();
        teachedSubjectResponseDto.setId(teachedSubject.getId());
        teachedSubjectResponseDto.setStartDate(teachedSubject.getStartDate());
        teachedSubjectResponseDto.setEndDate(teachedSubject.getEndDate());

        return teachedSubjectResponseDto;
    }
}
