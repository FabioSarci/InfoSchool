package com.infoschool.infoschool.dto.response;

import java.util.Set;

import com.infoschool.infoschool.util.ERole;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JwtResponse {

  private String token;
  private String type = "Bearer";
  private Long id;
  private String name;
  private String surname;
  private String email;
  private Set<ERole> roles;

  public JwtResponse(String token, Long id, String name, String surname, String email, Set<ERole> roles2) {
    this.token = token;
    this.id = id;
    this.name = name;
    this.surname = surname;
    this.email = email;
    this.roles = roles2;
  }

}