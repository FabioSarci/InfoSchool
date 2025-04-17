package com.infoschool.infoschool.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.infoschool.infoschool.model.Project;

public interface ProjectReposiroty extends JpaRepository<Project, Long>{
    
}
