package com.infoschool.infoschool.controller;

import com.infoschool.infoschool.dto.response.MessageResponse;
import com.infoschool.infoschool.model.Elaborate;
import com.infoschool.infoschool.service.ElaborateService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/elaborates")
@Tag(name = "Elaborates", description = "API per la gestione degli elaborati")
public class ElaborateController {

    private final ElaborateService elaborateService;

    public ElaborateController(ElaborateService elaborateService) {
        this.elaborateService = elaborateService;
    }

    @Operation(summary = "Aggiungi un nuovo elaborato")
    @PreAuthorize("hasRole('USER')")
    @PostMapping
    public ResponseEntity<?> addElaborate(@RequestBody Elaborate elaborate) {
        try {
            Elaborate createdElaborate = elaborateService.add(elaborate);
            return ResponseEntity.status(201).body(createdElaborate);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }

    @Operation(summary = "Ottieni un elaborato per ID")
    @PreAuthorize("hasRole('USER') or hasRole('TEACHER') or hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<?> getElaborateById(@PathVariable Long id) {
        try {
            Elaborate elaborate = elaborateService.getById(id);
            if (elaborate == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(elaborate);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }

    @Operation(summary = "Modifica un elaborato")
    @PreAuthorize("hasRole('USER')")
    @PutMapping
    public ResponseEntity<?> editElaborate(@RequestBody Elaborate elaborate) {
        try {
            Elaborate updatedElaborate = elaborateService.edit(elaborate);
            return ResponseEntity.ok(updatedElaborate);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }

    @Operation(summary = "Elimina un elaborato per ID")
    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteElaborateById(@PathVariable Long id) {
        try {
            elaborateService.deleteById(id);
            return ResponseEntity.ok(new MessageResponse("Elaborato eliminato con successo"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }
}