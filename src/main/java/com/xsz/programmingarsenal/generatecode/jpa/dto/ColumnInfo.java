package com.xsz.programmingarsenal.generatecode.jpa.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 字段实体对象
 * @author ChenTian
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ColumnInfo implements Serializable {

    /** 字段名称 */
    private String columnName;
    /** 字段类型 */
    private String dataType;
    /** 字段注释 */
    private String columnComment;
    /** 字段键信息 */
    private String columnKey;
    /** 字段自增信息 */
    private String extra;

    /**
     * 是否主键
     */
    public boolean isPrimaryKey(){
        return "PRI".equals(columnKey);
    }

}
