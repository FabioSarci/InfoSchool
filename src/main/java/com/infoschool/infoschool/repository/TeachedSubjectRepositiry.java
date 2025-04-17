package com.infoschool.infoschool.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.infoschool.infoschool.model.TeachedSubject;

public interface TeachedSubjectRepositiry extends JpaRepository<TeachedSubject, Long> {
    
}
