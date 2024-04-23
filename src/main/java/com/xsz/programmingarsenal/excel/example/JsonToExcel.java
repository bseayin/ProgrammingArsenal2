package com.xsz.programmingarsenal.excel.example;

import com.alibaba.fastjson.JSON;
//import com.xsz.programmingarsenal.json.example.User1;
import lombok.Data;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
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
    //1.读取json文件内容，转成java对象
    //2.对象写入excel
    @Test
    public void test5(){
        // 读取json文件内容转成Array
        String jsonString = getJsonString();
        List<User1> users1 = JSON.parseArray(jsonString, User1.class);
        System.out.println(users1);

        // result
        // [User(username=jsonuser1, password=11111, isvalid=false), User(username=jsonuser2, password=22222, isvalid=false), User(username=jsonuser3, password=3333, isvalid=false)]
    }
}
@Data
class User1{
    String username;
    Long password;
    Boolean isvalid;

}
