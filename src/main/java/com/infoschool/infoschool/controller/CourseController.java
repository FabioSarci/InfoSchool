package com.infoschool.infoschool.controller;

import com.infoschool.infoschool.dto.response.MessageResponse;
import com.infoschool.infoschool.model.Course;
import com.infoschool.infoschool.service.CourseService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/courses")
@Tag(name = "Courses", description = "API per la gestione dei corsi")
public class CourseController {

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @Operation(summary = "Aggiungi un nuovo corso")
    @PostMapping
    public ResponseEntity<?> addCourse(@RequestBody Course course) {
        try {
            Course createdCourse = courseService.add(course);
            return ResponseEntity.status(201).body(createdCourse);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }

    @Operation(summary = "Ottieni un corso per ID")
    @GetMapping("/{id}")
    public ResponseEntity<?> getCourseById(@PathVariable Long id) {
        try {
            Course course = courseService.getById(id);
            if (course == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(course);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }

    @Operation(summary = "Ottieni un corso per nome")
    @GetMapping("/name/{name}")
    public ResponseEntity<?> getCourseByName(@PathVariable String name) {
        try {
            Course course = courseService.getByName(name);
            if (course == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(course);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }

    @Operation(summary = "Ottieni un corso per nome e anno")
    @GetMapping("/name/{name}/year/{year}")
    public ResponseEntity<?> getCourseByNameAndYear(@PathVariable String name, @PathVariable int year) {
        try {
            Course course = courseService.getByNameAndYear(name, year);
            if (course == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(course);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }

    @Operation(summary = "Modifica un corso")
    @PutMapping
    public ResponseEntity<?> editCourse(@RequestBody Course course) {
        try {
            Course updatedCourse = courseService.edit(course);
            return ResponseEntity.ok(updatedCourse);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }

    @Operation(summary = "Elimina un corso per ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCourseById(@PathVariable Long id) {
        try {
            courseService.deleteById(id);
            return ResponseEntity.ok(new MessageResponse("Corso eliminato con successo"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }
}