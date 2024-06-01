package com.xsz.programmingarsenal.generatecode.jpa.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 主键实体对象
 * @author ChenTian
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PrimaryKeyDto {
    private String primaryType;
    private String primaryName;
    private boolean isUUID;
}
