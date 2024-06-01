package com.xsz.programmingarsenal.generatecode.jpa.util;

/**
 * @author Chentian
 * @date 2022/4/6
 */
public class StrUtil {

    /**
     * 功能：字符串删除指定前缀
     */
    public static String subStrByPrefix(String str, String prefix) {
        return str.indexOf(prefix) == 0 ? str.substring(prefix.length()) : str;
    }

    /**
     * 功能：将以下划线分隔的字符串首字母转换为大写
     * 示例：user_info -> UserInfo
     */
    public static String initcap(String str) {
        String[] arr = str.split("_");
        String result = "";
        if (arr.length == 1) {
            return upperFirst(str.toLowerCase());
        }
        for (int i = 0; i < arr.length; i++) {
            if (i == 0) {
                char[] ch = upperFirst(arr[i].toLowerCase()).toCharArray();
                if (ch[0] >= 'a' && ch[0] <= 'z') {
                    ch[0] = (char) (ch[0] - 32);
                }
                result += new String(ch);
            } else {
                result += upperFirst(arr[i].toLowerCase());
            }
        }
        return result;
    }

    /**
     * 功能：将输入字符串的首字母改成小写及将以下划线分隔的字符串首字母转换为大写
     * 示例: user_name -> userName
     */
    public static String initcap2(String str) {
        String[] arr = str.split("_");
        String result = "";
        if (arr.length == 1) {
            return lowerFirst(str.toLowerCase());
        }
        for (int i = 0; i < arr.length; i++) {
            if (i == 0) {
                char[] ch = upperFirst(arr[i].toLowerCase()).toCharArray();
                if (ch[0] >= 'A' && ch[0] <= 'Z') {
                    ch[0] = (char) (ch[0] + 32);
                }
                result += new String(ch);
            } else {
                result += upperFirst(arr[i].toLowerCase());
            }
        }
        return result;
    }

    /**
     * 将字符串首字母转成小写
     */
    public static String lowerFirst(String str) {
        char[] ch = str.toCharArray();
        if (ch[0] >= 'A' && ch[0] <= 'Z') {
            ch[0] = (char) (ch[0] + 32);
        }
        return new String(ch);
    }

    /**
     * 将字符串首字母转成大写
     */
    public static String upperFirst(String str) {
        char[] ch = str.toCharArray();
        if (ch[0] >= 'a' && ch[0] <= 'z') {
            ch[0] = (char) (ch[0] - 32);
        }
        return new String(ch);
    }

    public static void main(String[] args) {
        System.out.println(initcap2("t_trace_id_scan_request_count"));
    }
}
