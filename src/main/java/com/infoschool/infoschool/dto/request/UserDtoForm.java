package com.infoschool.infoschool.dto.request;


import com.infoschool.infoschool.model.Role;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDtoForm {
    
    private Long userId;
    private String name;
    private String surname;
    private String email;
    private String password;
    private String address;
    private String birthDate;
    private Role role;
}
