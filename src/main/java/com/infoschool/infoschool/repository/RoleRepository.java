package com.infoschool.infoschool.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.infoschool.infoschool.model.Role;
import com.infoschool.infoschool.util.ERole;

public interface RoleRepository extends JpaRepository<Role, Long> {
    
    public Optional<Role> findByName(ERole name);
    
}
