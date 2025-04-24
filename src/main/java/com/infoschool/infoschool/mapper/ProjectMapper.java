package com.infoschool.infoschool.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.infoschool.infoschool.dto.request.ProjectRequestDto;
import com.infoschool.infoschool.dto.response.ProjectResponseDto;
import com.infoschool.infoschool.model.Project;
import com.infoschool.infoschool.repository.CourseRepository;

@Component
public class ProjectMapper {

    @Autowired
    private CourseRepository courseRepository;
    
    public ProjectResponseDto projectToProjectDto(Project project) {
        if (project == null) {
            return null;
        }

        ProjectResponseDto projectDto = new ProjectResponseDto();
        projectDto.setId(project.getId());
        projectDto.setTitle(project.getTitle());
        projectDto.setDescription(project.getDescription());
        projectDto.setStartDate(project.getStartDate());
        projectDto.setEndDate(project.getEndDate());
        projectDto.setMax_evaluation(project.getMax_evaluation());
        projectDto.setCourseId(project.getCourse().getId());
        projectDto.setCourseName(project.getCourse().getName());
        projectDto.setCourseDescription(project.getCourse().getDescription());
        projectDto.setCourseCode(project.getCourse().getCode());
        projectDto.setCourseYear(project.getCourse().getYear());

        return projectDto;
    }

    public Project projectDtotoProject(ProjectRequestDto projectDto) {
        if (projectDto == null) {
            return null;
        }

        Project project = new Project();
        project.setTitle(projectDto.getTitle());
        project.setDescription(projectDto.getDescription());
        project.setStartDate(projectDto.getStartDate());
        project.setEndDate(projectDto.getEndDate());
        project.setMax_evaluation(projectDto.getMax_evaluation());

        project.setCourse(courseRepository.findById(projectDto.getCourseId()).orElse(null));
        if (project.getCourse() == null) {
            throw new RuntimeException("Course not found with ID: " + projectDto.getCourseId());
        }

        return project;
    }
}
