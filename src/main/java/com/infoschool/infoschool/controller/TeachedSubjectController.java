package com.infoschool.infoschool.controller;

import com.infoschool.infoschool.dto.request.TeachedSubjectRequestDto;
import com.infoschool.infoschool.dto.response.MessageResponse;
import com.infoschool.infoschool.dto.response.TeachedSubjectResponseDto;
import com.infoschool.infoschool.service.TeachedSubjectService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/teached-subjects")
@Tag(name = "Teached Subjects", description = "API per la gestione delle materie insegnate")
public class TeachedSubjectController {

    private final TeachedSubjectService teachedSubjectService;

    public TeachedSubjectController(TeachedSubjectService teachedSubjectService) {
        this.teachedSubjectService = teachedSubjectService;
    }

    @Operation(summary = "Aggiungi una nuova materia insegnata")
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<?> addTeachedSubject(@RequestBody TeachedSubjectRequestDto teachedSubject) {
        try {
            TeachedSubjectResponseDto createdTeachedSubject = teachedSubjectService.add(teachedSubject);
            return ResponseEntity.status(201).body(createdTeachedSubject);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }

    @Operation(summary = "Modifica una materia insegnata")
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping
    public ResponseEntity<?> updateTeachedSubject(@RequestBody TeachedSubjectRequestDto teachedSubject) {
        try {
            TeachedSubjectResponseDto updatedTeachedSubject = teachedSubjectService.update(teachedSubject);
            return ResponseEntity.ok(updatedTeachedSubject);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }

    @Operation(summary = "Elimina una materia insegnata per ID")
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTeachedSubjectById(@PathVariable Long id) {
        try {
            teachedSubjectService.deleteById(id);
            return ResponseEntity.ok(new MessageResponse("Materia insegnata eliminata con successo"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }

    @Operation(summary = "Ottieni una materia insegnata per ID")
    @PreAuthorize("hasRole('ADMIN') or hasRole('TEACHER') or hasRole('USER')")
    @GetMapping("/{id}")
    public ResponseEntity<?> findTeachedSubjectById(@PathVariable Long id) {
        try {
            TeachedSubjectResponseDto teachedSubject = teachedSubjectService.findById(id);
            return ResponseEntity.ok(teachedSubject);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }

    @Operation(summary = "Ottieni tutte le materie insegnate")
    @PreAuthorize("hasRole('ADMIN') or hasRole('TEACHER') or hasRole('USER')")
    @GetMapping
    public ResponseEntity<?> findAllTeachedSubjects() {
        try {
            List<TeachedSubjectResponseDto> teachedSubjects = teachedSubjectService.findAll();
            return ResponseEntity.ok(teachedSubjects);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }

    @Operation(summary = "Ottieni tutte le materie insegnate da un insegnante")
    @PreAuthorize("hasRole('ADMIN') or hasRole('TEACHER') or hasRole('USER')")
    @GetMapping("/teacher/{teacherId}")
    public ResponseEntity<?> findTeachedSubjectsByTeacherId(@PathVariable Long teacherId) {
        try {
            List<TeachedSubjectResponseDto> teachedSubjects = teachedSubjectService.findByTeacherId(teacherId);
            return ResponseEntity.ok(teachedSubjects);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }

    @Operation(summary = "Ottieni tutte le materie insegnate per ID della materia")
    @PreAuthorize("hasRole('ADMIN') or hasRole('TEACHER') or hasRole('USER')")
    @GetMapping("/subject/{subjectId}")
    public ResponseEntity<?> findTeachedSubjectsBySubjectId(@PathVariable Long subjectId) {
        try {
            List<TeachedSubjectResponseDto> teachedSubjects = teachedSubjectService.findBySubjectId(subjectId);
            return ResponseEntity.ok(teachedSubjects);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }
}