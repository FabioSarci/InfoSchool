package com.infoschool.infoschool.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.infoschool.infoschool.model.Subject;
import com.infoschool.infoschool.repository.SubjectRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SubjectService {
    
    private final SubjectRepository subjectRepository;

    public SubjectService(SubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }

    public Subject add(Subject subject) {
        try {
            log.info("Adding subject: {}", subject.getName());
            if (subject.getName() == null || subject.getName().isEmpty()) {
                throw new IllegalArgumentException("Subject name cannot be null or empty");
            }
            return subjectRepository.save(subject);
        } catch (Exception e) {
            log.error("Error adding subject: {}", e.getMessage());
            throw new RuntimeException("Error adding subject", e);
        }
    }

    public Subject update(Subject subject) {
        try {
            log.info("Updating subject: {}", subject.getName());
            if (subject.getId() == null) {
                throw new IllegalArgumentException("Subject ID cannot be null");
            }
            return subjectRepository.save(subject);
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
            subjectRepository.deleteById(id);
        } catch (Exception e) {
            log.error("Error deleting subject: {}", e.getMessage());
            throw new RuntimeException("Error deleting subject", e);
        }
    }

    public Subject findById(Long id) {
        try {
            log.info("Finding subject with ID: {}", id);
            if (id == null) {
                throw new IllegalArgumentException("Subject ID cannot be null");
            }
            return subjectRepository.findById(id).orElse(null);
        } catch (Exception e) {
            log.error("Error finding subject: {}", e.getMessage());
            throw new RuntimeException("Error finding subject", e);
        }
    }

    public List<Subject> findAll() {
        try {
            log.info("Finding all subjects");
            return subjectRepository.findAll();
        } catch (Exception e) {
            log.error("Error finding all subjects: {}", e.getMessage());
            throw new RuntimeException("Error finding all subjects", e);
        }
    }

    public Subject findByName(String name) {
        try {
            log.info("Finding subject with name: {}", name);
            if (name == null || name.isEmpty()) {
                throw new IllegalArgumentException("Subject name cannot be null or empty");
            }
            return subjectRepository.findByName(name).orElse(null);
        } catch (Exception e) {
            log.error("Error finding subject by name: {}", e.getMessage());
            throw new RuntimeException("Error finding subject by name", e);
        }
    }
}
