package com.infoschool.infoschool.service.export;

import java.io.OutputStream;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import com.infoschool.infoschool.model.Course;
import com.infoschool.infoschool.model.Subject;

@Service
public class ExcelExportService {


    public void exportCoursesAndSubjectsToExcel(OutputStream outputStream, List<Course> courses) throws Exception {

        Workbook workbook = new XSSFWorkbook();

        Sheet sheet = workbook.createSheet("Courses of study");

    Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Course ID");
        headerRow.createCell(1).setCellValue("Cpurse Name");
        headerRow.createCell(2).setCellValue("Year");
        headerRow.createCell(3).setCellValue("Projects");

        int rowNum = 1;
        for (Course course : courses) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(course.getId());
            row.createCell(1).setCellValue(course.getName());
            row.createCell(2).setCellValue(course.getYear());

            StringBuilder subjects = new StringBuilder();
            for (Subject subject : course.getSubjects()) {
                subjects.append(subject.getName()).append(", ");
            }
            row.createCell(3).setCellValue(subjects.length() > 0 ? subjects.substring(0, subjects.length() - 2) : "N/A");
        }

        workbook.write(outputStream);
        workbook.close();
    }
    
}
