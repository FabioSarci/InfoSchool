package com.infoschool.infoschool.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.infoschool.infoschool.model.TeachedSubject;
import com.infoschool.infoschool.repository.TeachedSubjectRepositiry;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class TeachedSubjectService {
    
    private final TeachedSubjectRepositiry teachedSubjectRepositiry;

    public TeachedSubjectService(TeachedSubjectRepositiry teachedSubjectRepositiry) {
        this.teachedSubjectRepositiry = teachedSubjectRepositiry;
    }

    public TeachedSubject add(TeachedSubject teachedSubject) {
        try {
            log.info("Adding TeachedSubject: {}", teachedSubject);
            if (teachedSubject.getStartDate() == null || teachedSubject.getEndDate() == null) {
                throw new IllegalArgumentException("Start date and end date cannot be null");
            }
            if (teachedSubject.getStartDate().isAfter(teachedSubject.getEndDate())) {
                throw new IllegalArgumentException("Start date cannot be after end date");
            }
            if (teachedSubject.getTeacher() == null || teachedSubject.getSubject() == null) {
                throw new IllegalArgumentException("Teacher and subject cannot be null");
            }
            return teachedSubjectRepositiry.save(teachedSubject);
        } catch (Exception e) {
            log.error("Error adding TeachedSubject: {}", e.getMessage());
            throw new RuntimeException("Error adding TeachedSubject", e);
        }
    }

    public TeachedSubject update(TeachedSubject teachedSubject) {
        try {
            log.info("Updating TeachedSubject: {}", teachedSubject);
            if (teachedSubject.getId() == null) {
                throw new IllegalArgumentException("ID cannot be null");
            }
            if (teachedSubject.getStartDate() == null || teachedSubject.getEndDate() == null) {
                throw new IllegalArgumentException("Start date and end date cannot be null");
            }
            if (teachedSubjectRepositiry.existsById(teachedSubject.getId())) {
                throw new IllegalArgumentException("TeachedSubject with this ID does not exist");
            }
            if (teachedSubject.getStartDate().isAfter(teachedSubject.getEndDate())) {
                throw new IllegalArgumentException("Start date cannot be after end date");
            }
            return teachedSubjectRepositiry.save(teachedSubject);
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

    public TeachedSubject findById(long id) {
        try {
            log.info("Finding TeachedSubject with ID: {}", id);
            return teachedSubjectRepositiry.findById(id).orElseThrow(() -> new IllegalArgumentException("TeachedSubject with this ID does not exist"));
        } catch (Exception e) {
            log.error("Error finding TeachedSubject: {}", e.getMessage());
            throw new RuntimeException("Error finding TeachedSubject", e);
        }
    }

    public List<TeachedSubject> findAll() {
        try {
            log.info("Finding all TeachedSubjects");
            return teachedSubjectRepositiry.findAll();
        } catch (Exception e) {
            log.error("Error finding all TeachedSubjects: {}", e.getMessage());
            throw new RuntimeException("Error finding all TeachedSubjects", e);
        }
    }

    public List<TeachedSubject> findByTeacherId(Long teacherId) {
        try {
            log.info("Finding TeachedSubjects by teacher ID: {}", teacherId);
            return teachedSubjectRepositiry.findByTeacherId(teacherId);
        } catch (Exception e) {
            log.error("Error finding TeachedSubjects by teacher ID: {}", e.getMessage());
            throw new RuntimeException("Error finding TeachedSubjects by teacher ID", e);
        }
    }

    public List<TeachedSubject> findBySubjectId(Long subjectId) {
        try {
            log.info("Finding TeachedSubjects by subject ID: {}", subjectId);
            return teachedSubjectRepositiry.findBySubjectId(subjectId);
        } catch (Exception e) {
            log.error("Error finding TeachedSubjects by subject ID: {}", e.getMessage());
            throw new RuntimeException("Error finding TeachedSubjects by subject ID", e);
        }
    }
}
