package com.infoschool.infoschool.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infoschool.infoschool.dto.request.ProjectRequestDto;
import com.infoschool.infoschool.dto.response.ProjectResponseDto;
import com.infoschool.infoschool.mapper.ProjectMapper;
import com.infoschool.infoschool.model.Project;
import com.infoschool.infoschool.repository.ProjectRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ProjectService {
    
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private ProjectMapper projectMapper;

    public ProjectResponseDto add(ProjectRequestDto project) {
        try {
            log.info("Adding new project: {}", project);
            Project project2create = projectMapper.projectDtotoProject(project);
            projectRepository.save(project2create);
            log.info("Project created successfully: {}", project2create);
            return projectMapper.projectToProjectDto(project2create);
        } catch (Exception e) {
            log.error("Error adding project: {}", project, e);
            throw new RuntimeException("Error adding project: " + e.getMessage(), e);
        }
    }

    public ProjectResponseDto edit(ProjectRequestDto project){

        try {
            log.info("Editing project: {}", project);
            Project project2edit = projectMapper.projectDtotoProject(project);
            project2edit.setId(project.getId());
            
            projectRepository.save(project2edit);
            log.info("Project edited successfully: {}", project2edit);
            return projectMapper.projectToProjectDto(project2edit);
        } catch (Exception e) {
            log.error("Error editing project: {}", project, e);
            throw new RuntimeException("Error editing project: " + e.getMessage(), e);
        }
    }

    public ProjectResponseDto getById(Long id) {
        try {
            log.info("Fetching project by ID: {}", id);
            Project project = projectRepository.findById(id).orElse(null);
            if (project == null) {
                log.error("Project not found with ID: {}", id);
                throw new RuntimeException("Project not found with ID: " + id);
            }
            log.info("Project fetched successfully: {}", project);
            return projectMapper.projectToProjectDto(project);
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

    public List<ProjectResponseDto> getByCourseId(Long courseId) {
        try {
            log.info("Fetching projects by course ID: {}", courseId);
            List<Project> courses = projectRepository.findByCourseId(courseId);
            if (courses.isEmpty()) {
                log.error("No projects found for course ID: {}", courseId);
                throw new RuntimeException("No projects found for course ID: " + courseId);
            }
            List<ProjectResponseDto> projectDtos = courses.stream()
                    .map(projectMapper::projectToProjectDto)
                    .toList();
            log.info("Projects fetched successfully for course ID: {}", courseId);
            return projectDtos;
        } catch (Exception e) {
            log.error("Error fetching projects by course ID: {}", courseId, e);
            throw new RuntimeException("Error fetching projects by course ID: " + e.getMessage(), e);
        }
    }
}
