package com.whitemagic2014.config;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.sqlite.javax.SQLiteConnectionPoolDataSource;

import com.whitemagic2014.pojo.DBVersionTable;
import com.whitemagic2014.pojo.Version;
import com.whitemagic2014.util.Path;

/**
 * @Description: sqlite + mybatis 配置 ，版本更新 sqlite DDL
 * @author: magic chen
 * @date: 2020/8/20 15:46
 **/
@Configuration
@MapperScan(basePackages = "com.whitemagic2014.dao")
public class SqlLiteConfig {

    @Autowired
    DataSource dataSource;


    @Bean(name="sqliteDataSource")
    public DataSource sqliteDataSource() {
        SQLiteConnectionPoolDataSource pool = new SQLiteConnectionPoolDataSource();
        pool.setUrl("jdbc:sqlite:" + Path.getPath() + "botData.db");
        return pool;
    }


    @Bean
    public SqlSessionFactory sqliteSqlSessionFactory(@Qualifier("sqliteDataSource") DataSource dataSource) throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);
        sessionFactory
                .setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/*.xml"));
        return sessionFactory.getObject();
    }



    @Bean(name="dbversionList")
    public List<DBVersionTable> getDbVersionTables(){
        List<DBVersionTable> result = new ArrayList<>();


        DBVersionTable v0_0_2 = new DBVersionTable();
        v0_0_2.setVer(new Version("0.0.2"));
        List<String> sql_0_0_2 = new ArrayList<>();

        sql_0_0_2.add("DROP TABLE IF EXISTS user_plan");
        sql_0_0_2.add("CREATE TABLE \"user_plan\" (\n" +
                "  \"id\" INTEGER PRIMARY KEY autoincrement,\n" +
                "  \"uid\" text(20),\n" +
                "  \"itemId\" integer,\n" +
                "  \"itemName\" text(64),\n" +
                "  \"nowNum\" integer,\n" +
                "  \"planNum\" integer\n" +
                ");");
        v0_0_2.setSqls(sql_0_0_2);
        result.add(v0_0_2);

        DBVersionTable v0_0_3 = new DBVersionTable();
        v0_0_3.setVer(new Version("0.0.3"));
        List<String> sql_0_0_3 = new ArrayList<>();
        sql_0_0_3.add("DROP TABLE IF EXISTS can_eat");
        sql_0_0_3.add("CREATE TABLE \"can_eat\" (\n" +
                "  \"id\" INTEGER PRIMARY KEY autoincrement,\n" +
                "  \"itemName\" text(64),\n" +
                "  \"can\" integer\n" +
                ");");
        v0_0_3.setSqls(sql_0_0_3);
        result.add(v0_0_3);


        DBVersionTable v0_0_4 = new DBVersionTable();
        v0_0_4.setVer(new Version("0.0.4"));
        List<String> sql_0_0_4 = new ArrayList<>();
        sql_0_0_4.add("DROP TABLE IF EXISTS pcr_guild");
        sql_0_0_4.add("CREATE TABLE \"pcr_guild\" (\n" +
                " \"gid\" INTEGER PRIMARY KEY,\n" +
                " \"gname\" TEXT(64)\n" +
                ");");
        sql_0_0_4.add("DROP TABLE IF EXISTS pcr_user");
        sql_0_0_4.add("CREATE TABLE \"pcr_user\" (\n" +
                " \"uid\" INTEGER,\n" +
                " \"uname\" TEXT(64),\n" +
                " \"role\" TEXT(8),\n" +
                " \"gid\" INTEGER,\n" +
                " \"sl\" INTEGER\n"+
                ");");
        sql_0_0_4.add("DROP TABLE IF EXISTS pcr_boss");
        sql_0_0_4.add("CREATE TABLE \"pcr_boss\" (\n" +
                " \"id\" INTEGER PRIMARY KEY autoincrement,\n" +
                " \"gid\" INTEGER,\n" +
                " \"cycle\" INTEGER,\n" +
                " \"num\" INTEGER,\n" +
                " \"hp\" INTEGER,\n" +
                " \"hpnow\" INTEGER,\n" +
                " \"active\" INTEGER\n" +
                ");");
        sql_0_0_4.add("DROP TABLE IF EXISTS pcr_battle");
        sql_0_0_4.add("CREATE TABLE \"pcr_battle\" (\n" +
                " \"id\" INTEGER PRIMARY KEY autoincrement,\n" +
                " \"bossid\" INTEGER,\n" +
                " \"uid\" INTEGER,\n" +
                " \"gid\" INTEGER,\n" +
                " \"uname\" TEXT(64),\n" +
                " \"damage\" INTEGER,\n" +
                " \"killboss\" INTEGER,\n" +
                " \"type\" TEXT(8),\n" +
                " \"time\" TEXT(32)\n" +
                ");");

        sql_0_0_4.add("DROP TABLE IF EXISTS pcr_notice");
        sql_0_0_4.add("CREATE TABLE \"pcr_notice\" (\n" +
                " \"gid\" INTEGER,\n" +
                " \"uid\" INTEGER,\n" +
                " \"bossNum\" INTEGER,\n" +
                " \"type\" TEXT(16)\n" +
                ");");

        v0_0_4.setSqls(sql_0_0_4);
        result.add(v0_0_4);


        DBVersionTable v1_0_0 = new DBVersionTable();
        v1_0_0.setVer(new Version("1.0.0"));
        v1_0_0.setSqls(new ArrayList<>());
        result.add(v1_0_0);



        // 货币系统
        DBVersionTable v1_1_0 = new DBVersionTable();
        v1_1_0.setVer(new Version("1.1.0"));
        List<String> sql_1_1_0 = new ArrayList<>();
        // 账户表
        sql_1_1_0.add("DROP TABLE IF EXISTS user_coin");
        sql_1_1_0.add("CREATE TABLE \"user_coin\" (\n" +
                " \"uid\" INTEGER PRIMARY KEY,\n" +
                " \"magicCoin\" INTEGER,\n" +
                " \"time\" TEXT(32),\n" +
                " \"available\" INTEGER\n" +
                ");");
        // 货币变更记录表
        sql_1_1_0.add("DROP TABLE IF EXISTS coin_log");
        sql_1_1_0.add("CREATE TABLE \"coin_log\" (\n" +
                " \"id\" INTEGER PRIMARY KEY autoincrement,\n" +
                " \"uid\" INTEGER,\n" +
                " \"gid\" INTEGER,\n" +
                " \"type\" TEXT(16),\n" +
                " \"amount\" INTEGER,\n" +
                " \"before\" INTEGER,\n" +
                " \"after\" INTEGER,\n" +
                " \"remark\" TEXT(511),\n" +
                " \"time\" TEXT(32),\n" +
                " \"field1\" TEXT(255),\n" +
                " \"field2\" TEXT(255),\n" +
                " \"field3\" TEXT(255)\n" +
                ");");
        v1_1_0.setSqls(sql_1_1_0);
        result.add(v1_1_0);


        // 夸夸模块
        DBVersionTable v1_2_0 = new DBVersionTable();
        v1_2_0.setVer(new Version("1.2.0"));
        List<String> sql_1_2_0 = new ArrayList<>();
        sql_1_2_0.add("DROP TABLE IF EXISTS chp_data");
        sql_1_2_0.add("CREATE TABLE \"chp_data\" (\n" +
                " \"hash\" TEXT(32) PRIMARY KEY,\n" +
                " \"content\" TEXT(1024)\n" +
                ");");

        v1_2_0.setSqls(sql_1_2_0);
        result.add(v1_2_0);

        // 毒鸡汤 朋友圈 文案
        DBVersionTable v1_2_1 = new DBVersionTable();
        v1_2_1.setVer(new Version("1.2.1"));
        List<String> sql_1_2_1 = new ArrayList<>();
        sql_1_2_1.add("DROP TABLE IF EXISTS djt_data");
        sql_1_2_1.add("CREATE TABLE \"djt_data\" (\n" +
                " \"hash\" TEXT(32) PRIMARY KEY,\n" +
                " \"content\" TEXT(1024)\n" +
                ");");
        sql_1_2_1.add("DROP TABLE IF EXISTS pyq_data");
        sql_1_2_1.add("CREATE TABLE \"pyq_data\" (\n" +
                " \"hash\" TEXT(32) PRIMARY KEY,\n" +
                " \"content\" TEXT(1024)\n" +
                ");");
        v1_2_1.setSqls(sql_1_2_1);
        result.add(v1_2_1);


        // 火纹续战
        DBVersionTable v1_2_2 = new DBVersionTable();
        v1_2_2.setVer(new Version("1.2.2"));
        List<String> sql_1_2_2 = new ArrayList<>();
        sql_1_2_2.add("DROP TABLE IF EXISTS engage_battle");
        sql_1_2_2.add("CREATE TABLE \"engage_battle\" (\n" +
                " \"id\" INTEGER PRIMARY KEY autoincrement,\n" +
                " \"battleKey\" TEXT(16),\n" +
                " \"status\" INTEGER,\n" +
                " \"remark\" TEXT(511)\n" +
                ");");
        v1_2_2.setSqls(sql_1_2_2);
        result.add(v1_2_2);

        // 续战优化
        DBVersionTable v1_2_3 = new DBVersionTable();
        v1_2_3.setVer(new Version("1.2.3"));
        List<String> sql_1_2_3 = new ArrayList<>();
        sql_1_2_3.add("ALTER TABLE engage_battle ADD COLUMN \"qqNum\" TEXT(32);");
        sql_1_2_3.add("ALTER TABLE engage_battle ADD COLUMN \"linkNum1\" TEXT(32);");
        sql_1_2_3.add("ALTER TABLE engage_battle ADD COLUMN \"linkNum2\" TEXT(32);");
        sql_1_2_3.add("ALTER TABLE engage_battle ADD COLUMN \"linkNum3\" TEXT(32);");
        sql_1_2_3.add("ALTER TABLE engage_battle ADD COLUMN \"linkNum4\" TEXT(32);");
        sql_1_2_3.add("ALTER TABLE engage_battle ADD COLUMN \"finNum\" TEXT(32);");

        v1_2_3.setSqls(sql_1_2_3);
        result.add(v1_2_3);



        return result;
    }

}
