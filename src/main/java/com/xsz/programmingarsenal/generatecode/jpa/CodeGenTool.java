package com.xsz.programmingarsenal.generatecode.jpa;

import com.xsz.programmingarsenal.generatecode.jpa.dto.TableInfo;
import com.xsz.programmingarsenal.generatecode.jpa.meta.MysqlTableColumnMeta;
import com.xsz.programmingarsenal.generatecode.jpa.tool.*;
import com.xsz.programmingarsenal.generatecode.jpa.util.StrUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * 自动生成代码工具类
 * @author Brad
 */
@Slf4j
public class CodeGenTool {

    /** 指定 生成文件输出模块名 */
    public static String modulePath = "ProgrammingArsenal";
    /** 指定 模块 package */
    public static String modulePackage = "com.xsz.programmingarsenal.pando";
    /** 指定 Entity 实体生成所在包的路径 */
    public static String entityPackageOutPath =  modulePackage + ".entity";
    /** 指定 Dao 所在包的路径 */
    public static String daoPackageOutPath = modulePackage + ".dao";
    /** 指定 Service 接口所在包的路径 */
    public static String svcPackageOutPath = modulePackage + ".service";
    /** 指定 ServiceImpl 实现所在包的路径 */
    public static String svcImplPackageOutPath = modulePackage + ".service.impl";
    /** 指定 Controller 实现所在包的路径 */
    public static String webPackageOutPath = modulePackage + ".controller";
    /** 指定 基础依赖类所在包的路径 */
    public static String basePackageOutPath = "com.xsz.programmingarsenal.generatecode.jpa.base";
    /** 指定 作者名字 */
    public static String authorName = "Brad";

    /** 指定 数据库连接配置 */
    public static final String DATABASE = "db_pando";
    public static final String URL = "jdbc:mysql://127.0.0.1:3306/"+DATABASE+"?characterEncoding=UTF-8&autoReconnect=true&useUnicode=true&serverTimezone=GMT%2B8&zeroDateTimeBehavior=convertToNull&useSSL=false";
    public static final String USERNAME = "root";
    public static final String PASSWORD = "XSZ202006a";
    public static final String DRIVER = "com.mysql.cj.jdbc.Driver";

    public static void main(String[] args) {
//        gen("t_sys_ta%", true, true, true, true, "t_");
        gen("t_sys_task", true, true, true, true, "t_");
    }


    /**
     * 代码生成入口
     * @param tbName 表名，支持sql模糊匹配，通配符：%
     * @param isGenEntity 是否生成实体
     * @param isGenDao 是否生成Dao，为true时，isGenEntity也必须为true
     * @param isGenService 是否生成Service，为true时，isGenDao、sGenEntity也必须为true
     * @param isGenWeb 是否生成Controller，为true时，isGenService、isGenEntity也必须为true
     * @param ignoreTablePrefix 生成类名忽略的表前缀
     */
    public static void gen(String tbName, boolean isGenEntity, boolean isGenDao, boolean isGenService,
                           boolean isGenWeb, String ignoreTablePrefix) {

        // 查询数据库获取表的字段属性列表
        List<TableInfo> tableInfoList = MysqlTableColumnMeta.queryTableInfoListByDataBase(tbName);
        if(tableInfoList == null){
            log.error("获取表的字段属性列表失败！");
            return;
        }

        // 复制 base 类
        BaseClassGen.genBaseClass();

        for (TableInfo tableInfo : tableInfoList) {
            String entityName = StrUtil.initcap(StrUtil.subStrByPrefix(tableInfo.getTableName(), ignoreTablePrefix));

            if (isGenEntity) {
                EntityGen.parse(entityName,tableInfo);
            }
            if (isGenDao) {
                DaoGen.parse(entityName, tableInfo.getTableComment());
            }
            if (isGenService) {
                ServiceGen.parse(entityName,tableInfo);
            }
            if (isGenWeb) {
                WebGen.parse(entityName, tableInfo);
            }
        }

        log.info("生成完毕！");
    }
}
