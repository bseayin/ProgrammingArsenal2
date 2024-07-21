package com.xsz.programmingarsenal.utils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExcelReader {
    public static void main(String[] args) {
        try {
            FileInputStream fis = new FileInputStream(new File("C:\\tmp\\test.xlsx"));
            Workbook workbook = new XSSFWorkbook(fis);
            Sheet sheet = workbook.getSheetAt(0); // 假设我们只读取第一个工作表
            List<Map<String, String>> data = new ArrayList<>();

            // 获取第一行作为列头
            Row headerRow = sheet.getRow(0);
            if (headerRow != null) {
                for (int i = 1; i <= sheet.getLastRowNum(); i++) { // 从第二行开始读取数据
                    Row row = sheet.getRow(i);
                    if (row != null) {
                        Map<String, String> rowData = new HashMap<>();
                        for (Cell cell : headerRow) {
                            int colIndex = cell.getColumnIndex();
                            Cell dataCell = row.getCell(colIndex);
                            if (dataCell != null) {
//                                rowData.put(cell.getStringCellValue(), dataCell.getStringCellValue());
                                rowData.put(getStringValue(cell), getStringValue(dataCell));
                            }
                        }
                        data.add(rowData);
                    }
                }
            }

            // 关闭资源
            workbook.close();
            fis.close();

            // 打印结果
            System.out.println(data);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static  String getStringValue(Cell cell ){
        CellType cellType = cell.getCellType();
        String cellValue = null;

        switch (cellType) {
            case STRING:
                cellValue = cell.getStringCellValue();
                break;
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    // 如果是日期格式的数值，则转换为日期字符串
                    cellValue = cell.getDateCellValue().toString();
                } else {
                    // 否则，转换为数字字符串
                    cellValue = String.valueOf(cell.getNumericCellValue());
                }
                break;
            case BOOLEAN:
                cellValue = String.valueOf(cell.getBooleanCellValue());
                break;
            case FORMULA:
                // 处理公式单元格
                cellValue = cell.getCellFormula();
                break;
            case BLANK:
                cellValue = ""; // 或者null，取决于你的需求
                break;
            case ERROR:
                cellValue = "ERROR"; // 或者抛出异常，取决于你的需求
                break;
            default:
                throw new IllegalStateException("Unexpected cell type: " + cellType);
        }
        return  cellValue;

// 现在你可以安全地使用cellValue了
    }
}
