package com.infoschool.infoschool.dto.request;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    
    private long id;
    private String name;
    private String surname;
    private String email;
    private String address;
    private LocalDate birthDate;
    private long roleId;
}
