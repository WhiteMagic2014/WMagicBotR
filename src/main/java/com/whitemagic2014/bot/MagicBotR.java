package com.whitemagic2014.bot;

import com.whitemagic2014.util.Path;
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

    public MagicBotR(Long account, String pwd, String deviceInfo, List<ListenerHost> events) {
        bot = startBot(account, pwd, deviceInfo, events);
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
     * @Param: account
     * @Param: pwd
     * @Param: deviceInfo
     * @Param: events
     * @Return: net.mamoe.mirai.Bot
     * @Author: magic chen
     * @Date: 2020/8/20 15:54
     **/
    private Bot startBot(Long account, String pwd, String deviceInfo, List<ListenerHost> events) {
        BotConfiguration config = new BotConfiguration();
        config.fileBasedDeviceInfo(deviceInfo);
        config.redirectBotLogToDirectory(new File(Path.getPath() + "logs", "bot"));
        config.redirectNetworkLogToDirectory(new File(Path.getPath() + "logs" ,"net") );

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
