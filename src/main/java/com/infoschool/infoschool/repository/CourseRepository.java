package com.infoschool.infoschool.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.infoschool.infoschool.model.Course;

public interface CourseRepository extends JpaRepository<Course, Long> {

    Optional<Course> findByName(String name);

    Boolean existsByName(String name);

    List<Course> findByYear(int year);

    Optional<Course> findByNameAndYear(String name, int year);

    Boolean existsByNameAndYear(String name, int year);
}
