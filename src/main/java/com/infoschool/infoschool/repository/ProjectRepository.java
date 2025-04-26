package com.infoschool.infoschool.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.infoschool.infoschool.model.Project;

public interface ProjectRepository extends JpaRepository<Project, Long>{

    List<Project> findByCourseId(Long courseId);

    Optional<Project> findByTitle(String title);
}
