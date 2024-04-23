package com.xsz.programmingarsenal.file.example;

import com.alibaba.fastjson.JSON;
import com.xsz.programmingarsenal.file.FileUtil;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JsonToExcel {
    public static String path = "src/main/resources/data/testData.json";

    //获取json文件内容转为json字符串
    public static String getJsonString() {
        BufferedReader bufferedReader = null;
        String len = null;
        StringBuilder de = new StringBuilder();
        try {
            bufferedReader = new BufferedReader(new FileReader(path));

            while ((len = bufferedReader.readLine()) != null) {
                de.append(len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        assert de != null;
        return de.toString();
    }
    public static void main(String[] args) {
        jsonFileToCSV();
    }

    static  void  objectToCSV(){
        List<DemoData> list = new ArrayList<>();
        DemoData demoData=new DemoData();
        demoData.setUsername("tname");
        demoData.setPassword(345l);
        demoData.setIsvalid(false);
        list.add(demoData);
        FileUtil.createCsv("投票项目表", list, DemoData.class);
    }

    static  void  jsonFileToCSV(){
        String jsonString = getJsonString();
        List<DemoData> list = JSON.parseArray(jsonString, DemoData.class);
        FileUtil.createCsv("投票项目表", list, DemoData.class);
    }

}
