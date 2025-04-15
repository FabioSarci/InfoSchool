package com.infoschool.infoschool.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.infoschool.infoschool.model.Course;

public interface CourseRepository extends JpaRepository<Course, Long> {
}
