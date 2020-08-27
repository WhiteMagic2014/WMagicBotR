package com.whitemagic2014.command.impl.everywhere;

import com.whitemagic2014.command.EverywhereCommand;
import com.whitemagic2014.pojo.PrivateModel;
import net.mamoe.mirai.contact.Member;
import net.mamoe.mirai.contact.User;
import net.mamoe.mirai.message.data.At;
import net.mamoe.mirai.message.data.Message;
import net.mamoe.mirai.message.data.PlainText;

/**
 * @Description: base everywhere command
 * @author: magic chen
 * @date: 2020/8/27 16:37
 **/
public abstract class BaseEveryWhereCommand implements EverywhereCommand {


    /**
     * @Name: simpleMsgStr
     * @Description: 直接返回文本，需要判断是不是在群环境下，如果是 需要@sender
     * @Param: sender
     * @Param: msg
     * @Return: net.mamoe.mirai.message.data.Message
     * @Author: magic chen
     * @Date: 2020/8/27 16:53
     **/
    protected Message simpleMsgStr(User sender, String msg) {
        if (sender instanceof Member) {
            return new At((Member) sender).plus(msg);
        } else {
            return new PlainText(msg);
        }
    }


    /**
     * @Name: simpleMsg
     * @Description: 简单的包装 特殊业务的话额外处理
     * @Param: sender
     * @Param: result
     * @Return: net.mamoe.mirai.message.data.Message
     * @Author: magic chen
     * @Date: 2020/8/27 16:53
     **/
    protected Message simpleMsg(User sender, PrivateModel<String> result) {
        if (result.isSuccess()) {
            return simpleMsgStr(sender, result.getReturnObject());
        } else {
            return simpleMsgStr(sender, result.getReturnMessage());
        }
    }


    /**
     * @Name: simpleErrMsg
     * @Description: 简单错误包装 一般在确定业务失败后调用
     * @Param: sender
     * @Param: result
     * @Return: net.mamoe.mirai.message.data.Message
     * @Author: magic chen
     * @Date: 2020/8/27 16:52
     **/
    protected <S> Message simpleErrMsg(User sender, PrivateModel<S> result) {
        return simpleMsgStr(sender, result.getReturnMessage());
    }

}
