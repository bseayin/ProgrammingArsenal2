package com.xsz.programmingarsenal.generatecode.jpa.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 表实体对象
 * @author ChenTian
 * @date 2022/4/6
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TableInfo {
    /** 表名 */
    private String tableName;
    /** 表说明 */
    private String tableComment;
    /** 表字段列表 */
    private List<ColumnInfo> columnInfoList;
    /** 表主键 */
    private List<ColumnInfo> primaryKeyList;

}
