package com.infoschool.infoschool.controller;

import com.infoschool.infoschool.dto.response.MessageResponse;
import com.infoschool.infoschool.model.AttachmentType;
import com.infoschool.infoschool.service.AttachmentTypeService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/attachment-types")
@Tag(name = "Attachment Types", description = "API per la gestione dei tipi di allegati")
public class AttachmentTypeController {

    private final AttachmentTypeService attachmentTypeService;

    public AttachmentTypeController(AttachmentTypeService attachmentTypeService) {
        this.attachmentTypeService = attachmentTypeService;
    }

    @Operation(summary = "Aggiungi un nuovo tipo di allegato")
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<?> addAttachmentType(@RequestBody AttachmentType attachmentType) {
        try {
            AttachmentType createdAttachmentType = attachmentTypeService.add(attachmentType);
            return ResponseEntity.status(201).body(createdAttachmentType);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }

    @Operation(summary = "Ottieni un tipo di allegato per ID")
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<?> getAttachmentTypeById(@PathVariable Long id) {
        try {
            AttachmentType attachmentType = attachmentTypeService.getById(id);
            if (attachmentType == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(attachmentType);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }

    @Operation(summary = "Ottieni un tipo di allegato per nome")
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/name/{name}")
    public ResponseEntity<?> getAttachmentTypeByName(@PathVariable String name) {
        try {
            AttachmentType attachmentType = attachmentTypeService.getByName(name);
            if (attachmentType == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(attachmentType);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }

    @Operation(summary = "Modifica un tipo di allegato")
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping
    public ResponseEntity<?> editAttachmentType(@RequestBody AttachmentType attachmentType) {
        try {
            AttachmentType updatedAttachmentType = attachmentTypeService.edit(attachmentType);
            return ResponseEntity.ok(updatedAttachmentType);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }

    @Operation(summary = "Elimina un tipo di allegato per ID")
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAttachmentTypeById(@PathVariable Long id) {
        try {
            attachmentTypeService.deleteById(id);
            return ResponseEntity.ok(new MessageResponse("Tipo di allegato eliminato con successo"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }
}