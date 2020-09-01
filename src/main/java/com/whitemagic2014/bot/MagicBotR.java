package com.whitemagic2014.bot;

import com.whitemagic2014.util.MagicLogger;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.BotFactoryJvm;
import net.mamoe.mirai.event.Events;
import net.mamoe.mirai.event.ListenerHost;
import net.mamoe.mirai.utils.BotConfiguration;

import java.io.File;
import java.util.List;

/**
 * @Description: 创建bot
 * @author: magic chen
 * @date: 2020/8/20 15:46
 **/
public class MagicBotR {

    private Bot bot;

    public MagicBotR(Long account, String pwd, String deviceInfo, List<ListenerHost> events, String netlog) {
        bot = startBot(account, pwd, deviceInfo, events, netlog);
    }

    public Bot getBot() {
        return bot;
    }

    public void setBot(Bot bot) {
        this.bot = bot;
    }

    /**
     * @Name: startBot
     * @Description: 创建bot
     * @Param: account bot账号
     * @Param: pwd  bot密码
     * @Param: deviceInfo 存储设备信息文件
     * @Param: events 注册监听事件
     * @Param: netlog net日志重定向到文件夹路径
     * @Return: net.mamoe.mirai.Bot
     * @Author: magic chen
     * @Date: 2020/8/20 15:54
     **/
    private Bot startBot(Long account, String pwd, String deviceInfo, List<ListenerHost> events, String netlog) {
        BotConfiguration config = new BotConfiguration();
        config.fileBasedDeviceInfo(deviceInfo);
        // 使用自定义的log
        config.setBotLoggerSupplier(bot -> new MagicLogger());
        // 将net层输出写入文件
        config.redirectNetworkLogToDirectory(new File(netlog));
        Bot bot = BotFactoryJvm.newBot(account, pwd, config);
        bot.login();
        // 注册事件
        for (ListenerHost event : events) {
            Events.registerEvents(bot, event);
        }
        // 这个和picbotx 还是不太一样 那个不会占用主线程
        // 这里必须要启新线程去跑bot 不然会占用主线程
        new Thread(() -> {
            bot.join();
        }).start();
        return bot;
    }

}
