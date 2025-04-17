package com.infoschool.infoschool.service;

import org.springframework.stereotype.Service;

import com.infoschool.infoschool.model.Elaborate;
import com.infoschool.infoschool.repository.ElaborateRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ElaborateService {
    
    private final ElaborateRepository elaborateRepository;

    public ElaborateService(ElaborateRepository elaborateRepository) {
        this.elaborateRepository = elaborateRepository;
    }

    public Elaborate add(Elaborate elaborate) {
        try {
            log.info("Adding new elaborate: {}", elaborate);
            return elaborateRepository.save(elaborate);
        } catch (Exception e) {
            log.error("Error adding elaborate: {}", elaborate, e);
            throw new RuntimeException("Error adding elaborate: " + e.getMessage(), e);
        }
    }

    public Elaborate edit(Elaborate elaborate) {
        try {
            log.info("Editing elaborate: {}", elaborate);
            if (elaborateRepository.existsById(elaborate.getId())) {
                log.error("Elaborate with ID {} already exists", elaborate.getId());
                throw new RuntimeException("Elaborate with this ID already exists");
            }
            return elaborateRepository.save(elaborate);
        } catch (Exception e) {
            log.error("Error editing elaborate: {}", elaborate, e);
            throw new RuntimeException("Error editing elaborate: " + e.getMessage(), e);
        }
    }

    public Elaborate getById(Long id) {
        try {
            log.info("Fetching elaborate by ID: {}", id);
            return elaborateRepository.findById(id).orElse(null);
        } catch (Exception e) {
            log.error("Error fetching elaborate by ID: {}", id, e);
            throw new RuntimeException("Error fetching elaborate by ID: " + e.getMessage(), e);
        }
    }

    public void deleteById(Long id) {
        try {
            log.info("Deleting elaborate by ID: {}", id);
            elaborateRepository.deleteById(id);
        } catch (Exception e) {
            log.error("Error deleting elaborate by ID: {}", id, e);
            throw new RuntimeException("Error deleting elaborate by ID: " + e.getMessage(), e);
        }
    }
}
