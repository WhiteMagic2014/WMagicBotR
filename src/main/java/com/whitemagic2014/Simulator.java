package com.whitemagic2014;

import com.whitemagic2014.annotate.AnnotateAnalyzer;
import com.whitemagic2014.bot.MagicBotR;
import com.whitemagic2014.config.properties.SwitchProperties;
import com.whitemagic2014.db.DBInitHelper;
import com.whitemagic2014.db.DBVersion;
import com.whitemagic2014.events.CommandEvents;
import com.whitemagic2014.service.RemindService;
import net.mamoe.mirai.event.ListenerHost;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import top.mrxiaom.qsign.QSignService;

import java.io.File;
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

    @Value("${bot.account}")
    Long account;

    @Value("${bot.pwd}")
    String pwd;

    @Value("${log.net.path}")
    String lognet;

    @Value("${project.version}")
    public String version;

    @Autowired
    RemindService remindService;

    @Autowired
    AnnotateAnalyzer annotateAnalyzer;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        logger.info("bot版本: " + version);
        // 数据库check初始化
        DBInitHelper.getInstance().initDBIfNotExist();
        // 数据库版本检查更新
        dbVersion.checkUpdateDB();
        // 开始读取 component switch 配置
        SwitchProperties.updateSwitchByProperty();
        // 指令事件 实例化后加入事件列表
        CommandEvents commandEvents = new CommandEvents();
        commandEvents.registerCommandHeads("#", "$", "!", "！", "");
        commandEvents.registerCommands(annotateAnalyzer.getCommands());
        events.add(commandEvents);
        // 读取备忘数据
        remindService.loadTask();


        setup();

        // 启动bot
        try {
            MagicBotR.startBot(account, pwd, "device.json", events, lognet);
            logger.info("启动成功！");
        } catch (Exception e) {
            logger.error("启动失败", e);
        }

    }

    public static void setup() {
        // 初始化签名服务，参数为签名服务工作目录，里面应当包含
        // config.json, dtconfig.json, libfekit.so, libQSec.so
        QSignService.Factory.init(new File("txlib/8.9.85"));
        // 加载签名服务所需协议信息，如果你的协议信息存在非 以上的工作目录 中的文件夹，请将参数 null 改为协议信息所在目录
        // 该方法将会扫描目录下以协议信息命名的文件进行加载，如 android_phone.json
        // 如果你想单独加载协议信息文件，详见 loadProtocolExample() 中的例子
        QSignService.Factory.loadProtocols(null);

        // 注册签名服务
        QSignService.Factory.register();
    }


}
