package com.infoschool.infoschool.service;

import org.springframework.stereotype.Service;

import com.infoschool.infoschool.model.AttachmentType;
import com.infoschool.infoschool.repository.AttachmentTypeRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AttachmentTypeService {
    
    private final AttachmentTypeRepository attachmentTypeRepository;

    public AttachmentTypeService(AttachmentTypeRepository attachmentTypeRepository) {
        this.attachmentTypeRepository = attachmentTypeRepository;
    }

    
    public AttachmentType add(AttachmentType attachmentType) {
        try{
            log.info("Adding new attachment type: {}", attachmentType);
            if (attachmentTypeRepository.existsByName(attachmentType.getName())) {
                log.error("Attachment type with name {} already exists", attachmentType.getName());
                throw new RuntimeException("Attachment type with this name already exists");
            }
            return attachmentTypeRepository.save(attachmentType);
        } catch (Exception e) {
            log.error("Error adding attachment type: {}", attachmentType, e);
            throw new RuntimeException("Error adding attachment type: " + e.getMessage(), e);
        }
    }

    public AttachmentType getById(Long id) {
        try {
            log.info("Fetching attachment type by ID: {}", id);
            return attachmentTypeRepository.findById(id).orElse(null);
        } catch (Exception e) {
            log.error("Error fetching attachment type by ID: {}", id, e);
            throw new RuntimeException("Error fetching attachment type by ID: " + e.getMessage(), e);
        }
    }

    public AttachmentType getByName(String name) {
        try {
            log.info("Fetching attachment type by name: {}", name);
            return attachmentTypeRepository.findByName(name).orElse(null);
        } catch (Exception e) {
            log.error("Error fetching attachment type by name: {}", name, e);
            throw new RuntimeException("Error fetching attachment type by name: " + e.getMessage(), e);
        }
    }

    public AttachmentType edit(AttachmentType attachmentType) {
        try {
            log.info("Editing attachment type: {}", attachmentType);
            if (attachmentTypeRepository.existsByName(attachmentType.getName())) {
                log.error("Attachment type with name {} already exists", attachmentType.getName());
                throw new RuntimeException("Attachment type with this name already exists");
            }
            return attachmentTypeRepository.save(attachmentType);
        } catch (Exception e) {
            log.error("Error editing attachment type: {}", attachmentType, e);
            throw new RuntimeException("Error editing attachment type: " + e.getMessage(), e);
        }
    }

    public void deleteById(Long id) {
        try {
            log.info("Deleting attachment type by ID: {}", id);
            attachmentTypeRepository.deleteById(id);
        } catch (Exception e) {
            log.error("Error deleting attachment type by ID: {}", id, e);
            throw new RuntimeException("Error deleting attachment type by ID: " + e.getMessage(), e);
        }
    }


}
