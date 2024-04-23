package com.xsz.programmingarsenal.json.example;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import lombok.Data;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyJsonUtils {
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

    @Test
    public void test1(){
        // Object 对象转 json字符串
        User user = new User();
        user.setIsvalid(true);
        user.setPassword(111L);
        user.setUsername("demo1");
        String s = JSON.toJSONString(user);
        System.out.println(s);
        // result
        // {"isvalid":true,"password":111,"username":"demo1"}
    }

    @Test
    public void test2(){
        // List 对象转 json字符串
        User user1 = new User();
        user1.setIsvalid(true);
        user1.setPassword(111L);
        user1.setUsername("demo1");

        User user2 = new User();
        user2.setIsvalid(true);
        user2.setPassword(111L);
        user2.setUsername("demo1");

        ArrayList<User> users = new ArrayList<>();
        users.add(user1);
        users.add(user2);

        String s = JSON.toJSONString(users);
        System.out.println(s);
        // result
        // [{"isvalid":true,"password":111,"username":"demo1"},{"isvalid":true,"password":111,"username":"demo1"}]
    }

    @Test
    public void test3(){
        // Map 对象转 json字符串
        User user1 = new User();
        user1.setIsvalid(true);
        user1.setPassword(111L);
        user1.setUsername("demo1");

        User user2 = new User();
        user2.setIsvalid(true);
        user2.setPassword(111L);
        user2.setUsername("demo1");

        HashMap<String, User> userMap = new HashMap<>();
        userMap.put("user1",user1);
        userMap.put("user2",user2);


        String s = JSON.toJSONString(userMap);
        System.out.println(s);
        // result
        // {"user1":{"isvalid":true,"password":111,"username":"demo1"},"user2":{"isvalid":true,"password":111,"username":"demo1"}}
    }

    @Test
    public void test4(){
        //  json字符串转Map对象
        String str1 = "{\"user1\":{\"isvalid\":true,\"password\":111,\"username\":\"demo1\"},\"user2\":{\"isvalid\":true,\"password\":111,\"username\":\"demo1\"}}";

        Map<String, User> stringUserMap = JSON.parseObject(str1, new TypeReference<Map<String, User>>() {
        });
        System.out.println(stringUserMap);
        // result
        // {user1=User(username=demo1, password=111, isvalid=true), user2=User(username=demo1, password=111, isvalid=true)}
    }

    @Test
    public void test5(){
        //  json字符串转List对象
        String str1 = "[{\"isvalid\":true,\"password\":111,\"username\":\"demo1\"},{\"isvalid\":true,\"password\":111,\"username\":\"demo1\"}]";

        List<User> users = JSON.parseArray(str1, User.class);
        System.out.println(users);
        // [User(username=demo1, password=111, isvalid=true), User(username=demo1, password=111, isvalid=true)]

        // 读取json文件内容转成Array
        String jsonString = getJsonString();
        List<User> users1 = JSON.parseArray(jsonString, User.class);
        System.out.println(users1);
        // result
        // [User(username=jsonuser1, password=11111, isvalid=false), User(username=jsonuser2, password=22222, isvalid=false), User(username=jsonuser3, password=3333, isvalid=false)]
    }

    @Test
    public void test6(){
        //  json字符串转Object对象
        String str1 = "{\"isvalid\":true,\"password\":111,\"username\":\"demo1\"}";

        User user = JSON.parseObject(str1, User.class);
        System.out.println(user);
        // result
        // User(username=demo1, password=111, isvalid=true)

        // 不指定类型，直接根据数据类型强转
        Map<String, Object> object = JSON.parseObject(str1);
        Object username = object.get("username");
        System.out.println(username);
        // result
        // demo1
    }
}

@Data
class User{
    String username;
    Long password;
    Boolean isvalid;

}