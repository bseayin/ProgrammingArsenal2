package com.xsz.programmingarsenal.generatecode.jpa.tool;

import com.xsz.programmingarsenal.generatecode.jpa.CodeGenTool;
import com.xsz.programmingarsenal.generatecode.jpa.dto.*;
import com.xsz.programmingarsenal.generatecode.jpa.meta.MysqlTableColumnMeta;
import com.xsz.programmingarsenal.generatecode.jpa.util.FileUtil;
import com.xsz.programmingarsenal.generatecode.jpa.util.StrUtil;

import java.util.List;

/**
 * 生成 Entity
 * @author ChenTian
 */
public class EntityGen {

    private static boolean isDate = false;
    private static boolean isSql = false;
    private static boolean isUuid = false;

    /**
     * 功能：生成实体类主体代码
     */
    public static void parse(String entityName, TableInfo tableInfo) {

        //todo 暂不支持复合主键

        // 生成成员变量
        String attrStr = parseColumnList(tableInfo.getColumnInfoList());

        // 生成引用包及注解信息
        String importStr = parseImportAndAnnotation(tableInfo);

        // 生成类文本信息
        StringBuffer sbr = new StringBuffer();
        sbr.append(importStr);
        sbr.append("public class " + entityName + " implements Serializable {\r\n\r\n");
        sbr.append(attrStr);
        sbr.append("}\r\n");
        String classContent = sbr.toString();

        // 生成文件
        String path = System.getProperty("user.dir")+"/"+ CodeGenTool.modulePath;
        String outputPath = path + "/src/main/java/" + CodeGenTool.entityPackageOutPath.replace(".", "/");
        String filePath = outputPath + "/" + entityName + ".java";
        FileUtil.genFile(outputPath, filePath, classContent);
        System.out.println("生成Entity: " + filePath.split("src/main/java/")[1]);
    }


    /**
     * 生成成员变量列表信息
     */
    private static String parseColumnList(List<ColumnInfo> columnInfoList){
        StringBuilder attrSbr = new StringBuilder();

        for (ColumnInfo columnInfo : columnInfoList) {

            attrSbr.append("\t/**\r\n");
            attrSbr.append("\t * " + columnInfo.getColumnComment() + "\r\n");
            attrSbr.append("\t */ \r\n");

            // 获取字段数据类型
            String columnType = MysqlTableColumnMeta.sqlType2JavaType(columnInfo.getDataType());

            if (columnInfo.getColumnKey().equals(MysqlTableColumnMeta.PRIMARY_KEY)) {
                attrSbr.append("\t@Id\r\n");
                if (columnInfo.getExtra().equals(MysqlTableColumnMeta.AUTO_INCREMENT)) {
                    attrSbr.append("\t@GeneratedValue(strategy = GenerationType.IDENTITY)\r\n");
                } else {
                    attrSbr.append("\t@GeneratedValue(generator = \"jpa-uuid\")\r\n");
                    isUuid = true;
                }
            }
            if (columnInfo.getDataType().equalsIgnoreCase("datetime")
                    || columnInfo.getDataType().equalsIgnoreCase("timestamp")) {
                isDate = true;
            }
            if (columnInfo.getDataType().equalsIgnoreCase("image")) {
                isSql = true;
            }

            attrSbr.append("\t@Column(name = \"" + columnInfo.getColumnName() + "\")\r\n");
            attrSbr.append("\tprivate " + columnType + " " + StrUtil.initcap2(columnInfo.getColumnName()) + ";\r\n\r\n");
        }
        return attrSbr.toString();
    }

    /**
     * 生成引用包及注解信息
     */
    private static String parseImportAndAnnotation(TableInfo tableInfo) {
        StringBuilder sbr = new StringBuilder();
        sbr.append("package " + CodeGenTool.entityPackageOutPath + ";\r\n");
        sbr.append("\r\n");

        sbr.append("import lombok.*;\r\n\r\n");
        sbr.append("import javax.persistence.*;\r\n");
        sbr.append("import org.hibernate.annotations.DynamicInsert;\r\n");
        sbr.append("import org.hibernate.annotations.DynamicUpdate;\r\n");
        //判断是否导入工具包
        if (isDate) {
            isDate = false;
            sbr.append("import java.util.Date;\r\n");
        }
        if (isSql) {
            isSql = false;
            sbr.append("import java.sql.*;\r\n");
        }
        if(isUuid){
            isUuid = false;
            sbr.append("import org.hibernate.annotations.GenericGenerator;\n");
        }
        sbr.append("import java.io.Serializable;\r\n\r\n");

        //注释部分
        sbr.append("/**\r\n");
        sbr.append(" * " + tableInfo.getTableComment() + "\r\n");
        sbr.append(" *\r\n");
        sbr.append(" * @author " + CodeGenTool.authorName + "\r\n");
        sbr.append(" */ \r\n");

        sbr.append("@NoArgsConstructor\r\n");
        sbr.append("@AllArgsConstructor\r\n");
        sbr.append("@Data\r\n");
        sbr.append("@Builder\r\n");
        sbr.append("@ToString\r\n");
        sbr.append("@DynamicInsert\r\n");
        sbr.append("@DynamicUpdate\r\n");
        sbr.append("@Entity\r\n");
        sbr.append("@Table(name = \"" + tableInfo.getTableName() + "\")\r\n");
        if (isUuid) {
            sbr.append("@GenericGenerator(name = \"jpa-uuid\", strategy = \"uuid\")\r\n");
        }
        return sbr.toString();
    }

}
