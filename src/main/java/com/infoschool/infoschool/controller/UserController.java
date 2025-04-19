package com.infoschool.infoschool.controller;

import com.infoschool.infoschool.dto.request.UserDtoForm;
import com.infoschool.infoschool.dto.request.UserRegistrarionToCourseDto;
import com.infoschool.infoschool.dto.response.MessageResponse;
import com.infoschool.infoschool.model.User;
import com.infoschool.infoschool.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@Tag(name = "Users", description = "API per la gestione degli utenti")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Aggiungi un nuovo utente")
    @PostMapping
    public ResponseEntity<?> addUser(@RequestBody UserDtoForm userDtoForm) {
        try {
            User createdUser = userService.addUser(userDtoForm);
            return ResponseEntity.status(201).body(createdUser);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }

    @Operation(summary = "Modifica un utente")
    @PutMapping
    public ResponseEntity<?> editUser(@RequestBody UserDtoForm userDtoForm) {
        try {
            User updatedUser = userService.edit(userDtoForm);
            if (updatedUser == null) {
                return ResponseEntity.badRequest().body(new MessageResponse("Utente non trovato"));
            }
            return ResponseEntity.ok(updatedUser);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }

    @Operation(summary = "Elimina un utente per ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUserById(@PathVariable Long id) {
        try {
            userService.deleteUserById(id);
            return ResponseEntity.ok(new MessageResponse("Utente eliminato con successo"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }

    @Operation(summary = "Ottieni un utente per ID")
    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        try {
            Optional<User> user = userService.getUserById(id);
            if (user.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(user.get());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }

    @Operation(summary = "Ottieni un utente per email")
    @GetMapping("/email/{email}")
    public ResponseEntity<?> getUserByEmail(@PathVariable String email) {
        try {
            Optional<User> user = userService.getUserByEmail(email);
            if (user.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(user.get());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }

    @Operation(summary = "Registra un utente a un corso")
    @PostMapping("/register")
    public ResponseEntity<?> registerUserToCourse(@RequestBody UserRegistrarionToCourseDto registrationDto) {
        try {
            User registeredUser = userService.registration(registrationDto);
            return ResponseEntity.ok(registeredUser);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }
}