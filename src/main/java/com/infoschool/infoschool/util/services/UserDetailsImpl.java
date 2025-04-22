package com.infoschool.infoschool.util.services;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.infoschool.infoschool.model.User;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDetailsImpl implements UserDetails {
  private static final long serialVersionUID = 1L;

  private Long id;

  private String email;

  @JsonIgnore
  private String password;

  private String name;

  private String surname;
  private String address;
  private LocalDate birthDate;

  private Collection<? extends GrantedAuthority> authorities;

  /**
  * Crea un'istanza di UserDetailsImpl a partire da un oggetto User.
  */
  public static UserDetailsImpl build(User user) {
    GrantedAuthority authority = new SimpleGrantedAuthority(user.getRole().getName().name());

    return new UserDetailsImpl(
        user.getId(),
        user.getEmail(),
        user.getPassword(),
        user.getName(),
        user.getSurname(),
        user.getAddress(),
        user.getBirthDate(),
        List.of(authority)
    );
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return authorities;
  }

  @Override
  public String getUsername() {
    return email;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;

    if (o == null || getClass() != o.getClass())
      return false;

    UserDetailsImpl user = (UserDetailsImpl) o;

    return Objects.equals(id, user.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }
}