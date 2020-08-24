package com.whitemagic2014.db;

import com.whitemagic2014.dao.BotDBDao;
import com.whitemagic2014.pojo.BotDB;
import com.whitemagic2014.pojo.DBVersionTable;
import com.whitemagic2014.pojo.Version;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Description: 数据库更新
 * @author: magic chen
 * @date: 2020/8/20 17:34
 **/
@Component
public class DBVersion {

    private static final Logger logger = LoggerFactory.getLogger(DBVersion.class);

    @Autowired
    BotDBDao dbDao;

    @Autowired
    @Qualifier("dbversionList")
    List<DBVersionTable> dbVersionTable;

    public void checkUpdateDB() {
        logger.info("开始校验数据版本!");
        if (dbVersionTable.isEmpty()) {
            System.out.println("获取最新版本失败!");
        } else {
            BotDB db = dbDao.DBVersion();
            Version ver = new Version(db.getVersion());
            Version latestVer = dbVersionTable.get(dbVersionTable.size() - 1).getVer();
            logger.info("当前版本:" + ver + ",最新版本:" + latestVer);
            if (ver.compareTo(latestVer) < 0) {
                logger.info("开始更新!");
                for (DBVersionTable dbver : dbVersionTable) {
                    if (ver.compareTo(dbver.getVer()) < 0) {
                        for (String sql : dbver.getSqls()) {
                            dbDao.runDDLSql(sql);
                        }
                    } else {
                        continue;
                    }
                }
                db.setVersion(latestVer.toString());
                dbDao.updateDBVersion(db);
                logger.info("更新完毕!");
            }
        }

    }
}
