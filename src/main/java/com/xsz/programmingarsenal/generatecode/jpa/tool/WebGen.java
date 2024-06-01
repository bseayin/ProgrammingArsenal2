package com.xsz.programmingarsenal.generatecode.jpa.tool;

import com.xsz.programmingarsenal.generatecode.jpa.CodeGenTool;
import com.xsz.programmingarsenal.generatecode.jpa.dto.*;
import com.xsz.programmingarsenal.generatecode.jpa.meta.MysqlTableColumnMeta;
import com.xsz.programmingarsenal.generatecode.jpa.util.FileUtil;
import com.xsz.programmingarsenal.generatecode.jpa.util.StrUtil;

import java.util.List;

/**
 * @author ChenTian
 * @date 2022/4/6
 */
public class WebGen {
    /**
     * 功能：生成 Controller 类主体代码
     */
    public static void parse(String entityName, TableInfo tableInfo) {
        String webName = entityName + "Controller";
        StringBuilder sb = new StringBuilder();
        sb.append("package "+ CodeGenTool.webPackageOutPath + ";\r\n");
        sb.append("\r\n");

        sb.append("import " + CodeGenTool.basePackageOutPath + ".BaseController;\r\n");
        sb.append("import " + CodeGenTool.entityPackageOutPath + "." + entityName + ";\r\n");
        sb.append("import " + CodeGenTool.svcPackageOutPath + ".I" + entityName + "Service;\r\n");
        sb.append("import javax.annotation.Resource;\r\n");
        sb.append("import org.springframework.web.bind.annotation.*;\r\n\r\n");

        //注释部分
        sb.append("/**\r\n");
        sb.append(" * " + tableInfo.getTableComment() + " Controller\r\n");
        sb.append(" *\r\n");
        sb.append(" * @author " + CodeGenTool.authorName + "\r\n");
        sb.append(" */ \r\n");

        sb.append("@RestController\r\n");
        sb.append("@RequestMapping(\"/" + StrUtil.lowerFirst(entityName) + "\")\r\n");

        //实体部分
        sb.append("public class " + webName + " extends BaseController {\r\n\r\n");
        sb.append("\t@Resource\r\n");
        sb.append("\tprivate I" + entityName + "Service " + StrUtil.lowerFirst(entityName) + "Service;\r\n\r\n");

        List<ColumnInfo> primaryKeyList = tableInfo.getPrimaryKeyList();
        if(primaryKeyList !=null && !primaryKeyList.isEmpty()) {
            ColumnInfo primaryKeyDto = primaryKeyList.get(0);
            String columnName = StrUtil.initcap2(primaryKeyDto.getColumnName());
            String columnType = MysqlTableColumnMeta.sqlType2JavaType(primaryKeyDto.getDataType());

            sb.append("\t@GetMapping(\"/getById/{" + columnName + "}\")\r\n");
            sb.append("\tpublic "+ entityName+" getById(@PathVariable " + columnType + " " + columnName + ") {\r\n");
            sb.append("\t\t" + entityName + " result = " + StrUtil.lowerFirst(entityName) + "Service.findById(" + columnName + ");\r\n");
            sb.append("\t\treturn result;\r\n");
            sb.append("\t}\r\n\r\n");

            sb.append("\t@DeleteMapping(\"/deleteById/{"+columnName+"}\")\r\n");
            sb.append("\tpublic void deleteById(@PathVariable "+ columnType +" "+ columnName + ") {\r\n");
            sb.append("\t\t" + StrUtil.lowerFirst(entityName) + "Service.deleteById("+ columnName +");\r\n");
            sb.append("\t}\r\n\r\n");
        }

        sb.append("\t@PostMapping(\"/save\")\r\n");
        sb.append("\tpublic "+ entityName +" save(@RequestBody " + entityName + " model) {\r\n");
        sb.append("\t\t" + entityName + " result = " + StrUtil.lowerFirst(entityName) + "Service.save(model);\r\n");
        sb.append("\t\treturn result;\r\n");
        sb.append("\t}\r\n\r\n");



        sb.append("}");

        // 生成文件
        String path = System.getProperty("user.dir")+"/"+ CodeGenTool.modulePath;
        String outputPath = path +"/src/main/java/" + CodeGenTool.webPackageOutPath.replace(".", "/");
        String filePath = outputPath + "/" + webName + ".java";
        FileUtil.genFile(outputPath, filePath, sb.toString());
        System.out.println("生成Controller: " + filePath.split("src/main/java/")[1]);
    }
}
