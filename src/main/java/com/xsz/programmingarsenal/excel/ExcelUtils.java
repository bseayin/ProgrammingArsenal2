package com.xsz.programmingarsenal.excel;

import org.apache.poi.ss.usermodel.*;
//import org.apache.poi.ss.formula.FormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ExcelUtils {

    private static final String FILE_EXTENSION_XLS = ".xls";
    private static final String FILE_EXTENSION_XLSX = ".xlsx";

    /**
     * 读取Excel文件中的数据。
     *
     * @param filePath Excel文件的路径。
     * @return 读取的数据列表。
     * @throws IOException 如果文件读取失败。
     */
    public static List<List<Object>> readExcel(String filePath) throws IOException {
        List<List<Object>> data = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream(new File(filePath))) {
            Workbook workbook = WorkbookFactory.create(fis);
            Sheet sheet = workbook.getSheetAt(0); // 获取第一个工作表
            FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();

            for (Row row : sheet) {
                List<Object> rowData = new ArrayList<>();
                for (Cell cell : row) {
                    CellType cellType = cell.getCellType();
                    if (cellType == CellType.FORMULA) {
                        // 使用公式评估器来计算公式的结果
                        CellValue cellValue = evaluator.evaluate(cell);
                        rowData.add(getCellValue(cellValue));
                    } else if (cellType == CellType.NUMERIC) {
                        rowData.add(cell.getNumericCellValue());
                    } else if (cellType == CellType.STRING) {
                        rowData.add(cell.getStringCellValue());
                    } else if (cellType == CellType.BLANK) {
                        rowData.add(null);
                    }
                }
                data.add(rowData);
            }

            workbook.close();
        }

        return data;
    }

    /**
     * 写入数据到Excel文件。
     *
     * @param filePath 文件路径。
     * @param data     要写入的数据列表。
     * @throws IOException 如果文件写入失败。
     */
    public static void writeExcel(String filePath, List<List<Object>> data) throws IOException {
        Workbook workbook = createWorkbook(filePath);
        Sheet sheet = workbook.createSheet("Sheet1");

        for (int i = 0; i < data.size(); i++) {
            Row row = sheet.createRow(i);
            List<Object> rowData = data.get(i);
            for (int j = 0; j < rowData.size(); j++) {
                Cell cell = row.createCell(j);
                setCellValue(cell, rowData.get(j));
            }
        }

        try (FileOutputStream fos = new FileOutputStream(filePath)) {
            workbook.write(fos);
            workbook.close();
        }
    }

    /**
     * 读取Excel文件中的数据，并将其转换为 List<Map<String, String>>。
     *
     * @param filePath Excel文件的路径。
     * @return 读取的数据列表。
     * @throws IOException 如果文件读取失败。
     */
    public static List<Map<String, String>> readExcelAsMap(String filePath) throws IOException {
        List<Map<String, String>> data = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream(new File(filePath))) {
            Workbook workbook = WorkbookFactory.create(fis);
            Sheet sheet = workbook.getSheetAt(0); // 获取第一个工作表
            FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
            List<String> headers = new ArrayList<>();

            // 读取头部信息
            Row headerRow = sheet.getRow(0);
            for (Cell headerCell : headerRow) {
                headers.add(getStringCellValue(headerCell));
            }

            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue; // 跳过头部行
                Map<String, String> rowData = new LinkedHashMap<>();
                for (int i = 0; i < headers.size(); i++) {
                    Cell cell = row.getCell(i);
                    if (cell != null) {
                        CellType cellType = cell.getCellType();
                        if (cellType == CellType.FORMULA) {
                            // 使用公式评估器来计算公式的结果
                            CellValue cellValue = evaluator.evaluate(cell);
                            rowData.put(headers.get(i), getStringCellValue(cell));
                        } else if (cellType == CellType.NUMERIC) {
                            rowData.put(headers.get(i), Double.toString(cell.getNumericCellValue()));
                        } else if (cellType == CellType.STRING) {
                            rowData.put(headers.get(i), cell.getStringCellValue());
                        } else if (cellType == CellType.BLANK) {
                            rowData.put(headers.get(i), "");
                        }
                    } else {
                        rowData.put(headers.get(i), "");
                    }
                }
                data.add(rowData);
            }

            workbook.close();
        }

        return data;
    }

    /**
     * 写入数据到Excel文件。
     *
     * @param filePath 文件路径。
     * @param data     要写入的数据列表。
     * @throws IOException 如果文件写入失败。
     */
    public static void writeMapExcel(String filePath, List<Map<String, String>> data) throws IOException {
        Workbook workbook = createWorkbook(filePath);
        Sheet sheet = workbook.createSheet("Sheet1");

        // 写入头部
        Row headerRow = sheet.createRow(0);
        int colIndex = 0;
        if (!data.isEmpty()) {
            for (String header : data.get(0).keySet()) {
                Cell cell = headerRow.createCell(colIndex++);
                cell.setCellValue(header);
            }
        }

        // 写入数据
        for (int i = 0; i < data.size(); i++) {
            Row row = sheet.createRow(i + 1); // 从第二行开始写入数据
            Map<String, String> rowData = data.get(i);
            colIndex = 0;
            for (String value : rowData.values()) {
                Cell cell = row.createCell(colIndex++);
                cell.setCellValue(value);
            }
        }

        try (FileOutputStream fos = new FileOutputStream(filePath)) {
            workbook.write(fos);
            workbook.close();
        }
    }

    /**
     * 创建Workbook实例。
     *
     * @param filePath 文件路径。
     * @return Workbook实例。
     */
    private static Workbook createWorkbook(String filePath) {
        if (filePath.endsWith(FILE_EXTENSION_XLSX)) {
            return new XSSFWorkbook();
        } else if (filePath.endsWith(FILE_EXTENSION_XLS)) {
            return new org.apache.poi.hssf.usermodel.HSSFWorkbook();
        } else {
            throw new IllegalArgumentException("Unsupported file extension: " + filePath);
        }
    }

    /**
     * 设置单元格的值。
     *
     * @param cell 单元格对象。
     * @param value 单元格的值。
     */
    private static void setCellValue(Cell cell, Object value) {
        if (value instanceof Number) {
            cell.setCellValue(((Number) value).doubleValue());
        } else if (value instanceof String) {
            cell.setCellValue((String) value);
        } else if (value == null) {
            cell.setCellType(CellType.BLANK);
        }
    }

    /**
     * 获取单元格的值。
     *
     * @param cellValue 包含计算结果的 CellValue 对象。
     * @return 单元格的值。
     */
    private static Object getCellValue(CellValue cellValue) {
        switch (cellValue.getCellType()) {
            case NUMERIC:
                return cellValue.getNumberValue();
            case STRING:
                return cellValue.getStringValue();
            default:
                return null;
        }
    }
    /**
     * 获取单元格的值（字符串形式）。
     *
     * @return 单元格的值。
     */
    public static  String getStringCellValue(Cell cell ){
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
