package com.xsz.programmingarsenal.excel;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class ExcelUtilsTest {


    @Test
    void readExcel() {
        List<List<Object>> data = null;
        try {
            data = ExcelUtils.readExcel("C:\\Users\\Brad\\Downloads\\wechatpay-java-main (1)\\ProgrammingArsenal\\src\\main\\java\\com\\xsz\\programmingarsenal\\excel\\example\\file.xlsx");
        } catch (IOException e) {
            e.printStackTrace();
        }

// 打印读取的数据
        for (List<Object> row : data) {
            for (Object cell : row) {
                System.out.print(cell + "\t");
            }
            System.out.println();
        }
    }

    @Test
    void writeExcel() {
        List<List<Object>> data = new ArrayList<>();
        data.add(Arrays.asList("John Doe", 30, "Developer"));
        data.add(Arrays.asList("Jane Doe", 28, "Designer"));

        try {
            ExcelUtils.writeExcel("C:\\Users\\Brad\\Downloads\\wechatpay-java-main (1)\\ProgrammingArsenal\\src\\main\\java\\com\\xsz\\programmingarsenal\\excel\\example\\file.xlsx", data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void readExcelAsMap() {
        List<Map<String, String>> data = null;
        try {
            data = ExcelUtils.readExcelAsMap("C:\\Users\\Brad\\Downloads\\wechatpay-java-main (1)\\ProgrammingArsenal\\src\\main\\java\\com\\xsz\\programmingarsenal\\excel\\example\\file.xlsx");
        } catch (IOException e) {
            e.printStackTrace();
        }

// 打印读取的数据
        for (Map<String, String> row : data) {
            for (Map.Entry<String, String> entry : row.entrySet()) {
                System.out.print(entry.getKey() + ": " + entry.getValue() + "\t");
            }
            System.out.println();
        }
    }



    @Test
    void testWriteMapExcel() {
        List<Map<String, String>> data = new ArrayList<>();
        Map<String, String> row1 = new LinkedHashMap<>();
        row1.put("Name", "John Doe");
        row1.put("Age", "30");
        row1.put("Role", "Developer");
        data.add(row1);

        Map<String, String> row2 = new LinkedHashMap<>();
        row2.put("Name", "Jane Doe");
        row2.put("Age", "28");
        row2.put("Role", "Designer");
        data.add(row2);

        try {
            ExcelUtils.writeMapExcel("C:\\Users\\Brad\\Downloads\\wechatpay-java-main (1)\\ProgrammingArsenal\\src\\main\\java\\com\\xsz\\programmingarsenal\\excel\\example\\file2.xlsx", data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}