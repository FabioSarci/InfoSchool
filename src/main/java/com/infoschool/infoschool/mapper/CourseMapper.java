package com.infoschool.infoschool.mapper;

import org.springframework.stereotype.Component;

import com.infoschool.infoschool.dto.request.CourseRequestDto;
import com.infoschool.infoschool.dto.response.CourseResponseDto;
import com.infoschool.infoschool.model.Course;

@Component
public class CourseMapper {
    
    public CourseResponseDto courseToCourseResponseDto(Course course) {
        if (course == null) {
            return null;
        }
        CourseResponseDto courseResponseDto = new CourseResponseDto();
        courseResponseDto.setId(course.getId());
        courseResponseDto.setCode(course.getCode());
        courseResponseDto.setYear(course.getYear());
        courseResponseDto.setName(course.getName());
        courseResponseDto.setDescription(course.getDescription());
        return courseResponseDto;
    }

    public Course courseResponseDtotoCourse(CourseResponseDto courseResponseDto) {
        if (courseResponseDto == null) {
            return null;
        }
        Course course = new Course();
        course.setId(courseResponseDto.getId());
        course.setCode(courseResponseDto.getCode());
        course.setYear(courseResponseDto.getYear());
        course.setName(courseResponseDto.getName());
        course.setDescription(courseResponseDto.getDescription());
        return course;
    }

    public Course courseRequestDtotoCourse(CourseRequestDto courseRequestDto) {
        if (courseRequestDto == null) {
            return null;
        }
        Course course = new Course();
        course.setCode(courseRequestDto.getCode());
        course.setYear(courseRequestDto.getYear());
        course.setName(courseRequestDto.getName());
        course.setDescription(courseRequestDto.getDescription());
        return course;
    }
}
