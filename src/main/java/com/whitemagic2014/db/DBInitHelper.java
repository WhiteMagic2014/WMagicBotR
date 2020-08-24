package com.whitemagic2014.db;

import com.whitemagic2014.util.Path;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * @Description: 数据库初始化工具
 * @author: magic chen
 * @date: 2020/8/20 15:08
 **/
public class DBInitHelper {

    private static final Logger logger = LoggerFactory.getLogger(DBInitHelper.class);

    // 内部静态类构造单例
    private DBInitHelper() {
    }

    private static class Lazy {
        private static final DBInitHelper instsance = new DBInitHelper();
    }

    public static final DBInitHelper getInstance() {
        return Lazy.instsance;
    }
    // end


    /*
     * @Name: initDBIfNotExist
     * @Description: 如果还没有初始化数据库，则初始化数据库
     * @Param:
     * @Return: boolean
     *
     * @Author: magic chen
     * @Date:   2020/8/20 15:19
     **/
    public boolean initDBIfNotExist() {
        File db = new File(Path.getPath() + "botData.db");
        if (db.exists()) {
            logger.info("已经找到数据文件!");
            return false;
        } else {
            logger.info("未找到数据文件,即将初始化数据文件!");
            createDB();
            logger.info("已创建数据文件:" + Path.getPath() + "botData.db");
            return true;
        }
    }


    /*
     * @Name: createDB
     * @Description: 创建数据库文件
     * @Param:
     * @Return: void
     *
     * @Author: magic chen
     * @Date:   2020/8/20 15:20
     **/
    private void createDB() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e1) {
            logger.error(e1.getMessage());
        }
        String url = "jdbc:sqlite:" + Path.getPath() + "botData.db";

        try (Connection connection = DriverManager.getConnection(url);
             Statement statement = connection.createStatement();) {

            statement.executeUpdate("DROP TABLE IF EXISTS DBInfo;");

            String createDBsql = "create table DBInfo("
                    + "id INTEGER PRIMARY KEY autoincrement,"
                    + "name varchar(255) ,"
                    + "version varchar(20),"
                    + "createDate varchar(20),"
                    + "updateDate varchar(20));";
            statement.executeUpdate(createDBsql);

            String initsql = "INSERT INTO DBInfo (name,version,createDate,updateDate) VALUES ("
                    + "'MagicBot',"
                    + "'0.0.1',"
                    + "'" + sdf.format(new Date()) + "',"
                    + "'')";
            statement.executeUpdate(initsql);

        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }


}
