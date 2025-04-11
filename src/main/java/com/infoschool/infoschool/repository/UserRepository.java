package com.infoschool.infoschool.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.infoschool.infoschool.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    
    public Optional<User> findByEmail(String email);

    public boolean existsByEmail(String email);
}
