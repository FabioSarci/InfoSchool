package com.infoschool.infoschool.controller;

import com.infoschool.infoschool.dto.response.MessageResponse;
import com.infoschool.infoschool.model.Mail;
import com.infoschool.infoschool.service.MailService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mails")
@Tag(name = "Mails", description = "API per la gestione delle mail")
public class MailController {

    private final MailService mailService;

    public MailController(MailService mailService) {
        this.mailService = mailService;
    }

    @Operation(summary = "Invia una nuova mail")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN') or hasRole('TEACHER')")
    @PostMapping
    public ResponseEntity<?> sendMail(@RequestBody Mail mail) {
        try {
            Mail sentMail = mailService.send(mail);
            return ResponseEntity.status(201).body(sentMail);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }

    @Operation(summary = "Ottieni una mail per ID")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN') or hasRole('TEACHER')")
    @GetMapping("/{id}")
    public ResponseEntity<?> getMailById(@PathVariable Long id) {
        try {
            Mail mail = mailService.getById(id);
            if (mail == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(mail);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }

    @Operation(summary = "Elimina una mail per ID")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN') or hasRole('TEACHER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMailById(@PathVariable Long id) {
        try {
            mailService.deleteById(id);
            return ResponseEntity.ok(new MessageResponse("Mail eliminata con successo"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }

    @Operation(summary = "Ottieni tutte le mail inviate da un utente")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN') or hasRole('TEACHER')")
    @GetMapping("/sender/{senderId}")
    public ResponseEntity<?> getMailsBySenderId(@PathVariable Long senderId) {
        try {
            List<Mail> mails = mailService.getBySenderId(senderId);
            return ResponseEntity.ok(mails);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }

    @Operation(summary = "Ottieni tutte le mail ricevute da un utente")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN') or hasRole('TEACHER')")
    @GetMapping("/receiver/{receiverId}")
    public ResponseEntity<?> getMailsByReceiverId(@PathVariable Long receiverId) {
        try {
            List<Mail> mails = mailService.getByReceiverId(receiverId);
            return ResponseEntity.ok(mails);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }

    @Operation(summary = "Ottieni tutte le mail")
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<?> getAllMails() {
        try {
            List<Mail> mails = mailService.getAll();
            return ResponseEntity.ok(mails);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }

    @Operation(summary = "Ottieni tutte le mail inviate da un utente a un altro utente")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN') or hasRole('TEACHER')")
    @GetMapping("/sender/{senderId}/receiver/{receiverId}")
    public ResponseEntity<?> getMailsBySenderIdAndReceiverId(@PathVariable Long senderId, @PathVariable Long receiverId) {
        try {
            List<Mail> mails = mailService.getBySenderIdAndReceiverId(senderId, receiverId);
            return ResponseEntity.ok(mails);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }
}
