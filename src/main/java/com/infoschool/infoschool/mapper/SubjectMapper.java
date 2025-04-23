package com.infoschool.infoschool.mapper;

import org.springframework.stereotype.Component;

import com.infoschool.infoschool.dto.request.SubjectDto;
import com.infoschool.infoschool.dto.response.SubjectAssignedToCourseDto;
import com.infoschool.infoschool.model.Subject;

@Component
public class SubjectMapper {
    
    public SubjectDto subjectToDto(Subject subject) {
        if (subject == null) {
            return null;
        }
        SubjectDto subjectDTO = new SubjectDto();
        subjectDTO.setId(subject.getId());
        subjectDTO.setName(subject.getName());
        subjectDTO.setDescription(subject.getDescription());
        return subjectDTO;
    }

    public Subject dtoToSubject(SubjectDto subjectDTO) {
        if (subjectDTO == null) {
            return null;
        }
        Subject subject = new Subject();
        subject.setName(subjectDTO.getName());
        subject.setDescription(subjectDTO.getDescription());
        return subject;
    }

    public SubjectAssignedToCourseDto subjectToAssignedToCourseDto(Subject subject) {
        if (subject == null) {
            return null;
        }
        SubjectAssignedToCourseDto subjectAssignedToCourseDTO = new SubjectAssignedToCourseDto();
        subjectAssignedToCourseDTO.setId(subject.getId());
        subjectAssignedToCourseDTO.setName(subject.getName());
        subjectAssignedToCourseDTO.setDescription(subject.getDescription());
        return subjectAssignedToCourseDTO;
    }
}
