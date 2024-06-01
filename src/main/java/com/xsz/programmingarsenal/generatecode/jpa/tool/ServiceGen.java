package com.xsz.programmingarsenal.generatecode.jpa.tool;



import com.xsz.programmingarsenal.generatecode.jpa.CodeGenTool;
import com.xsz.programmingarsenal.generatecode.jpa.dto.*;
import com.xsz.programmingarsenal.generatecode.jpa.util.FileUtil;
import com.xsz.programmingarsenal.generatecode.jpa.util.StrUtil;

import java.util.List;

/**
 * @author ChenTian
 * @date 2022/4/6
 */
public class ServiceGen {

    public static void parse(String entityName, TableInfo tableInfo) {
        String svcName = "I" + entityName + "Service";
        String svcImplName = entityName + "ServiceImpl";
        genSvc(tableInfo.getTableComment(), entityName, svcName);
        genSvcImpl(tableInfo.getTableComment(), entityName, svcName, svcImplName,tableInfo.getColumnInfoList());
    }


    /**
     * 生成Service接口
     */
    private static void genSvc(String tableComment, String entityName, String svcName) {
        StringBuffer sb = new StringBuffer();
        sb.append("package " + CodeGenTool.svcPackageOutPath + ";\r\n");
        sb.append("\r\n");

        sb.append("import " + CodeGenTool.basePackageOutPath + ".BaseService;\r\n");
        sb.append("import " + CodeGenTool.entityPackageOutPath + "." + entityName + ";\r\n\r\n");

        //注释部分
        sb.append("/**\r\n");
        sb.append(" * " + tableComment + " 接口\r\n");
        sb.append(" *\r\n");
        sb.append(" * @author " + CodeGenTool.authorName + "\r\n");
        sb.append(" */ \r\n");

        //实体部分
        sb.append("public interface " + svcName + " extends BaseService<" + entityName + "> {\r\n\r\n");
        sb.append("}\r\n");

        // 生成文件
        String path = System.getProperty("user.dir")+"/"+ CodeGenTool.modulePath;
        String outputPath = path + "/src/main/java/" + CodeGenTool.svcPackageOutPath.replace(".", "/");
        String filePath = outputPath + "/" + svcName + ".java";
        FileUtil.genFile(outputPath, filePath, sb.toString());
        System.out.println("生成Service: " + filePath.split("src/main/java/")[1]);
    }

    /**
     * 生成Service实现类
     */
    private static void genSvcImpl(String tableComment, String entityName, String svcName, String svcImplName, List<ColumnInfo> columnInfoList) {
        StringBuffer sb = new StringBuffer();
        sb.append("package " + CodeGenTool.svcImplPackageOutPath + ";\r\n");
        sb.append("\r\n");

        sb.append("import " + CodeGenTool.basePackageOutPath + ".BaseServiceImpl;\r\n");
        sb.append("import " + CodeGenTool.entityPackageOutPath + "." + entityName + ";\r\n");
        sb.append("import " + CodeGenTool.svcPackageOutPath + "." + svcName + ";\r\n");
        sb.append("import " + CodeGenTool.daoPackageOutPath + "." + entityName + "Dao;\r\n");
        sb.append("import javax.annotation.Resource;\r\n");
        sb.append("import org.springframework.stereotype.Service;\r\n\r\n");


        //注释部分
        sb.append("/**\r\n");
        sb.append(" * " + tableComment + " 接口实现\r\n");
        sb.append(" *\r\n");
        sb.append(" * @author " + CodeGenTool.authorName + "\r\n");
        sb.append(" */ \r\n");

        sb.append("@Service\r\n");
        //实体部分
        sb.append("public class " + svcImplName + " extends BaseServiceImpl<" + entityName + "> implements " + svcName + " {\r\n\r\n");
        sb.append("\t@Resource\r\n");
        sb.append("\tprivate " + entityName + "Dao " + StrUtil.lowerFirst(entityName) + "Dao;\r\n\r\n");
        sb.append("}");

        // 生成文件
        String path = System.getProperty("user.dir")+"/"+ CodeGenTool.modulePath;
        String outputPath = path + "/src/main/java/" + CodeGenTool.svcImplPackageOutPath.replace(".", "/") + "/";
        String filePath = outputPath + svcImplName + ".java";
        FileUtil.genFile(outputPath, filePath, sb.toString());
    }
}
