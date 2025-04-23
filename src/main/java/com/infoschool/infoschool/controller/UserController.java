package com.infoschool.infoschool.controller;

import com.infoschool.infoschool.dto.request.SignupRequest;
import com.infoschool.infoschool.dto.request.UserDto;
import com.infoschool.infoschool.dto.request.UserRegistrarionToCourseDto;
import com.infoschool.infoschool.dto.response.MessageResponse;
import com.infoschool.infoschool.service.UserService;
import com.infoschool.infoschool.util.services.export.CsvExportService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@Tag(name = "Users", description = "API per la gestione degli utenti")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private CsvExportService csvExportService;


    @Operation(summary = "Aggiungi un nuovo utente")
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<?> addUser(@RequestBody SignupRequest userRequest) {
        try {
            if (userService.existsByEmail(userRequest.getEmail())) {
                return ResponseEntity
                        .badRequest()
                        .body(new MessageResponse("Error: Email is already in use!"));
            }
            UserDto createdUser = userService.addUser(userRequest);
            return ResponseEntity.status(201).body(createdUser);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }

    @Operation(summary = "Modifica un utente")
    @PutMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER') or hasRole('TEACHER')")
    public ResponseEntity<?> editUser(@RequestBody UserDto userDtoForm) {
        try {
            UserDto updatedUser = userService.edit(userDtoForm);
            if (updatedUser == null) {
                return ResponseEntity.badRequest().body(new MessageResponse("Utente non trovato"));
            }
            return ResponseEntity.ok(updatedUser);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }

    @Operation(summary = "Elimina un utente per ID")
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUserById(@PathVariable Long id) {
        try {
            userService.deleteUserById(id);
            return ResponseEntity.ok(new MessageResponse("Utente eliminato con successo"));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse("Errore durante l'eliminazione dell'utente"));
        }
    }

    @Operation(summary = "Ottieni un utente per ID")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER') or hasRole('TEACHER')")
    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        try {
            Optional<UserDto> user = userService.getUserById(id);
            if (user.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(user.get());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }

    @Operation(summary = "Ottieni un utente per email")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER') or hasRole('TEACHER')")
    @GetMapping("/email")
    public ResponseEntity<?> getUserByEmail(@RequestParam String email) {
        try {
            Optional<UserDto> user = userService.getUserByEmail(email);
            if (user.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(user.get());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }

    @Operation(summary = "Registra un utente a un corso")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER') or hasRole('TEACHER')")
    @PostMapping("/register")
    public ResponseEntity<?> registerUserToCourse(@RequestBody UserRegistrarionToCourseDto registrationDto) {
        try {
            UserDto registeredUser = userService.registration(registrationDto);
            return ResponseEntity.ok(registeredUser);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }

    @Operation(summary = "Esporta gli utenti in formato CSV")
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/export/csv")
    public void exportUsersToCsv(HttpServletResponse response) throws IOException {
        response.setContentType("text/csv");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=users.csv");

        csvExportService.exportUsersToCsv(response.getWriter());
    }
}