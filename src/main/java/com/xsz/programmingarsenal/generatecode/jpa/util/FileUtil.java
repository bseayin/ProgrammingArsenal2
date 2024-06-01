package com.xsz.programmingarsenal.generatecode.jpa.util;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 生成文件
 * @author ChenTian
 */
@Slf4j
public class FileUtil {

    /**
     * 生成文件
     * @param outputPath 文件所在目录
     * @param filePath 文件路径
     * @param fileContent 文件内容
     */
    public static void genFile(String outputPath, String filePath, String fileContent){
        try {
            File outPutFile = new File(outputPath);
            if (!outPutFile.exists()) {
                outPutFile.mkdirs();
            }

            FileWriter fw = new FileWriter(filePath);
            PrintWriter pw = new PrintWriter(fw);
            pw.println(fileContent);
            pw.flush();
            pw.close();
        } catch (IOException e) {
            log.error("error:",e);
        }
    }
}
