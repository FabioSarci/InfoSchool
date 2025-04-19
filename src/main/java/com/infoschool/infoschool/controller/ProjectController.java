package com.infoschool.infoschool.controller;

import com.infoschool.infoschool.dto.response.MessageResponse;
import com.infoschool.infoschool.model.Project;
import com.infoschool.infoschool.service.ProjectService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
@Tag(name = "Projects", description = "API per la gestione dei progetti")
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @Operation(summary = "Aggiungi un nuovo progetto")
    @PreAuthorize("hasRole('ADMIN') or hasRole('TEACHER')")
    @PostMapping
    public ResponseEntity<?> addProject(@RequestBody Project project) {
        try {
            Project createdProject = projectService.add(project);
            return ResponseEntity.status(201).body(createdProject);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }

    @Operation(summary = "Ottieni un progetto per ID")
    @PreAuthorize("hasRole('ADMIN') or hasRole('TEACHER') or hasRole('USER')")
    @GetMapping("/{id}")
    public ResponseEntity<?> getProjectById(@PathVariable Long id) {
        try {
            Project project = projectService.getById(id);
            if (project == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(project);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }

    @Operation(summary = "Modifica un progetto")
    @PreAuthorize("hasRole('ADMIN') or hasRole('TEACHER')")
    @PutMapping
    public ResponseEntity<?> editProject(@RequestBody Project project) {
        try {
            Project updatedProject = projectService.edit(project);
            return ResponseEntity.ok(updatedProject);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }

    @Operation(summary = "Elimina un progetto per ID")
    @PreAuthorize("hasRole('ADMIN') or hasRole('TEACHER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProjectById(@PathVariable Long id) {
        try {
            projectService.deleteById(id);
            return ResponseEntity.ok(new MessageResponse("Progetto eliminato con successo"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }

    @Operation(summary = "Ottieni tutti i progetti associati a un corso")
    @PreAuthorize("hasRole('ADMIN') or hasRole('TEACHER') or hasRole('USER')")
    @GetMapping("/course/{courseId}")
    public ResponseEntity<?> getProjectsByCourseId(@PathVariable Long courseId) {
        try {
            List<Project> projects = projectService.getByCourseId(courseId);
            return ResponseEntity.ok(projects);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }
}