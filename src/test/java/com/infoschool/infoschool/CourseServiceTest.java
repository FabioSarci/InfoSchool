package com.infoschool.infoschool;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.infoschool.infoschool.dto.request.CourseRequestDto;
import com.infoschool.infoschool.dto.response.CourseResponseDto;
import com.infoschool.infoschool.mapper.CourseMapper;
import com.infoschool.infoschool.model.Course;
import com.infoschool.infoschool.repository.CourseRepository;
import com.infoschool.infoschool.service.CourseService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

class CourseServiceTest {

    @InjectMocks
    private CourseService courseService;

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private CourseMapper courseMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddCourse() {
        CourseRequestDto courseRequest = new CourseRequestDto();
        courseRequest.setName("Test Course");
        courseRequest.setYear(2023);

        Course course = new Course();
        when(courseMapper.courseRequestDtotoCourse(courseRequest)).thenReturn(course);
        when(courseRepository.save(course)).thenReturn(course);

        CourseResponseDto response = new CourseResponseDto();
        when(courseMapper.courseToCourseResponseDto(course)).thenReturn(response);

        CourseResponseDto result = courseService.add(courseRequest);

        assertNotNull(result);
        verify(courseRepository, times(1)).save(course);
    }

    @Test
    void testGetById() {
        Course course = new Course();
        course.setId(1L);

        when(courseRepository.findById(1L)).thenReturn(Optional.of(course));

        CourseResponseDto response = new CourseResponseDto();
        when(courseMapper.courseToCourseResponseDto(course)).thenReturn(response);

        CourseResponseDto result = courseService.getById(1L);

        assertNotNull(result);
        verify(courseRepository, times(1)).findById(1L);
    }
}