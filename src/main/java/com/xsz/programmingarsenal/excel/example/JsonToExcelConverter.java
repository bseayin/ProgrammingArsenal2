package com.xsz.programmingarsenal.excel.example;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class JsonToExcelConverter {

    public static void main(String[] args) {
        String jsonFilePath = "C:\\Users\\Brad\\Downloads\\wechatpay-java-main (1)\\ProgrammingArsenal\\src\\main\\resources\\data\\file.json";
        String excelFilePath = "C:\\Users\\Brad\\Downloads\\wechatpay-java-main (1)\\ProgrammingArsenal\\src\\main\\resources\\data\\file.xlsx";
        convertJsonToExcel(jsonFilePath, excelFilePath);
    }

    public static void convertJsonToExcel(String jsonFilePath, String excelFilePath) {
        try {
            // Read the JSON file
            String jsonContent = new String(Files.readAllBytes(Paths.get(jsonFilePath)));
            JSONArray jsonArray = JSON.parseArray(jsonContent);

            // Create an Excel workbook and a sheet
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("Users");

            // Create the header row
            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("Name");
            headerRow.createCell(1).setCellValue("Age");

            // Populate the data
            int rowNum = 1;
            for (Object obj : jsonArray) {
                JSONObject jsonObject = (JSONObject) obj;
                User user = JSON.toJavaObject(jsonObject, User.class);

                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(user.getName());
                row.createCell(1).setCellValue(String.valueOf(user.getAge()));
            }

            // Write to the Excel file
            try (FileOutputStream outputStream = new FileOutputStream(excelFilePath)) {
                workbook.write(outputStream);
            }

            System.out.println("Excel file created successfully!");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}