package com.whitemagic2014.events;

import com.whitemagic2014.util.MagicMaps;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.event.EventHandler;
import net.mamoe.mirai.event.Listener;
import net.mamoe.mirai.event.ListeningStatus;
import net.mamoe.mirai.event.SimpleListenerHost;
import net.mamoe.mirai.message.GroupMessageEvent;
import net.mamoe.mirai.message.data.At;
import net.mamoe.mirai.message.data.Message;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @Description: 非指令消息的消息事件
 * @author: magic chen
 * @date: 2020/9/8 11:40
 **/
@Component
public class MessageEvents extends SimpleListenerHost {

    private static Logger logger = LoggerFactory.getLogger(MessageEvents.class);

    /**
     * @Name: catchXikali
     * @Description: 光佬捕捉球 捕捉光佬说出key的情况
     * @Param: event
     * @Return: net.mamoe.mirai.event.ListeningStatus
     * @Author: magic chen
     * @Date: 2020/9/8 11:44
     **/
    @NotNull
    @EventHandler(priority = Listener.EventPriority.NORMAL)
    public ListeningStatus catchXikali(@NotNull GroupMessageEvent event) throws Exception {
        Group g = event.getSubject();
        // 捕获cd 当捕获一次后,进入cd
        if (!MagicMaps.check("catchXikali")) {
            if (g.getId() == 720828494L) {
                if (event.getSender().getId() == 196435005L) {
                    String oriMsg = event.getMessage().contentToString();
                    if (oriMsg.toLowerCase().contains("key")) {
                        logger.info("捕获光佬");
                        Message msg = new At(g.get(418379149L)).plus("\n捕获光佬发言,包含关键字[key],请及时确认避免错失获得机会");
                        g.sendMessage(msg);
                        // cd 5分钟
                        MagicMaps.putWithExpire("catchXikali", "", 5L, TimeUnit.MINUTES);
                    }
                }
            }
        }
        return ListeningStatus.LISTENING;
    }

}
