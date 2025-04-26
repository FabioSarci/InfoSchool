package com.infoschool.infoschool;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.infoschool.infoschool.dto.request.ProjectRequestDto;
import com.infoschool.infoschool.dto.response.ProjectResponseDto;
import com.infoschool.infoschool.mapper.ProjectMapper;
import com.infoschool.infoschool.model.Project;
import com.infoschool.infoschool.repository.ProjectRepository;
import com.infoschool.infoschool.service.ProjectService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class ProjectServiceTest {

    @InjectMocks
    private ProjectService projectService;

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private ProjectMapper projectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddProject() {
        ProjectRequestDto projectRequest = new ProjectRequestDto();
        projectRequest.setTitle("Test Project");

        Project project = new Project();
        when(projectMapper.projectDtotoProject(projectRequest)).thenReturn(project);
        when(projectRepository.save(project)).thenReturn(project);

        ProjectResponseDto response = new ProjectResponseDto();
        when(projectMapper.projectToProjectDto(project)).thenReturn(response);

        ProjectResponseDto result = projectService.add(projectRequest);

        assertNotNull(result);
        verify(projectRepository, times(1)).save(project);
    }
}