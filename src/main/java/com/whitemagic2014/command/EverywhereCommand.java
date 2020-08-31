package com.whitemagic2014.command;

import net.mamoe.mirai.contact.Contact;
import net.mamoe.mirai.contact.User;
import net.mamoe.mirai.message.data.Message;
import net.mamoe.mirai.message.data.MessageChain;

import java.util.ArrayList;

/**
 * @Description: 适用于 好友私聊 群聊 群临时消息 情况
 * @author: magic chen
 * @date: 2020/8/20 22:34
 **/
public interface EverywhereCommand extends Command {

    /**
     * @Name: execute
     * @Description: 满足指令时执行
     * @Param: sender   发送人
     * @Param: args 参数
     * @Param: MessageChain  第一个元素一定为 [MessageSource], 存储此消息的发送人, 发送时间, 收信人, 消息 id 等数据. 随后的元素为拥有顺序的真实消息内容.
     * @Param: subject  消息事件主体
     * @Return: 需要回复的内容, 返回null不做处理
     * @Author: magic chen
     * @Date: 2020/8/20 22:49
     **/
    Message execute(User sender, ArrayList<String> args, MessageChain messageChain, Contact subject) throws Exception;

}
