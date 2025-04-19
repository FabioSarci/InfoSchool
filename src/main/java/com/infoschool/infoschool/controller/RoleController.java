package com.infoschool.infoschool.controller;

import com.infoschool.infoschool.dto.response.MessageResponse;
import com.infoschool.infoschool.model.Role;
import com.infoschool.infoschool.service.RoleService;
import com.infoschool.infoschool.util.ERole;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/roles")
@Tag(name = "Roles", description = "API per la gestione dei ruoli")
public class RoleController {

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @Operation(summary = "Aggiungi un nuovo ruolo")
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<?> addRole(@RequestBody Role role) {
        try {
            Role createdRole = roleService.add(role);
            if (createdRole == null) {
                return ResponseEntity.badRequest().body(new MessageResponse("Il ruolo esiste già"));
            }
            return ResponseEntity.status(201).body(createdRole);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }

    @Operation(summary = "Ottieni un ruolo per ID")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER') or hasRole('TEACHER')")
    @GetMapping("/{id}")
    public ResponseEntity<?> getRoleById(@PathVariable Long id) {
        try {
            Optional<Role> role = roleService.getById(id);
            if (role.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(role.get());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }

    @Operation(summary = "Modifica un ruolo")
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping
    public ResponseEntity<?> editRole(@RequestBody Role role) {
        try {
            Role updatedRole = roleService.edit(role);
            if (updatedRole == null) {
                return ResponseEntity.badRequest().body(new MessageResponse("Il ruolo non esiste"));
            }
            return ResponseEntity.ok(updatedRole);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }

    @Operation(summary = "Elimina un ruolo per ID")
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRoleById(@PathVariable Long id) {
        try {
            roleService.deleteById(id);
            return ResponseEntity.ok(new MessageResponse("Ruolo eliminato con successo"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }

    @Operation(summary = "Ottieni un ruolo per nome")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER') or hasRole('TEACHER')")
    @GetMapping("/name/{name}")
    public ResponseEntity<?> getRoleByName(@PathVariable ERole name) {
        try {
            Role role = roleService.getByName(name);
            if (role == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(role);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }
}