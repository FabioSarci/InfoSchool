package com.infoschool.infoschool.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infoschool.infoschool.dto.request.SubjectDto;
import com.infoschool.infoschool.dto.response.SubjectAssignedToCourseDto;
import com.infoschool.infoschool.mapper.SubjectMapper;
import com.infoschool.infoschool.model.Course;
import com.infoschool.infoschool.model.Subject;
import com.infoschool.infoschool.repository.CourseRepository;
import com.infoschool.infoschool.repository.SubjectRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SubjectService {
    
    @Autowired
    private SubjectRepository subjectRepository;
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private SubjectMapper mapper;

    public SubjectDto add(SubjectDto subject) {
        try {
            log.info("Adding subject: {}", subject.getName());
            if (subject.getName() == null || subject.getName().isEmpty()) {
                throw new IllegalArgumentException("Subject name cannot be null or empty");
            }
    
            Subject subject2Save = mapper.dtoToSubject(subject);
            if (subjectRepository.findByName(subject2Save.getName()).isPresent()) {
                log.error("Subject with name {} already exists", subject2Save.getName());
                throw new RuntimeException("Subject with this name already exists");
            }
            log.info(subject2Save.toString());
            Subject savedSubject = subjectRepository.save(subject2Save);
            log.info("Subject saved with ID: {}", savedSubject.getId());
            return mapper.subjectToDto(savedSubject);
        } catch (Exception e) {
            log.error("Error adding subject: {}", e.getMessage(), e);
            throw new RuntimeException("Error adding subject", e);
        }
    }

    public SubjectDto update(SubjectDto subject) {
        try {
            log.info("Updating subject: {}", subject.getName());
            Subject subject2Update = subjectRepository.findById(subject.getId()).orElse(null);
            if (subject2Update == null) {
                log.error("Subject with ID {} not found", subject.getId());
                throw new RuntimeException("Subject not found");
            }
            if (subject.getName() == null || subject.getName().isEmpty()) {
                throw new IllegalArgumentException("Subject name cannot be null or empty");
            }
            subject2Update.setName(subject.getName());
            subject2Update.setDescription(subject.getDescription());
            subjectRepository.save(subject2Update);
            return mapper.subjectToDto(subject2Update);
        } catch (Exception e) {
            log.error("Error updating subject: {}", e.getMessage());
            throw new RuntimeException("Error updating subject", e);
        }
    }

    public void deleteById(Long id) {
        try {
            log.info("Deleting subject with ID: {}", id);
            if (id == null) {
                throw new IllegalArgumentException("Subject ID cannot be null");
            }
            Subject subject = subjectRepository.findById(id).orElse(null);
            if (subject == null) {
                log.error("Subject with ID {} not found", id);
                throw new RuntimeException("Subject not found");
            }
            subjectRepository.delete(subject);
            log.info("Subject with ID {} deleted successfully", id);
        } catch (IllegalArgumentException e) {
            log.error("Error deleting subject: {}", e.getMessage());
            throw new IllegalArgumentException("Error deleting subject", e);
        }
    }

    public SubjectDto findById(Long id) {
        try {
            log.info("Finding subject with ID: {}", id);
            if (id == null) {
                throw new IllegalArgumentException("Subject ID cannot be null");
            }
            Subject subject = subjectRepository.findById(id).orElse(null);
            return mapper.subjectToDto(subject);
        } catch (Exception e) {
            log.error("Error finding subject: {}", e.getMessage());
            throw new RuntimeException("Error finding subject", e);
        }
    }

    public List<SubjectDto> findAll() {
        try {
            log.info("Finding all subjects");
            List<Subject> subjects = subjectRepository.findAll();
            return subjects.stream()
                .map(mapper::subjectToDto)
                .toList();

        } catch (Exception e) {
            log.error("Error finding all subjects: {}", e.getMessage());
            throw new RuntimeException("Error finding all subjects", e);
        }
    }

    public SubjectDto findByName(String name) {
        try {
            log.info("Finding subject with name: {}", name);
            if (name == null || name.isEmpty()) {
                throw new IllegalArgumentException("Subject name cannot be null or empty");
            }
            Subject subject = subjectRepository.findByName(name).orElse(null);
            return mapper.subjectToDto(subject);
        } catch (Exception e) {
            log.error("Error finding subject by name: {}", e.getMessage());
            throw new RuntimeException("Error finding subject by name", e);
        }
    }

    public SubjectAssignedToCourseDto assignSubjectToCourse(Long subjectId, Long courseId) {
        try {
            log.info("Assigning subject with ID: {} to course with ID: {}", subjectId, courseId);
            if (subjectId == null || courseId == null) {
                throw new IllegalArgumentException("Subject ID and Course ID cannot be null");
            }
            Subject subject = subjectRepository.findById(subjectId).orElse(null);
            if (subject == null) {
                throw new RuntimeException("Subject not found");
            }
            Course course = courseRepository.findById(courseId).orElse(null);
            if (course == null) {
                throw new RuntimeException("Course not found");
            }
            log.info(course.toString());
            course.getSubjects().add(subject);
            courseRepository.save(course);
            SubjectAssignedToCourseDto dto = new SubjectAssignedToCourseDto();
            dto = mapper.subjectToAssignedToCourseDto(subject);
            dto.setCourseId(course.getId());
            dto.setCourseYear(course.getYear());
            dto.setCourseName(course.getName());
            dto.setCourseCode(course.getCode());
            dto.setCourseDescription(course.getDescription());
            log.info("Successfully assigned subject to course: {}", dto);
            return dto;
        } catch (Exception e) {
            log.error("Error assigning subject to course: {}", e.getMessage());
            throw new RuntimeException("Error assigning subject to course", e);
        }
    }
}
