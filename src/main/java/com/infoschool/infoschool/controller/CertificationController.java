package com.infoschool.infoschool.controller;

import com.infoschool.infoschool.dto.response.MessageResponse;
import com.infoschool.infoschool.model.Certification;
import com.infoschool.infoschool.service.CertificationService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/certifications")
@Tag(name = "Certifications", description = "API per la gestione delle certificazioni")
public class CertificationController {

    private final CertificationService certificationService;

    public CertificationController(CertificationService certificationService) {
        this.certificationService = certificationService;
    }

    @Operation(summary = "Aggiungi una nuova certificazione")
    @PostMapping
    public ResponseEntity<?> addCertification(@RequestBody Certification certification) {
        try {
            Certification createdCertification = certificationService.add(certification);
            return ResponseEntity.status(201).body(createdCertification);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }

    @Operation(summary = "Ottieni una certificazione per ID")
    @GetMapping("/{id}")
    public ResponseEntity<?> getCertificationById(@PathVariable Long id) {
        try {
            Certification certification = certificationService.getById(id);
            if (certification == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(certification);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }

    @Operation(summary = "Ottieni una certificazione per nome")
    @GetMapping("/name/{name}")
    public ResponseEntity<?> getCertificationByName(@PathVariable String name) {
        try {
            Certification certification = certificationService.getByName(name);
            if (certification == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(certification);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }

    @Operation(summary = "Modifica una certificazione")
    @PutMapping
    public ResponseEntity<?> editCertification(@RequestBody Certification certification) {
        try {
            Certification updatedCertification = certificationService.edit(certification);
            return ResponseEntity.ok(updatedCertification);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }

    @Operation(summary = "Elimina una certificazione per ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCertificationById(@PathVariable Long id) {
        try {
            certificationService.deleteById(id);
            return ResponseEntity.ok(new MessageResponse("Certificazione eliminata con successo"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }
}
