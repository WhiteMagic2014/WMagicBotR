package com.whitemagic2014.command.impl.friend;

import com.whitemagic2014.command.FriendCommand;
import com.whitemagic2014.pojo.PrivateModel;
import net.mamoe.mirai.contact.Friend;
import net.mamoe.mirai.contact.Member;
import net.mamoe.mirai.contact.User;
import net.mamoe.mirai.message.data.At;
import net.mamoe.mirai.message.data.Message;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.message.data.PlainText;

import java.util.ArrayList;

/**
 * @Description: Base Friend Command
 * @author: magic chen
 * @date: 2020/8/27 17:10
 **/
public abstract class BaseFriendCommand implements FriendCommand {
    @Override
    public Message execute(Friend sender, ArrayList<String> args, MessageChain messageChain, Friend subject) throws Exception {
        // 总体判别权限,有些指令只能 超管 执行
        PrivateModel checkResult = checkRole(sender, subject);
        if (!checkResult.isSuccess()) {
            return new PlainText(checkResult.getReturnMessage());
        }
        return executeHandle(sender, args, messageChain, subject);
    }


    /**
     * @Name: simpleMsg
     * @Description: 简单的包装 特殊业务的话额外处理
     * @Param: result
     * @Return: net.mamoe.mirai.message.data.Message
     * @Author: magic chen
     * @Date: 2020/8/27 18:07
     **/
    protected Message simpleMsg(PrivateModel<String> result) {
        if (result.isSuccess()) {
            return new PlainText(result.getReturnObject());
        } else {
            return new PlainText(result.getReturnMessage());
        }
    }

    /**
     * @Name: executeHandle
     * @Description: 返回msg 如果返回null 则不作处理
     * @Param: sender
     * @Param: args
     * @Param: messageChain
     * @Param: subject
     * @Return: net.mamoe.mirai.message.data.Message
     * @Author: magic chen
     * @Date: 2020/8/27 17:14
     **/
    protected abstract Message executeHandle(Friend sender, ArrayList<String> args, MessageChain messageChain, Friend subject) throws Exception;


    /**
     * @Name: checkRole
     * @Description: 根据业务做前置 权限判断
     * @Param: sender
     * @Param: subject
     * @Return: com.whitemagic2014.pojo.PrivateModel<java.lang.String>
     * @Author: magic chen
     * @Date: 2020/8/27 17:13
     **/
    protected abstract PrivateModel<String> checkRole(Friend sender, Friend subject);

}
