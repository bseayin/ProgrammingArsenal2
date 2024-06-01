package com.xsz.programmingarsenal.generatecode.jpa.tool;
import com.xsz.programmingarsenal.generatecode.jpa.CodeGenTool;
import com.xsz.programmingarsenal.generatecode.jpa.util.FileUtil;

/**
 * @author ChenTian
 * @date 2022/4/6
 */
public class DaoGen {

    /**
     * 功能：生成Dao类主体代码
     */
    public static void parse(String entityName, String tableComment) {
        String daoName = entityName + "Dao";
        StringBuffer sb = new StringBuffer();
        sb.append("package " + CodeGenTool.daoPackageOutPath + ";\r\n");
        sb.append("\r\n");

        sb.append("import " + CodeGenTool.basePackageOutPath + ".BaseDao;\r\n");
        sb.append("import " + CodeGenTool.entityPackageOutPath + "." + entityName + ";\r\n");
        sb.append("import org.springframework.stereotype.Repository;\r\n\r\n");

        //注释部分
        sb.append("/**\r\n");
        sb.append(" * " + tableComment + " Dao\r\n");
        sb.append(" *\r\n");
        sb.append(" * @author " + CodeGenTool.authorName + "\r\n");
        sb.append(" */ \r\n");

        sb.append("@Repository\r\n");

        //实体部分
        sb.append("public interface " + daoName + " extends BaseDao<" + entityName + "> {\r\n\r\n");
        sb.append("}\r\n");

        // 生成文件
        String path = System.getProperty("user.dir")+"/"+ CodeGenTool.modulePath;
        String outputPath = path + "/src/main/java/" + CodeGenTool.daoPackageOutPath.replace(".", "/");
        String filePath = outputPath + "/" + daoName + ".java";
        FileUtil.genFile(outputPath,filePath,sb.toString());
        System.out.println("生成Dao: " + filePath.split("src/main/java/")[1]);

    }
}
