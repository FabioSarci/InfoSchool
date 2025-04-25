package com.infoschool.infoschool.controller;

import com.infoschool.infoschool.dto.request.CourseRequestDto;
import com.infoschool.infoschool.dto.response.CourseResponseDto;
import com.infoschool.infoschool.dto.response.MessageResponse;
import com.infoschool.infoschool.mapper.CourseMapper;
import com.infoschool.infoschool.model.Course;
import com.infoschool.infoschool.service.CourseService;
import com.infoschool.infoschool.service.export.ExcelExportService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/courses")
@Tag(name = "Courses", description = "API per la gestione dei corsi")
public class CourseController {

    @Autowired
    private CourseService courseService;
    @Autowired
    private ExcelExportService excelExportService;
    @Autowired
    private CourseMapper courseMapper;

    @Operation(summary = "Aggiungi un nuovo corso")
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<?> addCourse(@RequestBody CourseRequestDto course) {
        try {
            CourseResponseDto createdCourse = courseService.add(course);
            return ResponseEntity.status(201).body(createdCourse);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }

    @Operation(summary = "Ottieni un corso per ID")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER') or hasRole('TEACHER')")
    @GetMapping("/{id}")
    public ResponseEntity<?> getCourseById(@PathVariable Long id) {
        try {
            CourseResponseDto course = courseService.getById(id);
            if (course == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(course);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }

    @Operation(summary = "Ottieni un corso per nome")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER') or hasRole('TEACHER')")
    @GetMapping("/name")
    public ResponseEntity<?> getCourseByName(@RequestParam String name) {
        try {
            CourseResponseDto course = courseService.getByName(name);
            if (course == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(course);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }

    @Operation(summary = "Ottieni un corso per nome e anno")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER') or hasRole('TEACHER')")
    @GetMapping("/name-year")
    public ResponseEntity<?> getCourseByNameAndYear(@RequestParam String name, @RequestParam int year) {
        try {
            CourseResponseDto course = courseService.getByNameAndYear(name, year);
            if (course == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(course);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }

    @Operation(summary = "Modifica un corso")
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping
    public ResponseEntity<?> editCourse(@RequestBody CourseRequestDto course) {
        try {
            CourseResponseDto updatedCourse = courseService.edit(course);
            return ResponseEntity.ok(updatedCourse);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }

    @Operation(summary = "Elimina un corso per ID")
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCourseById(@PathVariable Long id) {
        try {
            courseService.deleteById(id);
            return ResponseEntity.ok(new MessageResponse("Corso eliminato con successo"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }


    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/export/excel")
    public void exportCoursesToExcel(HttpServletResponse response) throws IOException {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=courses.xlsx");

        List<CourseResponseDto> courses = courseService.getAll();
        if (courses.isEmpty()) {
            throw new IOException("Nessun corso trovato per l'esportazione.");
        }
        List<Course> coursesEntityList = courses.stream()
                .map(courseMapper::courseResponseDtotoCourse)
                .toList();
        try (var outputStream = response.getOutputStream()) {
            excelExportService.exportCoursesAndSubjectsToExcel(outputStream, coursesEntityList);
            outputStream.flush();
        } catch (Exception e) {
            throw new IOException("Errore durante l'esportazione in Excel: " + e.getMessage(), e);
        }
    }
}