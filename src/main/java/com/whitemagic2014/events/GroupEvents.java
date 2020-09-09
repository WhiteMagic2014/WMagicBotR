package com.whitemagic2014.events;

import net.mamoe.mirai.event.EventHandler;
import net.mamoe.mirai.event.Listener;
import net.mamoe.mirai.event.ListeningStatus;
import net.mamoe.mirai.event.SimpleListenerHost;
import net.mamoe.mirai.event.events.MemberJoinEvent;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

/**
 * @Description: 群事件
 * @author: magic chen
 * @date: 2020/9/9 17:49
 **/
@Component
public class GroupEvents extends SimpleListenerHost {


    /**
     * @Name: onMemberJoinEvent
     * @Description: 加群事件
     * @Param: event
     * @Return: net.mamoe.mirai.event.ListeningStatus
     * @Author: magic chen
     * @Date: 2020/9/9 18:20
     **/
    @NotNull
    @EventHandler(priority = Listener.EventPriority.NORMAL)
    public ListeningStatus onMemberJoinEvent(@NotNull MemberJoinEvent event) {
        String name = event.getMember().getNameCard();
        event.getGroup().sendMessage("欢迎: " + name);
        return ListeningStatus.LISTENING; // 表示继续监听事件
    }

}
