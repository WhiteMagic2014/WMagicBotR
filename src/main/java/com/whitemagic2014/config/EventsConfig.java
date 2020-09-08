package com.whitemagic2014.config;

import com.whitemagic2014.command.Command;
import com.whitemagic2014.events.CommandEvents;
import com.whitemagic2014.events.MessageEvents;
import net.mamoe.mirai.event.ListenerHost;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 需要注册的事件
 * @author: magic chen
 * @date: 2020/8/20 23:36
 **/
@Configuration
public class EventsConfig {


//    @Autowired
//    RecallEvent recallEvent;

    @Autowired
    CommandEvents commandEvents;

    @Autowired
    MessageEvents messageEvents;

    /**
     * @Name: initMessageEvents
     * @Description: 消息事件处理, 不同于其他事件, 消息事件中进一步封装了指令
     * @Param: heads
     * @Param: commands
     * @Return: 消息事件实例
     * @Author: magic chen
     * @Date: 2020/8/21 00:17
     **/
    @Bean
    public CommandEvents initMessageEvents(@Qualifier("initCommandHeads") String[] heads, @Qualifier("initCommands") Command[] commands) {
        CommandEvents commandEvents = new CommandEvents();
        commandEvents.registerCommandHeads(heads);
        commandEvents.registerCommands(commands);
        return commandEvents;
    }


    @Bean(name = "botEvents")
    public List<ListenerHost> getBotevents() {
        List<ListenerHost> events = new ArrayList<ListenerHost>();
//        events.add(recallEvent);
        events.add(commandEvents);
        events.add(messageEvents);
        return events;
    }


}
