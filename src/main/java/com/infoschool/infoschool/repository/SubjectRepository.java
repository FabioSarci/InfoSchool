package com.infoschool.infoschool.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.infoschool.infoschool.model.Subject;

public interface SubjectRepository extends JpaRepository<Subject, Long>{
    
}
