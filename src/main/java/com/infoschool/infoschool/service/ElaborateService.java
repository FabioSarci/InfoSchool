package com.infoschool.infoschool.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infoschool.infoschool.dto.request.ElaborateRequestDto;
import com.infoschool.infoschool.dto.response.ElaborateResponseDto;
import com.infoschool.infoschool.mapper.ElaborateMapper;
import com.infoschool.infoschool.model.Elaborate;
import com.infoschool.infoschool.repository.ElaborateRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ElaborateService {
    
    @Autowired
    private ElaborateRepository elaborateRepository;
    @Autowired
    private ElaborateMapper elaborateMapper;

    public ElaborateResponseDto add(ElaborateRequestDto elaborate) {
        try {
            log.info("Adding new elaborate: {}", elaborate);
            Elaborate elaborateEntity = elaborateMapper.requestToEntity(elaborate);
            elaborateRepository.save(elaborateEntity);
            log.info("Elaborate added successfully: {}", elaborateEntity);
            return elaborateMapper.entityToResponse(elaborateEntity);
        } catch (Exception e) {
            log.error("Error adding elaborate: {}", elaborate, e);
            throw new RuntimeException("Error adding elaborate: " + e.getMessage(), e);
        }
    }

    public ElaborateResponseDto edit(ElaborateRequestDto elaborate) {
        try {
            log.info("Editing elaborate: {}", elaborate);

            Elaborate elaborateEntity = elaborateMapper.requestToEntity(elaborate);
            elaborateEntity.setId(elaborate.getId());
            if (elaborateEntity.getId() == null) {
                log.error("Elaborate ID is null");
                throw new RuntimeException("Elaborate ID cannot be null");
            }
            Elaborate existingElaborate = elaborateRepository.findById(elaborateEntity.getId()).orElse(null);
            if (elaborateEntity.getProject() != null) {
                existingElaborate.setProject(elaborateEntity.getProject());   
            }
            if (elaborateEntity.getStudent() != null) {
                existingElaborate.setStudent(elaborateEntity.getStudent());   
            }
            if (elaborateEntity.getTitle() != null) {
                existingElaborate.setTitle(elaborateEntity.getTitle());   
            }
            if (elaborateEntity.getComment() != null) {
                existingElaborate.setComment(elaborateEntity.getComment());   
            }
            if (elaborateEntity.getEvaluation() != 0) {
                existingElaborate.setEvaluation(elaborateEntity.getEvaluation());   
            }
            elaborateRepository.save(existingElaborate);
            log.info("Elaborate edited successfully: {}", elaborateEntity);
            return elaborateMapper.entityToResponse(existingElaborate);
        } catch (Exception e) {
            log.error("Error editing elaborate: {}", elaborate, e);
            throw new RuntimeException("Error editing elaborate: " + e.getMessage(), e);
        }
    }

    public ElaborateResponseDto getById(Long id) {
        try {
            log.info("Fetching elaborate by ID: {}", id);
            Elaborate elaborate = elaborateRepository.findById(id).orElse(null);
            if (elaborate == null) {
                log.error("Elaborate with ID {} not found", id);
                throw new RuntimeException("Elaborate not found");
            }
            log.info("Elaborate fetched successfully: {}", elaborate);
            return elaborateMapper.entityToResponse(elaborate);
        } catch (Exception e) {
            log.error("Error fetching elaborate by ID: {}", id, e);
            throw new RuntimeException("Error fetching elaborate by ID: " + e.getMessage(), e);
        }
    }

    public void deleteById(Long id) {
        try {
            log.info("Deleting elaborate by ID: {}", id);
            if (!elaborateRepository.existsById(id)) {
                log.error("Elaborate with ID {} not found", id);
                throw new RuntimeException("Elaborate not found");
            }
            elaborateRepository.deleteById(id);
        } catch (Exception e) {
            log.error("Error deleting elaborate by ID: {}", id, e);
            throw new RuntimeException("Error deleting elaborate by ID: " + e.getMessage(), e);
        }
    }
}
