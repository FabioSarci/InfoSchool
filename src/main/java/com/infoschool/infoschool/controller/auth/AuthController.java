package com.infoschool.infoschool.controller.auth;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.infoschool.infoschool.dto.request.SigninRequest;
import com.infoschool.infoschool.dto.request.SignupRequest;
import com.infoschool.infoschool.dto.request.UserDtoForm;
import com.infoschool.infoschool.dto.response.JwtResponse;
import com.infoschool.infoschool.dto.response.MessageResponse;
import com.infoschool.infoschool.model.Role;
import com.infoschool.infoschool.repository.RoleRepository;
import com.infoschool.infoschool.service.UserService;
import com.infoschool.infoschool.util.ERole;
import com.infoschool.infoschool.util.jwt.JwtUtils;
import com.infoschool.infoschool.util.services.UserDetailsImpl;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authentication Controller", description = "API per l' autenticazione e la registrazione delgi utenti")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserService userService;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;


    @Operation(summary = "Autentica un utente tramite username e password", description = "Restituisce il token e le credenziali")
    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody SigninRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);


        // Ottiene i dettagli dell'utente autenticato
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        Set<ERole> roles = userDetails.getAuthorities().stream()
                .map(item -> ERole.valueOf(item.getAuthority()))
                .collect(Collectors.toSet());

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getName(),
                userDetails.getSurname(),
                userDetails.getEmail(),
                roles));
    }


    @Operation(summary = "Registra l'utente nel database", description = "Restituisce il token e le credenziali")
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        // Verifica se il nome utente è già in uso
        if (userService.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }

        // Verifica se l'email è già registrata
        if (userService.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }

        // Creazione di un nuovo utente con credenziali codificate
        UserDtoForm newUser = new UserDtoForm();

        newUser.setEmail(signUpRequest.getEmail());
        newUser.setPassword(encoder.encode(signUpRequest.getPassword()));
        newUser.setName(signUpRequest.getName());
        newUser.setSurname(signUpRequest.getSurname());
        newUser.setBirthDate(signUpRequest.getBirthDate());

        String strRole = signUpRequest.getRole();
        Role role = new Role();

        // Assegna il ruolo all'utente in base alla richiesta
        if (strRole == null) {
            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            role = userRole;
        } else {
                switch (strRole.toLowerCase()) {
                    case "admin":
                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        role = adminRole;
                        break;
                    case "teacher":
                        Role teacherRole = roleRepository.findByName(ERole.ROLE_TEACHER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        role = teacherRole;
                        break;
                    default:
                        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        role = userRole;
                }
        }

        newUser.setRole(role);
        userService.addUser(newUser);

        // Autentica l'utente appena registrato
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(signUpRequest.getEmail(), signUpRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        return ResponseEntity.ok(new JwtResponse(jwt,
                newUser.getUserId(),
                newUser.getName(),
                newUser.getSurname(),
                newUser.getEmail(),
                Collections.singleton(role.getName())
                ));
    }
}