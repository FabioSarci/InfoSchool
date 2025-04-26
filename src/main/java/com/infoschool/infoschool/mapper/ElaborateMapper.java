package com.infoschool.infoschool.mapper;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.infoschool.infoschool.dto.request.ElaborateRequestDto;
import com.infoschool.infoschool.dto.response.ElaborateResponseDto;
import com.infoschool.infoschool.model.Elaborate;
import com.infoschool.infoschool.model.Project;
import com.infoschool.infoschool.model.User;
import com.infoschool.infoschool.repository.ProjectRepository;
import com.infoschool.infoschool.repository.UserRepository;

@Component
public class ElaborateMapper {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProjectRepository projectRepository;

    public Elaborate requestToEntity(ElaborateRequestDto elaborate) {

        
        Elaborate entity = new Elaborate();
        entity.setTitle(elaborate.getTitle());
        entity.setPostedAt(LocalDateTime.now());
        entity.setComment(elaborate.getComment());
        entity.setEvaluation(elaborate.getEvaluation());
        if (elaborate.getProjectId() != null) {
            Project project = projectRepository.findById(elaborate.getProjectId())
                .orElseThrow(() -> new RuntimeException("Project not found"));
            entity.setProject(project);
            
        }
        if (elaborate.getStudentId() != null) {
            User student = userRepository.findById(elaborate.getStudentId())
                .orElseThrow(() -> new RuntimeException("User not found"));
            entity.setStudent(student);
        }
        return entity;
    }

    public ElaborateResponseDto entityToResponse(Elaborate elaborate) {
        ElaborateResponseDto response = new ElaborateResponseDto();
        response.setId(elaborate.getId());
        response.setTitle(elaborate.getTitle());
        response.setPostedAt(elaborate.getPostedAt().toString());
        response.setComment(elaborate.getComment());
        response.setEvaluation(elaborate.getEvaluation());
        response.setProjectId(elaborate.getProject().getId());
        response.setProjectTitle(elaborate.getProject().getTitle());
        response.setStudentId(elaborate.getStudent().getId());
        response.setStudentName(elaborate.getStudent().getName());
        return response;
    }
}
