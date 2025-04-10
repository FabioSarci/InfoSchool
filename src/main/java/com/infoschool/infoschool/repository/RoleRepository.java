package com.infoschool.infoschool.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.infoschool.infoschool.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
    
    Role findByName(String name);
    
}
