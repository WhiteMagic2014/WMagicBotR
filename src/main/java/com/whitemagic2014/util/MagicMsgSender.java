package com.whitemagic2014.util;

import com.whitemagic2014.bot.MagicBotR;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.message.data.Message;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @Description: 用来发送消息的工具类
 * @author: magic chen
 * @date: 2020/9/29 16:31
 **/
public class MagicMsgSender {

    private static Bot bot = MagicBotR.getBot();


    /**
     * @Name: sendGroupMsg
     * @Description: 发送群消息
     * @Param: groupId
     * @Param: msg
     * @Return: void
     * @Author: magic chen
     * @Date: 2020/9/29 17:02
     **/
    public static void sendGroupMsg(Long groupId, Message msg) {
        bot.getGroup(groupId).sendMessage(msg);
    }


    /**
     * @Name: sendGroupMsgDelay
     * @Description: 发送延时群消息
     * @Param: groupId
     * @Param: msg
     * @Param: delay
     * @Return: java.util.Timer
     * @Author: magic chen
     * @Date: 2020/9/29 17:49
     **/
    public static Timer sendGroupMsgDelay(Long groupId, Message msg, Long delay) {
        Timer t = new Timer();
        t.schedule(new TimerTask() {
            @Override
            public void run() {
                bot.getGroup(groupId).sendMessage(msg);
            }
        }, delay);
        return t;
    }

    /**
     * @Name: sendGroupMsgTiming
     * @Description: 发送定时群消息
     * @Param: groupId
     * @Param: msg
     * @Param: time
     * @Return: java.util.Timer
     * @Author: magic chen
     * @Date: 2020/9/29 17:50
     **/
    public static Timer sendGroupMsgTiming(Long groupId, Message msg, Date time) {
        Timer t = new Timer();
        t.schedule(new TimerTask() {
            @Override
            public void run() {
                bot.getGroup(groupId).sendMessage(msg);
            }
        }, time);
        return t;
    }

    /**
     * @Name: sendFriendMsg
     * @Description: 发送私聊消息
     * @Param: uid
     * @Param: msg
     * @Return: void
     * @Author: magic chen
     * @Date: 2020/9/29 17:12
     **/
    public static void sendFriendMsg(Long uid, Message msg) {
        bot.getFriend(uid).sendMessage(msg);
    }


    /**
     * @Name: sendFriendMsgDelay
     * @Description: 发送延时私聊消息
     * @Param: uid
     * @Param: msg
     * @Param: delay
     * @Return: java.util.Timer
     *
     * @Author: magic chen
     * @Date:   2020/9/29 18:05
     **/
    public static Timer sendFriendMsgDelay(Long uid, Message msg, Long delay) {
        Timer t = new Timer();
        t.schedule(new TimerTask() {
            @Override
            public void run() {
                bot.getFriend(uid).sendMessage(msg);
            }
        }, delay);
        return t;
    }


    /**
     * @Name: sendFriendMsgTiming
     * @Description: 发送定时私聊消息
     * @Param: uid
     * @Param: msg
     * @Param: time
     * @Return: java.util.Timer
     *
     * @Author: magic chen
     * @Date:   2020/9/29 18:06
     **/
    public static Timer sendFriendMsgTiming(Long uid, Message msg, Date time) {
        Timer t = new Timer();
        t.schedule(new TimerTask() {
            @Override
            public void run() {
                bot.getFriend(uid).sendMessage(msg);
            }
        }, time);
        return t;
    }


    /**
     * @Name: sendBoradcast
     * @Description: 发送广播消息
     * @Param: msg
     * @Return: void
     * @Author: magic chen
     * @Date: 2020/9/29 17:02
     **/
    public static void sendBoradcast(Message msg) {
        for (Group g : bot.getGroups()) {
            g.sendMessage(msg);
        }
    }


}
