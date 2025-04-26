package com.infoschool.infoschool;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.Optional;

import com.infoschool.infoschool.dto.request.TeachedSubjectRequestDto;
import com.infoschool.infoschool.dto.response.TeachedSubjectResponseDto;
import com.infoschool.infoschool.mapper.TeachedSubjectMapper;
import com.infoschool.infoschool.model.Role;
import com.infoschool.infoschool.model.Subject;
import com.infoschool.infoschool.model.TeachedSubject;
import com.infoschool.infoschool.model.User;
import com.infoschool.infoschool.repository.SubjectRepository;
import com.infoschool.infoschool.repository.TeachedSubjectRepositiry;
import com.infoschool.infoschool.repository.UserRepository;
import com.infoschool.infoschool.service.TeachedSubjectService;
import com.infoschool.infoschool.util.ERole;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class TeachedSubjectServiceTest {

    @InjectMocks
    private TeachedSubjectService teachedSubjectService;

    @Mock
    private TeachedSubjectRepositiry teachedSubjectRepository;

    @Mock
    private TeachedSubjectMapper teachedSubjectMapper;

    @Mock
    private UserRepository userRepository;

    @Mock
    private SubjectRepository subjectRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddTeachedSubject() {
        Role role = new Role();
        role.setId(1L);
        role.setName(ERole.ROLE_TEACHER);

        User teacher = new User();
        teacher.setId(1L);
        teacher.setName("Mario");
        teacher.setSurname("Rossi");
        teacher.setEmail("mario@rossi.com");
        teacher.setPassword("password123");
        teacher.setRole(role);

        Subject subject = new Subject();
        subject.setId(2L);
        subject.setName("Java Programming");
        subject.setDescription("Learn Java from scratch");

        TeachedSubjectRequestDto request = new TeachedSubjectRequestDto(
                1L,
                2L,
                LocalDate.of(2025, 1, 1),
                LocalDate.of(2025, 12, 31)
        );

        when(userRepository.findById(1L)).thenReturn(Optional.of(teacher));
        when(subjectRepository.findById(2L)).thenReturn(Optional.of(subject));

        TeachedSubject teachedSubject = new TeachedSubject();
        teachedSubject.setId(1L);
        teachedSubject.setTeacher(teacher);
        teachedSubject.setSubject(subject);
        teachedSubject.setStartDate(request.getStartDate());
        teachedSubject.setEndDate(request.getEndDate());

        when(teachedSubjectMapper.toEntity(request)).thenReturn(teachedSubject);

        when(teachedSubjectRepository.save(any(TeachedSubject.class))).thenReturn(teachedSubject);

        TeachedSubjectResponseDto response = new TeachedSubjectResponseDto();
        response.setId(1L);
        response.setStartDate(request.getStartDate());
        response.setEndDate(request.getEndDate());
        response.setTeacherName(teacher.getName() + " " + teacher.getSurname());
        response.setSubjectName(subject.getName());

        when(teachedSubjectMapper.toDto(teachedSubject)).thenReturn(response);

        TeachedSubjectResponseDto result = teachedSubjectService.add(request);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Mario Rossi", result.getTeacherName());
        assertEquals("Java Programming", result.getSubjectName());
        verify(teachedSubjectRepository, times(1)).save(any(TeachedSubject.class));
    }

    @Test
    void testGetById() {
        User teacher = new User();
        teacher.setId(1L);
        teacher.setName("Mario");
        teacher.setSurname("Rossi");

        Subject subject = new Subject();
        subject.setId(2L);
        subject.setName("Java Programming");

        TeachedSubject teachedSubject = new TeachedSubject();
        teachedSubject.setId(1L);
        teachedSubject.setTeacher(teacher);
        teachedSubject.setSubject(subject);
        teachedSubject.setStartDate(LocalDate.of(2025, 1, 1));
        teachedSubject.setEndDate(LocalDate.of(2025, 12, 31));

        when(teachedSubjectRepository.findById(1L)).thenReturn(Optional.of(teachedSubject));

        TeachedSubjectResponseDto response = new TeachedSubjectResponseDto();
        response.setId(1L);
        response.setStartDate(teachedSubject.getStartDate());
        response.setEndDate(teachedSubject.getEndDate());
        response.setTeacherName(teacher.getName() + " " + teacher.getSurname());
        response.setSubjectName(subject.getName());

        when(teachedSubjectMapper.toDto(teachedSubject)).thenReturn(response);

        TeachedSubjectResponseDto result = teachedSubjectService.findById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Mario Rossi", result.getTeacherName());
        assertEquals("Java Programming", result.getSubjectName());
        verify(teachedSubjectRepository, times(1)).findById(1L);
    }
}