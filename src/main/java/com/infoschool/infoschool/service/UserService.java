package com.infoschool.infoschool.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infoschool.infoschool.dto.request.SignupRequest;
import com.infoschool.infoschool.dto.request.UserDto;
import com.infoschool.infoschool.dto.request.UserDtoForm;
import com.infoschool.infoschool.dto.request.UserRegistrarionToCourseDto;
import com.infoschool.infoschool.mapper.UserMapper;
import com.infoschool.infoschool.model.Course;
import com.infoschool.infoschool.model.Role;
import com.infoschool.infoschool.model.User;
import com.infoschool.infoschool.repository.RoleRepository;
import com.infoschool.infoschool.repository.UserRepository;
import com.infoschool.infoschool.util.ERole;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CourseService courseService;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserMapper userMapper;

    public UserDto registerUser(UserDtoForm user) {
        try {
            log.info("Adding new user: {}", user);
            User newUser = new User();
            newUser.setName(user.getName());
            newUser.setSurname(user.getSurname());
            newUser.setEmail(user.getEmail());
            newUser.setPassword(user.getPassword());
            newUser.setAddress(user.getAddress());
            newUser.setBirthDate(user.getBirthDate());

            Role role = roleRepository.findById(user.getRole().getId())
                    .orElseThrow(() -> new RuntimeException("Error: Role not found."));
            newUser.setRole(role);

            User savedUser = userRepository.save(newUser);
            return userMapper.userToDto(savedUser);
        } catch (Exception e) {
            log.error("Error adding user: {}", user, e);
            return null;
        }
    }

    public UserDto addUser(SignupRequest user) {
        try {
            log.info("Adding new user: {}", user);
            User newUser = new User();
            newUser.setName(user.getName());
            newUser.setSurname(user.getSurname());
            newUser.setEmail(user.getEmail());
            newUser.setPassword(user.getPassword());
            newUser.setAddress(user.getAddress());
            newUser.setBirthDate(user.getBirthDate());

            String strRole = user.getRole();
            Role role;

            if (strRole == null) {
                role = roleRepository.findByName(ERole.ROLE_USER)
                        .orElseThrow(() -> new RuntimeException("Error: Role not found."));
            } else {
                switch (strRole.toLowerCase()) {
                    case "admin":
                        role = roleRepository.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role not found."));
                        break;
                    case "teacher":
                        role = roleRepository.findByName(ERole.ROLE_TEACHER)
                                .orElseThrow(() -> new RuntimeException("Error: Role not found."));
                        break;
                    default:
                        role = roleRepository.findByName(ERole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role not found."));
                }
            }

            newUser.setRole(role);
            User savedUser = userRepository.save(newUser);
            return userMapper.userToDto(savedUser);
        } catch (Exception e) {
            log.error("Error adding user: {}", user, e);
            return null;
        }
    }

    public Optional<UserDto> getUserByEmail(String email) {
        try {
            log.info("Fetching user by email: {}", email);
            User user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("User not found"));
            return Optional.of(userMapper.userToDto(user));
        } catch (Exception e) {
            log.error("Error fetching user by email: {}", email, e);
            return Optional.empty();
        }
    }

    public Optional<UserDto> getUserById(Long id) {
        try {
            log.info("Fetching user by ID: {}", id);
            User user = userRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("User not found"));
            return Optional.of(userMapper.userToDto(user));
        } catch (Exception e) {
            log.error("Error fetching user by ID: {}", id, e);
            return Optional.empty();
        }
    }

    public void deleteUserById(Long id) {
        try {
            log.info("Deleting user by ID: {}", id);
            if (userRepository.existsById(id)) {
                userRepository.deleteById(id);
            } else {
                log.warn("User not found for deletion: {}", id);
                throw new RuntimeException("User not found");
            }
        } catch (Exception e) {
            log.error("Error deleting user by ID: {}", id, e);
            throw new RuntimeException("Error deleting user by ID: " + e.getMessage(), e);
        }
    }

    public boolean existsByEmail(String email) {
        try {
            log.info("Checking if user exists by email: {}", email);
            return userRepository.existsByEmail(email);
        } catch (Exception e) {
            log.error("Error checking if user exists by email: {}", email, e);
            return false;
        }
    }

    public UserDto edit(UserDto user) {
        try {
            log.info("Editing user: {}", user);
            User existingUser = userRepository.findById(user.getId())
                    .orElseThrow(() -> new RuntimeException("User not found"));
            existingUser.setName(user.getName());
            existingUser.setSurname(user.getSurname());
            existingUser.setEmail(user.getEmail());
            existingUser.setAddress(user.getAddress());
            existingUser.setBirthDate(user.getBirthDate());

            Role role = roleRepository.findById(user.getRoleId())
                    .orElseThrow(() -> new RuntimeException("Role not found"));
            existingUser.setRole(role);

            User updatedUser = userRepository.save(existingUser);
            return userMapper.userToDto(updatedUser);
        } catch (Exception e) {
            log.error("Error editing user: {}", user, e);
            return null;
        }
    }

    public UserDto registration(UserRegistrarionToCourseDto registration) {
        try {
            Course course = courseService.getById(registration.getCourseId());
            if (course != null) {
                User user = userRepository.findById(registration.getUserId())
                        .orElseThrow(() -> new RuntimeException("User not found"));
                user.getCourses().add(course);
                course.getStudents().add(user);
                userRepository.save(user);
                courseService.edit(course);

                UserDto userDto = userMapper.userToDto(user);
                log.info("User {} registered to course {}", user.getName(), course.getName());
                return userDto;
            } else {
                log.warn("Course not found for registration: {}", registration.getCourseId());
                throw new RuntimeException("Course not found");
            }
        } catch (Exception e) {
            log.error("Error registering user: {} for course: {}", registration.getUserId(), registration.getCourseId(), e);
            return null;
        }
    }
}