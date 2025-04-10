package com.infoschool.infoschool.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.infoschool.infoschool.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    
    public User findByEmail(String email);
}
