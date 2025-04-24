package com.infoschool.infoschool.service.export;

import com.infoschool.infoschool.dto.request.UserDto;
import com.infoschool.infoschool.mapper.UserMapper;
import com.infoschool.infoschool.model.User;
import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PdfGenerationService {

    @Autowired
    private UserMapper userMapper;

    public void generateStudentProfilePdf(UserDto student, String filePath) throws Exception {

        User studentEntity = userMapper.dtoToUser(student);

        PdfWriter writer = new PdfWriter(filePath);

        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);

        document.add(new Paragraph("Profilo Studente").setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD)).setFontSize(16));
        document.add(new Paragraph("ID: " + studentEntity.getId()));
        document.add(new Paragraph("Nome: " + studentEntity.getName()));
        document.add(new Paragraph("Cognome: " + studentEntity.getSurname()));
        document.add(new Paragraph("Email: " + studentEntity.getEmail()));
        document.add(new Paragraph("Indirizzo: " + studentEntity.getAddress()));
        document.add(new Paragraph("Data di nascita: " + studentEntity.getBirthDate()));
        document.add(new Paragraph("Ruolo: " + (studentEntity.getRole() != null ? studentEntity.getRole().getName().toString() : "N/A")));

        document.close();
    }
}
