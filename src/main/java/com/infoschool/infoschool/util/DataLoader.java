package com.infoschool.infoschool.util;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.infoschool.infoschool.model.Course;
import com.infoschool.infoschool.model.Role;
import com.infoschool.infoschool.model.Subject;
import com.infoschool.infoschool.model.TeachedSubject;
import com.infoschool.infoschool.model.User;
import com.infoschool.infoschool.repository.CourseRepository;
import com.infoschool.infoschool.repository.RoleRepository;
import com.infoschool.infoschool.repository.SubjectRepository;
import com.infoschool.infoschool.repository.TeachedSubjectRepositiry;
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
  private TeachedSubjectRepositiry teachedSubjectRepositiry;

  @Autowired
  private PasswordEncoder encoder;

  @Override
  public void run(String... args) throws Exception {
    if (roleRepository.findAll().isEmpty()) {
        Role roleAdmin = new Role();
        roleAdmin.setName(ERole.ROLE_ADMIN);
        roleRepository.save(roleAdmin);

        Role roleTeacher = new Role();
        roleTeacher.setName(ERole.ROLE_TEACHER);
        roleRepository.save(roleTeacher);

        Role roleUser = new Role();
        roleUser.setName(ERole.ROLE_USER);
        roleRepository.save(roleUser);
    }

    if (userRepository.findAll().isEmpty()) {
      Role role = roleRepository.findByName(ERole.ROLE_ADMIN).get();
      Role role2 = roleRepository.findByName(ERole.ROLE_USER).get();
      Role role3 = roleRepository.findByName(ERole.ROLE_TEACHER).get();

      User admin = new User();
      admin.setName("admin");
      admin.setSurname("admin");
      admin.setPassword(encoder.encode("admin"));
      admin.setRole(role);
      admin.setEmail("admin@admin.com");
      admin.setAddress("Via Roma 1, Milano");
      admin.setBirthDate(LocalDate.of(1990, 1, 1));
      userRepository.save(admin);

      User teacher = new User();
      teacher.setName("Mario");
      teacher.setSurname("Rossi");
      teacher.setPassword(encoder.encode("password"));
      teacher.setRole(role3);
      teacher.setEmail("mario@rossi.com");
      teacher.setAddress("Via Roma 1, Milano");
      teacher.setBirthDate(LocalDate.of(1990, 1, 1));
      userRepository.save(teacher);

      User user = new User();
      user.setName("Mattia");
      user.setSurname("Verdi");
      user.setPassword(encoder.encode("password"));
      user.setRole(role2);
      user.setEmail("matia@verdi.com");
      user.setAddress("Via Roma 1, Milano");
      user.setBirthDate(LocalDate.of(2004, 1, 1));
      userRepository.save(user);

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

    if (teachedSubjectRepositiry.findAll().isEmpty()) {

      User teacher = userRepository.findByEmail("mario@rossi.com").get();
      Subject subject = subjectRepository.findByName("React").get();

      TeachedSubject teachedSubject = new TeachedSubject();
      teachedSubject.setStartDate(LocalDate.of(2023, 1, 1));
      teachedSubject.setEndDate(LocalDate.of(2023, 6, 1));
      teachedSubject.setSubject(subject);
      teachedSubject.setTeacher(teacher);
      teachedSubjectRepositiry.save(teachedSubject);
    }
  }
}
