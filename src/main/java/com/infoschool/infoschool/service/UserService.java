package com.infoschool.infoschool.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infoschool.infoschool.dto.request.SignupRequest;
import com.infoschool.infoschool.dto.request.UserDto;
import com.infoschool.infoschool.dto.request.UserDtoForm;
import com.infoschool.infoschool.dto.request.UserRegistrarionToCourseDto;
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
    private final UserRepository userRepository;
    @Autowired
    private final CourseService courseService;
    @Autowired
    private final RoleRepository roleRepository;

    public UserService(UserRepository userRepository, CourseService courseService, RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.courseService = courseService;
    }



    public User registerUser(UserDtoForm user) {
        try {
            log.info("Adding new user: {}", user);
            User newUser = new User();
            newUser.setName(user.getName());
            newUser.setSurname(user.getSurname());
            newUser.setEmail(user.getEmail());
            newUser.setPassword(user.getPassword());
            newUser.setAddress(user.getAddress());
            newUser.setBirthDate(user.getBirthDate());
            newUser.setRole(user.getRole());
            return userRepository.save(newUser);
        } catch (Exception e) {
            log.error("Error adding user: {}", user, e);
            return null;
        }
    }

    public User addUser(SignupRequest user) {
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
            Role role = new Role();

            if (strRole == null) {
                role = roleRepository.findByName(ERole.ROLE_USER)
                        .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            } else {
                    switch (strRole.toLowerCase()) {
                        case "admin":
                            Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                            role = adminRole;
                            break;
                        case "teacher":
                            role = roleRepository.findByName(ERole.ROLE_TEACHER)
                                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                            break;
                        default:
                            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                            role = userRole;
                    }
            }

            newUser.setRole(role);
            return userRepository.save(newUser);
        } catch (Exception e) {
            log.error("Error adding user: {}", user, e);
            return null;
        }
    }

    public Optional<User> getUserByEmail(String email) {
        try {
            log.info("Fetching user by email: {}", email);
            return userRepository.findByEmail(email);
        } catch (Exception e) {
            log.error("Error fetching user by email: {}", email, e);
            return Optional.empty();
        }
    }

    public Optional<User> getUserById(Long id) {
        try {
            log.info("Fetching user by ID: {}", id);
            return userRepository.findById(id);
        } catch (Exception e) {
            log.error("Error fetching user by ID: {}", id, e);
            return Optional.empty();
        }
    }
    public void deleteUserById(Long id) {
        try {
            log.info("Deleting user by ID: {}", id);
            Optional<User> user = userRepository.findById(id);
            if (user.isPresent()) {
                userRepository.deleteById(id);
            } else {
                log.warn("User not found for deletion: {}", id);
                throw new RuntimeException("User not found");
            }
        } catch (RuntimeException e) {
            log.error("Error deleting user by ID: {}", id, e);
            throw new RuntimeException("Error deleting user by ID: " + e.getMessage(), e);
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

    public User edit(UserDto user) {
        try {
            log.info("Editing user: {}", user);
            User existingUser = userRepository.findById(user.getId()).orElse(null);
            if (existingUser != null) {
                existingUser.setName(user.getName());
                existingUser.setSurname(user.getSurname());
                existingUser.setEmail(user.getEmail());
                existingUser.setAddress(user.getAddress());
                existingUser.setBirthDate(user.getBirthDate());
                Role role = roleRepository.findById(user.getRoleId()).orElse(null);
                if (role != null) {
                    existingUser.setRole(role);
                } else {
                    log.warn("Role not found for user: {}", user.getRoleId());
                    throw new RuntimeException("Role not found");
                }
                return userRepository.save(existingUser);
            } else {
                log.warn("User not found for editing: {}", user);
                return null;
            }
        } catch (Exception e) {
            log.error("Error editing user: {}", user, e);
            return null;
        }
    }

    public User registration(UserRegistrarionToCourseDto registration){
        try {
            Course course = courseService.getById(registration.getCourseId());
            if(course != null) {
                User user = userRepository.findById(registration.getUserId()).orElse(null);
                if(user != null) {
                    user.getCourses().add(course);
                    course.getStudents().add(user);
                    userRepository.save(user);
                    courseService.edit(course);
                    return user;
                } else {
                    log.warn("User not found for registration: {}", registration.getUserId());
                    throw new RuntimeException("User not found");
                }
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
