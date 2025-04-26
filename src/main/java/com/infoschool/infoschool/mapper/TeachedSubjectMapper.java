package com.infoschool.infoschool.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.infoschool.infoschool.dto.request.TeachedSubjectRequestDto;
import com.infoschool.infoschool.dto.response.TeachedSubjectResponseDto;
import com.infoschool.infoschool.model.TeachedSubject;
import com.infoschool.infoschool.repository.SubjectRepository;
import com.infoschool.infoschool.repository.UserRepository;

@Component
public class TeachedSubjectMapper {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SubjectRepository subjectRepository;
    
    public TeachedSubject toEntity(TeachedSubjectRequestDto teachedSubjectRequestDto) {

        TeachedSubject teachedSubject = new TeachedSubject();
        teachedSubject.setStartDate(teachedSubjectRequestDto.getStartDate());
        teachedSubject.setEndDate(teachedSubjectRequestDto.getEndDate());

        teachedSubject.setTeacher(userRepository.findById(teachedSubjectRequestDto.getTeacherId()).orElseThrow(() -> new RuntimeException("User not found")));
        teachedSubject.setSubject(subjectRepository.findById(teachedSubjectRequestDto.getSubjectId()).orElseThrow(() -> new RuntimeException("Subject not found")));

        System.out.println("TEACHED SUBJECT MAPPER: " + teachedSubject.toString());
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
