package com.infoschool.infoschool.service;

import org.springframework.stereotype.Service;

import com.infoschool.infoschool.model.Certification;
import com.infoschool.infoschool.repository.CertificationRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CertificationService {
    
    private final CertificationRepository certificationRepository;

    public CertificationService(CertificationRepository certificationRepository) {
        this.certificationRepository = certificationRepository;
    }

    public Certification add(Certification certification) {
        try {
            log.info("Adding new certification: {}", certification);
            if (certificationRepository.existsByName(certification.getName())) {
                log.error("Certification with name {} already exists", certification.getName());
                throw new RuntimeException("Certification with this name already exists");
            }
            return certificationRepository.save(certification);
        } catch (Exception e) {
            log.error("Error adding certification: {}", certification, e);
            throw new RuntimeException("Error adding certification: " + e.getMessage(), e);
        }
    }

    public Certification getById(Long id) {
        try {
            log.info("Fetching certification by ID: {}", id);
            return certificationRepository.findById(id).orElse(null);
        } catch (Exception e) {
            log.error("Error fetching certification by ID: {}", id, e);
            throw new RuntimeException("Error fetching certification by ID: " + e.getMessage(), e);
        }
    }

    public Certification getByName(String name) {
        try {
            log.info("Fetching certification by name: {}", name);
            return certificationRepository.findByName(name).orElse(null);
        } catch (Exception e) {
            log.error("Error fetching certification by name: {}", name, e);
            throw new RuntimeException("Error fetching certification by name: " + e.getMessage(), e);
        }
    }

    public Certification edit(Certification certification) {
        try {
            log.info("Editing certification: {}", certification);
            if (certificationRepository.existsByName(certification.getName())) {
                log.error("Certification with name {} already exists", certification.getName());
                throw new RuntimeException("Certification with this name already exists");
            }
            if(certification.getId() == null) {
                log.error("Certification ID is null");
                throw new RuntimeException("Certification ID cannot be null");
            }
            if(!certificationRepository.existsById(certification.getId())) {
                log.error("Certification with ID {} does not exist", certification.getId());
                throw new RuntimeException("Certification with this ID does not exist");
            }
            return certificationRepository.save(certification);
        } catch (Exception e) {
            log.error("Error editing certification: {}", certification, e);
            throw new RuntimeException("Error editing certification: " + e.getMessage(), e);
        }
    }

    public void deleteById(Long id) {
        try {
            log.info("Deleting certification by ID: {}", id);
            if(!certificationRepository.existsById(id)) {
                log.error("Certification with ID {} does not exist", id);
                throw new RuntimeException("Certification with this ID does not exist");
            }
            certificationRepository.deleteById(id);
        } catch (Exception e) {
            log.error("Error deleting certification by ID: {}", id, e);
            throw new RuntimeException("Error deleting certification by ID: " + e.getMessage(), e);
        }
    }
}
