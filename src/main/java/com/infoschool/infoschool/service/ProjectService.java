package com.infoschool.infoschool.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.infoschool.infoschool.model.Project;
import com.infoschool.infoschool.repository.ProjectRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ProjectService {
    
    private final ProjectRepository projectRepository;

    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public Project add(Project project) {
        try {
            log.info("Adding new project: {}", project);
            return projectRepository.save(project);
        } catch (Exception e) {
            log.error("Error adding project: {}", project, e);
            throw new RuntimeException("Error adding project: " + e.getMessage(), e);
        }
    }

    public Project edit(Project project){

        try {
            log.info("Editing project: {}", project);
            if (projectRepository.existsById(project.getId())) {
                log.error("Project with ID {} already exists", project.getId());
                throw new RuntimeException("Project with this ID already exists");
            }
            return projectRepository.save(project);
        } catch (Exception e) {
            log.error("Error editing project: {}", project, e);
            throw new RuntimeException("Error editing project: " + e.getMessage(), e);
        }
    }

    public Project getById(Long id) {
        try {
            log.info("Fetching project by ID: {}", id);
            return projectRepository.findById(id).orElse(null);
        } catch (Exception e) {
            log.error("Error fetching project by ID: {}", id, e);
            throw new RuntimeException("Error fetching project by ID: " + e.getMessage(), e);
        }
    }

    public void deleteById(Long id) {
        try {
            log.info("Deleting project by ID: {}", id);
            projectRepository.deleteById(id);
        } catch (Exception e) {
            log.error("Error deleting project by ID: {}", id, e);
            throw new RuntimeException("Error deleting project by ID: " + e.getMessage(), e);
        }
    }

    public List<Project> getByCourseId(Long courseId) {
        try {
            log.info("Fetching projects by course ID: {}", courseId);
            return projectRepository.findByCourseId(courseId);
        } catch (Exception e) {
            log.error("Error fetching projects by course ID: {}", courseId, e);
            throw new RuntimeException("Error fetching projects by course ID: " + e.getMessage(), e);
        }
    }
}
