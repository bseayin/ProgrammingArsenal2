package com.xsz.programmingarsenal.generatecode.jpa.tool;


import com.xsz.programmingarsenal.generatecode.jpa.CodeGenTool;
import com.xsz.programmingarsenal.generatecode.jpa.util.FreemarkerTool;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * base 依赖类生成
 * @author ChenTian
 * @date 2022/4/6
 */
public class BaseClassGen {

    private static FreemarkerTool freemarkerTool = new FreemarkerTool();

    public static void genBaseClass() {
        String path = System.getProperty("user.dir")+"/"+ CodeGenTool.modulePath;
        String outputPath = path + "/src/main/java/" + CodeGenTool.basePackageOutPath.replace(".", "/");
        File outPutFile = new File(outputPath);
        if (!outPutFile.exists()) {
            outPutFile.mkdirs();
        }
        Map<String, Object> params = new HashMap<>();
        params.put("basePackage", CodeGenTool.basePackageOutPath);
        params.put("author", CodeGenTool.authorName);

        List<String> baseList = Arrays.asList("BaseDao","BaseService","BaseServiceImpl","BaseController");
        for (String base: baseList){
            String template = base+".ftl";
            String filePath = outputPath + "/"+base+".java";
            genFile(template, params, filePath);
        }

    }

    public static void genFile(String template, Map<String, Object> params, String filePath){
        try {
            freemarkerTool.processString(template, params, new File(filePath));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
