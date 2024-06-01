package com.xsz.programmingarsenal.generatecode.jpa.meta;


import com.xsz.programmingarsenal.generatecode.jpa.CodeGenTool;
import com.xsz.programmingarsenal.generatecode.jpa.dto.*;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 获取数据库表元数据信息
 * @author ChenTian
 */
@Slf4j
public class MysqlTableColumnMeta {

    public static final String PRIMARY_KEY = "PRI";

    public static final String AUTO_INCREMENT = "auto_increment";

    /** 获取表元数据语句 */
    private static final String TABLE_SQL = "SELECT TABLE_NAME,TABLE_COMMENT FROM information_schema.TABLES " +
            "WHERE TABLE_SCHEMA='" + CodeGenTool.DATABASE + "' AND TABLE_TYPE='BASE TABLE' AND TABLE_NAME LIKE '%s';";

    /** 获取字段元数据语句 */
    private static final String COLUMN_SQL = "SELECT table_name AS tableName, column_name AS columnName, data_type AS dataType, " +
            "column_comment AS columnComment, column_key AS columnKey, extra AS extra FROM information_schema.columns " +
            "WHERE table_schema ='" + CodeGenTool.DATABASE + "' AND table_name = '%s' ORDER BY table_name ASC, ordinal_position ASC;";

    /**
     * 查询数据库获取表的字段属性列表（支持表名'%'模糊匹配）
     */
    public static List<TableInfo> queryTableInfoListByDataBase(String tbName) {
        //查要生成实体类的表
        String tableSql = String.format(TABLE_SQL, tbName);
        log.info("查询表结构：{}", tableSql);
        try {
            Class.forName(CodeGenTool.DRIVER);
        } catch (ClassNotFoundException e) {
            log.error("load "+CodeGenTool.DRIVER+" error:",e);
        }

        // 创建连接
        try(Connection con = DriverManager.getConnection(CodeGenTool.URL, CodeGenTool.USERNAME, CodeGenTool.PASSWORD)){
            PreparedStatement preStatement;
            preStatement = con.prepareStatement(tableSql);
            ResultSet tablesResultSet = preStatement.executeQuery();

            List<TableInfo> tableInfoList = new ArrayList<>();
            while (tablesResultSet.next()) {
                String tableName = tablesResultSet.getString(1);
                String tableComment = tablesResultSet.getString(2);
                String entitySql = String.format(COLUMN_SQL, tableName);
                log.info("查询表字段：{}, {}", tableComment, entitySql);
                preStatement = con.prepareStatement(entitySql);
                ResultSet columnResultSet = preStatement.executeQuery();

                List<ColumnInfo> columnInfoList = new ArrayList<>();
                List<ColumnInfo> primaryList = new ArrayList<>();
                while (columnResultSet.next()) {
                    ColumnInfo columnInfo = new ColumnInfo(columnResultSet.getString(2), columnResultSet.getString(3), columnResultSet.getString(4), columnResultSet.getString(5), columnResultSet.getString(6));
                    if(PRIMARY_KEY.equals(columnInfo.getColumnKey())){
                        primaryList.add(columnInfo);
                    }
                    columnInfoList.add(columnInfo);
                }

                TableInfo tableInfo = new TableInfo(tableName, tableComment, columnInfoList,primaryList);
                tableInfoList.add(tableInfo);
            }
            return tableInfoList;
        } catch (SQLException e) {
            log.error("get database table meta error:",e);
        }
        return null;
    }

    /**
     * 功能：根据数据库类型获得列的Java数据类型
     */
    public static String sqlType2JavaType(String sqlType) {
        if (sqlType.equalsIgnoreCase("bit")) {
            return "Boolean";
        } else if (sqlType.equalsIgnoreCase("tinyint")) {
            return "Byte";
        } else if (sqlType.equalsIgnoreCase("smallint")) {
            return "Short";
        } else if (sqlType.equalsIgnoreCase("int") || sqlType.equalsIgnoreCase("INT UNSIGNED")) {
            //INT UNSIGNED无符号整形
            return "Integer";
        } else if (sqlType.equalsIgnoreCase("bigint")) {
            return "Long";
        } else if (sqlType.equalsIgnoreCase("float")) {
            return "Float";
        } else if (sqlType.equalsIgnoreCase("decimal") || sqlType.equalsIgnoreCase("numeric")
                || sqlType.equalsIgnoreCase("real") || sqlType.equalsIgnoreCase("double") || sqlType.equalsIgnoreCase("money")
                || sqlType.equalsIgnoreCase("smallmoney")) {
            return "Double";
        } else if (sqlType.equalsIgnoreCase("varchar") || sqlType.equalsIgnoreCase("char")
                || sqlType.equalsIgnoreCase("nvarchar") || sqlType.equalsIgnoreCase("nchar")
                || sqlType.equalsIgnoreCase("text")) {
            return "String";
        } else if (sqlType.equalsIgnoreCase("datetime") || sqlType.equalsIgnoreCase("timestamp")) {
            return "Date";
        } else if (sqlType.equalsIgnoreCase("image")) {
            return "Blob";
        } else if(sqlType.equalsIgnoreCase("blob")){
            return "Byte[]";
        }
        else {
            System.out.println("can't support sql type:"+sqlType);
        }
        return null;
    }

}
