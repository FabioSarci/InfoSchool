package com.infoschool.infoschool.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infoschool.infoschool.dto.request.TeachedSubjectRequestDto;
import com.infoschool.infoschool.dto.response.TeachedSubjectResponseDto;
import com.infoschool.infoschool.mapper.TeachedSubjectMapper;
import com.infoschool.infoschool.model.Subject;
import com.infoschool.infoschool.model.TeachedSubject;
import com.infoschool.infoschool.model.User;
import com.infoschool.infoschool.repository.SubjectRepository;
import com.infoschool.infoschool.repository.TeachedSubjectRepositiry;
import com.infoschool.infoschool.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class TeachedSubjectService {
    
    @Autowired
    private TeachedSubjectRepositiry teachedSubjectRepositiry;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private TeachedSubjectMapper teachedSubjectMapper;

    public TeachedSubjectResponseDto add(TeachedSubjectRequestDto teachedSubject) {
        try {
            log.info("Adding TeachedSubject: {}", teachedSubject);
            if (teachedSubject.getStartDate() == null || teachedSubject.getEndDate() == null) {
                throw new IllegalArgumentException("Start date and end date cannot be null");
            }
            if (teachedSubject.getStartDate().isAfter(teachedSubject.getEndDate())) {
                throw new IllegalArgumentException("Start date cannot be after end date");
            }
            User teacher = userRepository.findById(teachedSubject.getTeacherId())
                    .orElseThrow(() -> new IllegalArgumentException("Teacher with this ID does not exist"));

            Subject subject = subjectRepository.findById(teachedSubject.getSubjectId())
                    .orElseThrow(() -> new IllegalArgumentException("Subject with this ID does not exist"));

            TeachedSubject newTeachedSubject = new TeachedSubject();
            newTeachedSubject = teachedSubjectMapper.toEntity(teachedSubject);
            newTeachedSubject.setTeacher(teacher);
            newTeachedSubject.setSubject(subject);
            
            teachedSubjectRepositiry.save(newTeachedSubject);

            TeachedSubjectResponseDto responseDto = teachedSubjectMapper.toDto(newTeachedSubject);
            responseDto.setTeacherName(teacher.getName() + " " + teacher.getSurname());
            responseDto.setSubjectName(subject.getName());
            return responseDto;
        } catch (Exception e) {
            log.error("Error adding TeachedSubject: {}", e.getMessage());
            throw new RuntimeException("Error adding TeachedSubject", e);
        }
    }

    public TeachedSubjectResponseDto update(TeachedSubjectRequestDto teachedSubject) {
        try {
            log.info("Updating TeachedSubject: {}", teachedSubject);

            if (teachedSubject.getStartDate() == null || teachedSubject.getEndDate() == null) {
                throw new IllegalArgumentException("Start date and end date cannot be null");
            }
            if (teachedSubject.getStartDate().isAfter(teachedSubject.getEndDate())) {
                throw new IllegalArgumentException("Start date cannot be after end date");
            }
            User teacher = userRepository.findById(teachedSubject.getTeacherId())
            .orElseThrow(() -> new IllegalArgumentException("Teacher with this ID does not exist"));
    
            Subject subject = subjectRepository.findById(teachedSubject.getSubjectId())
            .orElseThrow(() -> new IllegalArgumentException("Subject with this ID does not exist"));

            TeachedSubject existingTeachedSubject = teachedSubjectRepositiry.findById(teachedSubject.getId())
            .orElseThrow(() -> new IllegalArgumentException("TeachedSubject with this ID does not exist"));
            
            existingTeachedSubject.setStartDate(teachedSubject.getStartDate());
            existingTeachedSubject.setEndDate(teachedSubject.getEndDate());
            existingTeachedSubject.setTeacher(teacher);
            existingTeachedSubject.setSubject(subject);

            teachedSubjectRepositiry.save(existingTeachedSubject);

            TeachedSubjectResponseDto responseDto = teachedSubjectMapper.toDto(existingTeachedSubject);
            responseDto.setSubjectName(subject.getName());
            responseDto.setTeacherName(teacher.getName() + " " + teacher.getSurname());
            return responseDto;
        } catch (Exception e) {
            log.error("Error updating TeachedSubject: {}", e.getMessage());
            throw new RuntimeException("Error updating TeachedSubject", e);
        }
    }

    public void deleteById(long id) {
        try {
            log.info("Deleting TeachedSubject with ID: {}", id);
            if (!teachedSubjectRepositiry.existsById(id)) {
                throw new IllegalArgumentException("TeachedSubject with this ID does not exist");
            }
            teachedSubjectRepositiry.deleteById(id);
        } catch (Exception e) {
            log.error("Error deleting TeachedSubject: {}", e.getMessage());
            throw new RuntimeException("Error deleting TeachedSubject", e);
        }
    }

    public TeachedSubjectResponseDto findById(long id) {
        try {
            log.info("Finding TeachedSubject with ID: {}", id);
            TeachedSubject teachedSubject = teachedSubjectRepositiry.findById(id).orElseThrow(() -> new IllegalArgumentException("TeachedSubject with this ID does not exist"));
            TeachedSubjectResponseDto responseDto = teachedSubjectMapper.toDto(teachedSubject);
            responseDto.setTeacherName(teachedSubject.getTeacher().getName() + " " + teachedSubject.getTeacher().getSurname());
            responseDto.setSubjectName(teachedSubject.getSubject().getName());
            return responseDto;
        } catch (Exception e) {
            log.error("Error finding TeachedSubject: {}", e.getMessage());
            throw new RuntimeException("Error finding TeachedSubject", e);
        }
    }

    public List<TeachedSubjectResponseDto> findAll() {
        try {
            log.info("Finding all TeachedSubjects");
            List<TeachedSubject> list = teachedSubjectRepositiry.findAll();
            List<TeachedSubjectResponseDto> responseList = list.stream()
                    .map(teachedSubject -> {
                        TeachedSubjectResponseDto responseDto = teachedSubjectMapper.toDto(teachedSubject);
                        responseDto.setTeacherName(teachedSubject.getTeacher().getName() + " " + teachedSubject.getTeacher().getSurname());
                        responseDto.setSubjectName(teachedSubject.getSubject().getName());
                        return responseDto;
                    })
                    .toList();
            return responseList;
        } catch (Exception e) {
            log.error("Error finding all TeachedSubjects: {}", e.getMessage());
            throw new RuntimeException("Error finding all TeachedSubjects", e);
        }
    }

    public List<TeachedSubjectResponseDto> findByTeacherId(Long teacherId) {
        try {
            log.info("Finding TeachedSubjects by teacher ID: {}", teacherId);
            List<TeachedSubject> list = teachedSubjectRepositiry.findByTeacherId(teacherId);
            List<TeachedSubjectResponseDto> responseList = list.stream()
                    .map(teachedSubject -> {
                        TeachedSubjectResponseDto responseDto = teachedSubjectMapper.toDto(teachedSubject);
                        responseDto.setTeacherName(teachedSubject.getTeacher().getName() + " " + teachedSubject.getTeacher().getSurname());
                        responseDto.setSubjectName(teachedSubject.getSubject().getName());
                        return responseDto;
                    })
                    .toList();
            return responseList;
        } catch (Exception e) {
            log.error("Error finding TeachedSubjects by teacher ID: {}", e.getMessage());
            throw new RuntimeException("Error finding TeachedSubjects by teacher ID", e);
        }
    }

    public List<TeachedSubjectResponseDto> findBySubjectId(Long subjectId) {
        try {
            log.info("Finding TeachedSubjects by subject ID: {}", subjectId);
            List<TeachedSubject> list = teachedSubjectRepositiry.findBySubjectId(subjectId);
            List<TeachedSubjectResponseDto> responseList = list.stream()
                    .map(teachedSubject -> {
                        TeachedSubjectResponseDto responseDto = teachedSubjectMapper.toDto(teachedSubject);
                        responseDto.setTeacherName(teachedSubject.getTeacher().getName() + " " + teachedSubject.getTeacher().getSurname());
                        responseDto.setSubjectName(teachedSubject.getSubject().getName());
                        return responseDto;
                    })
                    .toList();
            return responseList;
        } catch (Exception e) {
            log.error("Error finding TeachedSubjects by subject ID: {}", e.getMessage());
            throw new RuntimeException("Error finding TeachedSubjects by subject ID", e);
        }
    }
}
