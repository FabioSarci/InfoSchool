package com.infoschool.infoschool.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.infoschool.infoschool.model.Course;
import com.infoschool.infoschool.model.Role;
import com.infoschool.infoschool.model.Subject;
import com.infoschool.infoschool.model.User;
import com.infoschool.infoschool.repository.CourseRepository;
import com.infoschool.infoschool.repository.RoleRepository;
import com.infoschool.infoschool.repository.SubjectRepository;
import com.infoschool.infoschool.repository.UserRepository;

import org.springframework.boot.CommandLineRunner;

@Component
public class DataLoader implements CommandLineRunner {
  @Autowired
  private RoleRepository roleRepository;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private CourseRepository courseRepository;

  @Autowired
  private SubjectRepository subjectRepository;

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

    if (courseRepository.findAll().isEmpty()) {
      Course course = new Course();
      course.setName("Sviluppo Web");
      course.setYear(2025);
      course.setCode("SW-2025");
      course.setDescription("Corso di sviluppo web full stack");
      courseRepository.save(course);

      Course course2 = new Course();
      course2.setName("Sviluppo Software");
      course2.setYear(2025);
      course2.setCode("SS-2025");
      course2.setDescription("Corso di sviluppo software full stack");
      courseRepository.save(course2);

      Course course3 = new Course();
      course3.setName("Cyber Security");
      course3.setYear(2025);
      course3.setCode("CS-2025");
      course3.setDescription("Corso di cyber security");
      courseRepository.save(course3);
    }

    if (subjectRepository.findAll().isEmpty()) {
      Subject subject = new Subject();
      subject.setName("React");
      subject.setDescription("Materia di React JS");
      subjectRepository.save(subject);

      Subject subject2 = new Subject();
      subject2.setName("Python");
      subject2.setDescription("Materia di Python");
      subjectRepository.save(subject2);

      Subject subject3 = new Subject();
      subject3.setName("Ingegneria del Software");
      subject3.setDescription("Materia di ingegneria del software");
      subjectRepository.save(subject3);
    }
  }
    
}
