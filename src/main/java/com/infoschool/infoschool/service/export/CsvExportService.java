package com.infoschool.infoschool.service.export;

import java.io.PrintWriter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infoschool.infoschool.model.User;
import com.infoschool.infoschool.repository.UserRepository;

@Service
public class CsvExportService {

    @Autowired
    private UserRepository userRepository;

    public void exportUsersToCsv(PrintWriter writer) {
        writer.println("ID,Name,Surname,Email,Ruolo");

        List<User> users = userRepository.findAll();

        for (User user : users) {
            writer.println(
                user.getId() + "," +
                user.getName() + "," +
                user.getSurname() + "," +
                user.getEmail() + "," +
                (user.getRole() != null ? user.getRole().getName() : "")
            );
        }
    }
}
