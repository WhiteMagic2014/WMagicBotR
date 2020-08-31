package com.whitemagic2014.events;

import kotlin.coroutines.CoroutineContext;
import net.mamoe.mirai.event.EventHandler;
import net.mamoe.mirai.event.ListeningStatus;
import net.mamoe.mirai.event.SimpleListenerHost;
import net.mamoe.mirai.event.events.MessageRecallEvent;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


/**
 * @Description: 撤回事件
 * @author: magic chen
 * @date: 2020/8/20 23:28
 **/
@Component
public class RecallEvent extends SimpleListenerHost {

    private static Logger logger = LoggerFactory.getLogger(RecallEvent.class);

    @Override
    public void handleException(@NotNull CoroutineContext context, @NotNull Throwable exception) {
        // 貌似无法捕获异常
        logger.error("RecallEvent Error:", exception.getMessage());
    }


    /**
     * @Name: onGroupRecall
     * @Description: 监听群聊撤回
     * @Param: event
     * @Return: net.mamoe.mirai.event.ListeningStatus
     * @Author: magic chen
     * @Date: 2020/8/20 23:55
     **/
    @EventHandler
    public ListeningStatus onGroupRecall(@NotNull MessageRecallEvent.GroupRecall event) {
        String result = event.getOperator().getNameCard();
        System.out.println(result + " 撤回了" + event.getMessageId());
        event.getGroup().sendMessage(result + " 撤回了" + event.getMessageId());
        return ListeningStatus.LISTENING; // 表示继续监听事件
    }


    /**
     * @Name: onFriendRecall
     * @Description: 监听私聊撤回 暂时不可用
     * @Param: event
     * @Return: net.mamoe.mirai.event.ListeningStatus
     * @Author: magic chen
     * @Date: 2020/8/20 23:56
     **/
    @EventHandler
    public ListeningStatus onFriendRecall(@NotNull MessageRecallEvent.FriendRecall event) {
        String result = event.getOperator() + "";
        System.out.println(result + " 撤回了" + event.getMessageId());
        event.getBot().getFriend(event.getOperator()).sendMessage("你撤回了 " + event.getMessageId());
        return ListeningStatus.LISTENING; // 表示继续监听事件
    }

}
