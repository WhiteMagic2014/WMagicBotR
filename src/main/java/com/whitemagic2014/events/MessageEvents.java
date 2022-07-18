package com.whitemagic2014.events;

import com.whitemagic2014.service.ChpService;
import net.mamoe.mirai.event.EventHandler;
import net.mamoe.mirai.event.EventPriority;
import net.mamoe.mirai.event.ListeningStatus;
import net.mamoe.mirai.event.SimpleListenerHost;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.message.data.At;
import net.mamoe.mirai.message.data.Message;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Random;

/**
 * @Description: 非指令消息的消息事件
 * @author: magic chen
 * @date: 2020/9/8 11:40
 **/
@Component
public class MessageEvents extends SimpleListenerHost {

    private static final Logger logger = LoggerFactory.getLogger(MessageEvents.class);


    private Random random = new Random();

    @Autowired
    ChpService chpService;

    @NotNull
    @EventHandler(priority = EventPriority.NORMAL)
    public ListeningStatus chpModel(@NotNull GroupMessageEvent event) throws Exception {
        long gid = event.getGroup().getId();
        long uid = event.getSender().getId();
        if (chpService.checkChp(gid, uid)) {
            int seed = random.nextInt(100);
            if (seed > 50) {
                Message msg = new At(uid).plus(chpService.getChp());
                event.getSubject().sendMessage(msg);
            }
        }
        return ListeningStatus.LISTENING;
    }

}
