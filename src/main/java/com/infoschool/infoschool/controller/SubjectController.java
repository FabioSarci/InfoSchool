package com.infoschool.infoschool.controller;

import com.infoschool.infoschool.dto.request.SubjectDto;
import com.infoschool.infoschool.dto.response.MessageResponse;
import com.infoschool.infoschool.dto.response.SubjectAssignedToCourseDto;
import com.infoschool.infoschool.service.SubjectService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/subjects")
@Tag(name = "Subjects", description = "API per la gestione delle materie")
public class SubjectController {

    private final SubjectService subjectService;

    public SubjectController(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @Operation(summary = "Aggiungi una nuova materia")
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<?> addSubject(@RequestBody SubjectDto subject) {
        try {
            SubjectDto createdSubject = subjectService.add(subject);
            return ResponseEntity.status(201).body(createdSubject);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }

    @Operation(summary = "Modifica una materia")
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping
    public ResponseEntity<?> updateSubject(@RequestBody SubjectDto subject) {
        try {
            SubjectDto updatedSubject = subjectService.update(subject);
            return ResponseEntity.ok(updatedSubject);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }

    @Operation(summary = "Elimina una materia per ID")
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSubjectById(@PathVariable Long id) {
        try {
            subjectService.deleteById(id);
            return ResponseEntity.ok(new MessageResponse("Materia eliminata con successo"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }

    @Operation(summary = "Ottieni una materia per ID")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER') or hasRole('TEACHER')")
    @GetMapping("/{id}")
    public ResponseEntity<?> findSubjectById(@PathVariable Long id) {
        try {
            SubjectDto subject = subjectService.findById(id);
            if (subject == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(subject);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }

    @Operation(summary = "Ottieni tutte le materie")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER') or hasRole('TEACHER')")
    @GetMapping
    public ResponseEntity<?> findAllSubjects() {
        try {
            List<SubjectDto> subjects = subjectService.findAll();
            return ResponseEntity.ok(subjects);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }

    @Operation(summary = "Ottieni una materia per nome")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER') or hasRole('TEACHER')")
    @GetMapping("/by-name")
    public ResponseEntity<?> findSubjectByName(@RequestParam String name) {
        try {
            SubjectDto subject = subjectService.findByName(name);
            if (subject == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(subject);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }

    @Operation(summary = "Assegna una materia a un corso")
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/assign")
    public ResponseEntity<?> assignSubjectToCourse(@RequestParam Long subjectId, @RequestParam Long courseId) {
        try {
            SubjectAssignedToCourseDto assignedSubject = subjectService.assignSubjectToCourse(subjectId, courseId);
            return ResponseEntity.ok(assignedSubject);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }
}