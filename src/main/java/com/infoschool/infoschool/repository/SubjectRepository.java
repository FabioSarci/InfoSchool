package com.infoschool.infoschool.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.infoschool.infoschool.model.Subject;

public interface SubjectRepository extends JpaRepository<Subject, Long>{
    
    Optional<Subject> findByName(String name);
}
