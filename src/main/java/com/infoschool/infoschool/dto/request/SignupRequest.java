package com.infoschool.infoschool.dto.request;


import java.time.LocalDate;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignupRequest {
    @NotBlank
    @Size(max = 50)
    @Email
    private String email;
    @NotBlank
    @Size(min = 6, max = 40)
    private String password;
    @NotBlank
    private String name;
    @NotBlank
    private String surname;

    private String address;
    
    @NotBlank
    private LocalDate birthDate;
    @NotBlank
    private String role;
}
