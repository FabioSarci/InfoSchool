package com.infoschool.infoschool.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infoschool.infoschool.dto.request.CourseRequestDto;
import com.infoschool.infoschool.dto.response.CourseResponseDto;
import com.infoschool.infoschool.mapper.CourseMapper;
import com.infoschool.infoschool.model.Course;
import com.infoschool.infoschool.repository.CourseRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CourseService {
    
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private CourseMapper courseMapper;

    public CourseResponseDto add(CourseRequestDto course) {
        try {
            log.info("Adding new course: {}", course);
            if (courseRepository.existsByNameAndYear(course.getName(), course.getYear())) {
                log.error("Course with name {} and year {} already exists", course.getName(), course.getYear());
                throw new RuntimeException("Course with this name already exists");
            }
            Course newCourse = courseMapper.courseRequestDtotoCourse(course);
            courseRepository.save(newCourse);
            log.info("Course added successfully: {}", newCourse);
            return courseMapper.courseToCourseResponseDto(newCourse);
        } catch (Exception e) {
            log.error("Error adding course: {}", course, e);
            throw new RuntimeException("Error adding course: " + e.getMessage(), e);
        }
    }

    public CourseResponseDto getById(Long id) {
        try {
            log.info("Fetching course by ID: {}", id);
            Course course = courseRepository.findById(id).orElse(null);
            return course != null ? courseMapper.courseToCourseResponseDto(course) : null;
        } catch (Exception e) {
            log.error("Error fetching course by ID: {}", id, e);
            throw new RuntimeException("Error fetching course by ID: " + e.getMessage(), e);
        }
    }

    public CourseResponseDto getByName(String name) {
        try {
            log.info("Fetching course by name: {}", name);
            Course course = courseRepository.findByName(name).orElse(null);
            return course != null ? courseMapper.courseToCourseResponseDto(course) : null;
        } catch (Exception e) {
            log.error("Error fetching course by name: {}", name, e);
            throw new RuntimeException("Error fetching course by name: " + e.getMessage(), e);
        }
    }

    public CourseResponseDto getByNameAndYear(String name, int year) {
        try {
            log.info("Fetching course by name: {} and year: {}", name, year);
            Course course = courseRepository.findByNameAndYear(name, year).orElse(null);
            return course != null ? courseMapper.courseToCourseResponseDto(course) : null;
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
    
    public CourseResponseDto edit(CourseRequestDto course) {
        try {
            log.info("Editing course: {}", course);
            Course existingCourse = courseRepository.findById(course.getId()).orElse(null);
            if (existingCourse != null) {
                existingCourse.setName(course.getName());
                existingCourse.setYear(course.getYear());
                existingCourse.setDescription(course.getDescription());
                return courseMapper.courseToCourseResponseDto(courseRepository.save(existingCourse));
            } else {
                log.error("Course with ID {} not found", course.getId());
                throw new RuntimeException("Course not found");
            }
        } catch (Exception e) {
            log.error("Error editing course: {}", course, e);
            throw new RuntimeException("Error editing course: " + e.getMessage(), e);
        }
    }

    public List<CourseResponseDto> getAll() {
        try {
            log.info("Fetching all courses");
            List<Course> courses = courseRepository.findAll();
            if (courses.isEmpty()) {
                log.warn("No courses found");
                return List.of();
            }
            return courses.stream()
                    .map(courseMapper::courseToCourseResponseDto)
                    .toList();
        } catch (Exception e) {
            log.error("Error fetching all courses", e);
            throw new RuntimeException("Error fetching all courses: " + e.getMessage(), e);
        }
    }
}
