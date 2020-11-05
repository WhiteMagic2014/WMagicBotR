package com.whitemagic2014.config;

import com.whitemagic2014.command.Command;
import com.whitemagic2014.events.CommandEvents;
import com.whitemagic2014.events.GroupEvents;
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
 * 除了指令事件(现在由于指令事件需要 等待自定义注解扫描，自动注册指令，所以在启动bot前手动实例化)
 * @author: magic chen
 * @date: 2020/8/20 23:36
 **/
@Configuration
public class EventsConfig {


//    @Autowired
//    RecallEvent recallEvent;

    @Autowired
    MessageEvents messageEvents;

    @Autowired
    GroupEvents groupEvents;

    @Bean(name = "botEvents")
    public List<ListenerHost> getBotevents() {
        List<ListenerHost> events = new ArrayList<>();
//        events.add(recallEvent);
        events.add(messageEvents);
        events.add(groupEvents);
        return events;
    }


}
