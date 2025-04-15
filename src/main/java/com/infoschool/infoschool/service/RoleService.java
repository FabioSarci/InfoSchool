package com.infoschool.infoschool.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.infoschool.infoschool.model.Role;
import com.infoschool.infoschool.repository.RoleRepository;
import com.infoschool.infoschool.util.ERole;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class RoleService {
    
    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Role add(Role role) {

        try {
            log.info("Adding role: {}", role.getName());
            if (roleRepository.findByName(role.getName()).isPresent()) {
                log.warn("Role {} already exists", role.getName());
                return null;
            }else{

                Role role2add = roleRepository.save(role);
                log.info("Role {} added successfully", role2add.getName());
                return role2add;
            }
        } catch (Exception e) {
            log.error("Error adding role: {}", e.getMessage());
            throw new RuntimeException("Error adding role", e);
        }
    }

    public Optional<Role> getById(Long id) {
        try {
            log.info("Fetching role with id: {}", id);
            if (id == null) {
                log.warn("Role ID is null");
                return null;
            }
            return roleRepository.findById(id);
        } catch (Exception e) {
            log.error("Error fetching role with id {}: {}", id, e.getMessage());
            throw new RuntimeException("Error fetching role", e);
        }
    }

    public Role edit(Role role) {
        try {
            log.info("Editing role: {}", role.getName());
            if( roleRepository.existsById(role.getId()) == false ){
                log.warn("Role with id {} does not exist", role.getId());
                return null;
            }
            return roleRepository.save(role);
        } catch (Exception e) {
            log.error("Error editing role: {}", e.getMessage());
            throw new RuntimeException("Error editing role", e);
        }
    }

    public void deleteById(Long id) {
        try {
            log.info("Deleting role with id: {}", id);
            if (id == null) {
                log.warn("Role ID is null");
                return;
            }
            roleRepository.deleteById(id);
            log.info("Role with id {} deleted successfully", id);
        } catch (Exception e) {
            log.error("Error deleting role with id {}: {}", id, e.getMessage());
            throw new RuntimeException("Error deleting role", e);
        }
    }

    public Role getByName(ERole name) {
        try {
            log.info("Fetching role with name: {}", name);
            if (name == null) {
                log.warn("Role name is null");
                return null;
            }
            return roleRepository.findByName(name).orElse(null);
        } catch (Exception e) {
            log.error("Error fetching role with name {}: {}", name, e.getMessage());
            throw new RuntimeException("Error fetching role", e);
        }
    }

    
}
