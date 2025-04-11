package com.infoschool.infoschool.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.infoschool.infoschool.dto.request.UserDtoForm;
import com.infoschool.infoschool.model.User;
import com.infoschool.infoschool.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserService {
    
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public User addUser(UserDtoForm user) {
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
            userRepository.deleteById(id);
        } catch (Exception e) {
            log.error("Error deleting user by ID: {}", id, e);
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


}
