package com.whitemagic2014.dao;

import com.whitemagic2014.pojo.BotDB;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Description: db相关 比如创建表 之类的
 * @author: magic chen
 * @date: 2020/8/20 17:32
 **/
@Repository
public interface BotDBDao {

    /**
     * @Name: DBVersion
     * @Description: 获得DB版本信息
     * @Param:
     * @Return: com.whitemagic2014.pojo.BotDB
     * @Author: magic chen
     * @Date: 2020/8/20 17:33
     **/
    BotDB DBVersion();


    /**
     * @Name: updateDBVersion
     * @Description: 更新db版本
     * @Param: db
     * @Return: int
     * @Author: magic chen
     * @Date: 2020/8/20 17:33
     **/
    int updateDBVersion(BotDB db);


    /**
     * @Name: checkTableExist
     * @Description: 检查表是否存在
     * @Param: tableName
     * @Return: java.lang.Boolean
     * @Author: magic chen
     * @Date: 2020/8/20 17:33
     **/
    Boolean checkTableExist(@Param("tableName") String tableName);


    /**
     * @Name: tables
     * @Description: 获得所有表名
     * @Param:
     * @Return: java.util.List<java.lang.String>
     * @Author: magic chen
     * @Date: 2020/8/20 17:33
     **/
    List<String> tables();

    /**
     * @Name: runDDLSql
     * @Description: 直接执行ddl sql
     * @Param: sql
     * @Return: int
     * @Author: magic chen
     * @Date: 2020/8/20 17:33
     **/
    int runDDLSql(@Param("sql") String sql);

}
