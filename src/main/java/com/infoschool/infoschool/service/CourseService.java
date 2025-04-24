package com.infoschool.infoschool.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.infoschool.infoschool.model.Course;
import com.infoschool.infoschool.repository.CourseRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CourseService {
    
    private final CourseRepository courseRepository;

    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public Course add(Course course) {
        try {
            log.info("Adding new course: {}", course);
            if (courseRepository.existsByNameAndYear(course.getName(), course.getYear())) {
                log.error("Course with name {} and year {} already exists", course.getName(), course.getYear());
                throw new RuntimeException("Course with this name already exists");
            }
            return courseRepository.save(course);
        } catch (Exception e) {
            log.error("Error adding course: {}", course, e);
            throw new RuntimeException("Error adding course: " + e.getMessage(), e);
        }
    }

    public Course getById(Long id) {
        try {
            log.info("Fetching course by ID: {}", id);
            return courseRepository.findById(id).orElse(null);
        } catch (Exception e) {
            log.error("Error fetching course by ID: {}", id, e);
            throw new RuntimeException("Error fetching course by ID: " + e.getMessage(), e);
        }
    }

    public Course getByName(String name) {
        try {
            log.info("Fetching course by name: {}", name);
            return courseRepository.findByName(name).orElse(null);
        } catch (Exception e) {
            log.error("Error fetching course by name: {}", name, e);
            throw new RuntimeException("Error fetching course by name: " + e.getMessage(), e);
        }
    }

    public Course getByNameAndYear(String name, int year) {
        try {
            log.info("Fetching course by name: {} and year: {}", name, year);
            return courseRepository.findByNameAndYear(name, year).orElse(null);
        } catch (Exception e) {
            log.error("Error fetching course by name: {} and year: {}", name, year, e);
            throw new RuntimeException("Error fetching course by name and year: " + e.getMessage(), e);
        }
    }

    public void deleteById(Long id) {
        try {
            log.info("Deleting course by ID: {}", id);
            courseRepository.deleteById(id);
        } catch (Exception e) {
            log.error("Error deleting course by ID: {}", id, e);
            throw new RuntimeException("Error deleting course by ID: " + e.getMessage(), e);
        }
    }
    
    public Course edit(Course course) {
        try {
            log.info("Editing course: {}", course);
            Course existingCourse = courseRepository.findById(course.getId()).orElse(null);
            if (existingCourse != null) {
                existingCourse.setName(course.getName());
                existingCourse.setYear(course.getYear());
                return courseRepository.save(existingCourse);
            } else {
                log.error("Course with ID {} not found", course.getId());
                throw new RuntimeException("Course not found");
            }
        } catch (Exception e) {
            log.error("Error editing course: {}", course, e);
            throw new RuntimeException("Error editing course: " + e.getMessage(), e);
        }
    }

    public List<Course> getAll() {
        try {
            log.info("Fetching all courses");
            return courseRepository.findAll();
        } catch (Exception e) {
            log.error("Error fetching all courses", e);
            throw new RuntimeException("Error fetching all courses: " + e.getMessage(), e);
        }
    }
}
