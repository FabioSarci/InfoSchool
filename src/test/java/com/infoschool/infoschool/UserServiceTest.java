package com.infoschool.infoschool;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.infoschool.infoschool.dto.request.SignupRequest;
import com.infoschool.infoschool.dto.request.UserDto;
import com.infoschool.infoschool.dto.request.UserRegistrarionToCourseDto;
import com.infoschool.infoschool.mapper.UserMapper;
import com.infoschool.infoschool.model.Course;
import com.infoschool.infoschool.model.Role;
import com.infoschool.infoschool.model.User;
import com.infoschool.infoschool.repository.CourseRepository;
import com.infoschool.infoschool.repository.RoleRepository;
import com.infoschool.infoschool.repository.UserRepository;
import com.infoschool.infoschool.service.UserService;
import com.infoschool.infoschool.util.ERole;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Optional;

class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private UserMapper userMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddUser() {
        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setName("Marco");
        signupRequest.setSurname("Facecchia");
        signupRequest.setEmail("marco@facecchia.com");
        signupRequest.setPassword("password");
        signupRequest.setRole("user");

        Role role = new Role();
        role.setName(ERole.ROLE_USER);

        User user = new User();
        when(roleRepository.findByName(ERole.ROLE_USER)).thenReturn(Optional.of(role));
        when(userRepository.save(any(User.class))).thenReturn(user);

        UserDto userDto = new UserDto();
        when(userMapper.userToDto(user)).thenReturn(userDto);

        UserDto result = userService.addUser(signupRequest);

        assertNotNull(result);
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testRegisterUserToCourse() {
        UserRegistrarionToCourseDto registrationDto = new UserRegistrarionToCourseDto();
        registrationDto.setUserId(1L);
        registrationDto.setCourseId(1L);

        User user = new User();
        user.setCourses(new ArrayList<>());
        Course course = new Course();
        course.setStudents(new ArrayList<>());

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(courseRepository.findById(1L)).thenReturn(Optional.of(course));

        UserDto userDto = new UserDto();
        when(userMapper.userToDto(user)).thenReturn(userDto);

        UserDto result = userService.registration(registrationDto);

        assertNotNull(result);
        verify(userRepository, times(1)).save(user);
        verify(courseRepository, times(1)).save(course);
    }

    @Test
    void testGetUserByEmail() {
        String email = "marco@facecchia.com";

        User user = new User();
        user.setEmail(email);

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));

        UserDto userDto = new UserDto();
        userDto.setEmail(email);
        when(userMapper.userToDto(user)).thenReturn(userDto);

        Optional<UserDto> result = userService.getUserByEmail(email);

        assertTrue(result.isPresent());
        assertEquals(email, result.get().getEmail());
        verify(userRepository, times(1)).findByEmail(email);
    }

    @Test
    void testGetUserById() {
        Long userId = 1L;

        User user = new User();
        user.setId(userId);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        UserDto userDto = new UserDto();
        userDto.setId(userId);
        when(userMapper.userToDto(user)).thenReturn(userDto);

        Optional<UserDto> result = userService.getUserById(userId);

        assertTrue(result.isPresent());
        assertEquals(userId, result.get().getId());
        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    void testDeleteUserById() {
        Long userId = 1L;

        when(userRepository.existsById(userId)).thenReturn(true);

        userService.deleteUserById(userId);

        verify(userRepository, times(1)).deleteById(userId);
    }

    @Test
    void testExistsByEmail() {
        String email = "marco@facecchia.com";

        when(userRepository.existsByEmail(email)).thenReturn(true);

        boolean result = userService.existsByEmail(email);

        assertTrue(result);
        verify(userRepository, times(1)).existsByEmail(email);
    }

    @Test
    void testEditUser() {
        UserDto userDto = new UserDto();
        userDto.setId(1L);
        userDto.setName("Marco");
        userDto.setSurname("Facecchia");
        userDto.setEmail("marco@facecchia.com");
        userDto.setRoleId(1L);

        User user = new User();
        user.setId(1L);

        Role role = new Role();
        role.setId(1L);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(roleRepository.findById(1L)).thenReturn(Optional.of(role));
        when(userRepository.save(any(User.class))).thenReturn(user);

        UserDto updatedUserDto = new UserDto();
        when(userMapper.userToDto(user)).thenReturn(updatedUserDto);

        UserDto result = userService.edit(userDto);

        assertNotNull(result);
        verify(userRepository, times(1)).save(user);
    }
}