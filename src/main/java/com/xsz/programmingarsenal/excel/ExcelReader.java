package com.xsz.programmingarsenal.excel;

import org.apache.poi.ss.usermodel.*;
//import org.apache.poi.ss.formula.FormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class ExcelReader {

    public static void main(String[] args) {
        try (FileInputStream fis = new FileInputStream(new File("path/to/your/file.xlsx"))) {
            Workbook workbook = WorkbookFactory.create(fis);
            Sheet sheet = workbook.getSheetAt(0); // 获取第一个工作表
            FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();

            for (Row row : sheet) {
                for (Cell cell : row) {
                    CellType cellType = cell.getCellType();
                    if (cellType == CellType.FORMULA) {
                        // 使用公式评估器来计算公式的结果
                        CellValue cellValue = evaluator.evaluate(cell);
                        if (cellValue.getCellType() == CellType.NUMERIC) {
                            System.out.println("Formula result: " + cellValue.getNumberValue());
                        } else if (cellValue.getCellType() == CellType.STRING) {
                            System.out.println("Formula result: " + cellValue.getStringValue());
                        }
                    } else if (cellType == CellType.NUMERIC) {
                        System.out.println("Numeric value: " + cell.getNumericCellValue());
                    } else if (cellType == CellType.STRING) {
                        System.out.println("String value: " + cell.getStringCellValue());
                    }
                }
            }

            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
