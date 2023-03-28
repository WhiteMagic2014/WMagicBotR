package com.whitemagic2014;

import com.whitemagic2014.annotate.AnnotateAnalyzer;
import com.whitemagic2014.bot.MagicBotR;
import com.whitemagic2014.config.properties.SwitchProperties;
import com.whitemagic2014.db.DBInitHelper;
import com.whitemagic2014.db.DBVersion;
import com.whitemagic2014.events.CommandEvents;
import com.whitemagic2014.service.Pcrjjc;
import net.mamoe.mirai.event.ListenerHost;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Description: 启动类
 * @author: magic chen
 * @date: 2020/8/20 21:08
 **/
@Component
public class Simulator implements ApplicationRunner {

    private static final Logger logger = LoggerFactory.getLogger(Simulator.class);

    @Autowired
    @Qualifier("botEvents")
    List<ListenerHost> events;

    @Autowired
    DBVersion dbVersion;

    @Autowired
    Pcrjjc pcrjjc;

    @Value("${bot.account}")
    Long account;

    @Value("${bot.pwd}")
    String pwd;

    @Value("${log.net.path}")
    String lognet;


    @Autowired
    AnnotateAnalyzer annotateAnalyzer;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // 数据库check初始化
        DBInitHelper.getInstance().initDBIfNotExist();
        // 数据库版本检查更新
        dbVersion.checkUpdateDB();
        // 开始读取 component switch 配置
        SwitchProperties.updateSwitchByProperty();
        // jjc查询 check nickname文件 是否存在
        pcrjjc.initNameFile();

        // 指令事件 实例化后加入事件列表
        CommandEvents commandEvents = new CommandEvents();
        commandEvents.registerCommandHeads("#", "$", "!", "！", "");
        commandEvents.registerCommands(annotateAnalyzer.getCommands());
        events.add(commandEvents);

        // 启动bot
        try {
            MagicBotR.startBot(account, pwd, "device.json", events, lognet);
        }catch (Exception e){
            e.printStackTrace();
        }
        logger.info("启动成功！");
    }
}
