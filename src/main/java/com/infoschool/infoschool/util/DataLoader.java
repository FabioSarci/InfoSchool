package com.infoschool.infoschool.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.infoschool.infoschool.model.Role;
import com.infoschool.infoschool.model.User;
import com.infoschool.infoschool.repository.RoleRepository;
import com.infoschool.infoschool.repository.UserRepository;

import org.springframework.boot.CommandLineRunner;

@Component
public class DataLoader implements CommandLineRunner {
  @Autowired
  private RoleRepository roleRepository;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private PasswordEncoder encoder;

  @Override
  public void run(String... args) throws Exception {
    if (roleRepository.findAll().isEmpty()) {
        Role roleAdmin = new Role();
        roleAdmin.setName(ERole.ROLE_ADMIN);
        roleRepository.save(roleAdmin);

        Role roleModerator = new Role();
        roleModerator.setName(ERole.ROLE_TEACHER);
        roleRepository.save(roleModerator);

        Role roleUser = new Role();
        roleUser.setName(ERole.ROLE_USER);
        roleRepository.save(roleUser);
    }

    if (userRepository.findAll().isEmpty()) {
      Role role = roleRepository.findByName(ERole.ROLE_ADMIN).get();

      User admin = new User();
      admin.setName("admin");
      admin.setSurname("admin");
      admin.setPassword(encoder.encode("admin"));
      admin.setRole(role);
      admin.setEmail("admin@admin.com");
      userRepository.save(admin);
    }
  }
    
}
