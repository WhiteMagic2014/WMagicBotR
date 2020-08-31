package com.whitemagic2014.command.impl.group;

import com.whitemagic2014.command.GroupCommand;
import com.whitemagic2014.pojo.PrivateModel;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.contact.Member;
import net.mamoe.mirai.contact.MemberPermission;
import net.mamoe.mirai.message.data.At;
import net.mamoe.mirai.message.data.Message;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.message.data.MessageUtils;
import org.springframework.beans.factory.annotation.Value;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: base group command
 * @author: magic chen
 * @date: 2020/8/22 14:01
 **/
public abstract class BaseGroupCommand implements GroupCommand {

    @Value("${bot.admin}")
    protected Long adminUid;


    @Override
    public Message execute(Member sender, ArrayList<String> args, MessageChain messageChain, Group subject) throws Exception {
        // 总体判别权限,有些指令只能管理员 或者 群主执行
        PrivateModel checkResult = checkRole(sender, subject);
        if (!checkResult.isSuccess()) {
            return new At(sender).plus(" " + checkResult.getReturnMessage());
        }
        return executeHandle(sender, args, messageChain, subject);
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
     * @Date: 2020/8/23 18:30
     **/
    protected abstract Message executeHandle(Member sender, ArrayList<String> args, MessageChain messageChain, Group subject) throws Exception;


    /**
     * @Name: checkRole
     * @Description: 根据业务做前置 权限判断
     * @Param: sender
     * @Return: com.whitemagic2014.pojo.PrivateModel<java.lang.String>
     * @Author: magic chen
     * @Date: 2020/8/23 18:30
     **/
    protected abstract PrivateModel<String> checkRole(Member sender, Group subject);


    /**
     * @Name: isOwner
     * @Description: 判断成员是否是群主
     * @Param: member
     * @Return: boolean
     * @Author: magic chen
     * @Date: 2020/8/22 14:04
     **/
    protected boolean isGroupOwner(Member member) {
        MemberPermission permission = member.getPermission();
        if (permission == MemberPermission.OWNER) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * @Name: isAdmin
     * @Description: 判断成员是否是管理员
     * @Param: member
     * @Return: boolean
     * @Author: magic chen
     * @Date: 2020/8/22 14:05
     **/
    protected boolean isGroupAdmin(Member member) {
        MemberPermission permission = member.getPermission();
        if (permission == MemberPermission.ADMINISTRATOR || permission == MemberPermission.OWNER) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * @Name: simpleMsg
     * @Description: 简单的包装 特殊业务的话额外处理
     * @Param: sender
     * @Param: result
     * @Return: net.mamoe.mirai.message.data.Message
     * @Author: magic chen
     * @Date: 2020/8/23 17:48
     **/
    protected Message simpleMsg(Member sender, PrivateModel<String> result) {
        if (result.isSuccess()) {
            return new At(sender).plus(result.getReturnObject());
        } else {
            return new At(sender).plus(result.getReturnMessage());
        }
    }

    /**
     * @Name: simpleErrMsg
     * @Description: 简单错误包装 一般在确定业务失败后调用
     * @Param: sender
     * @Param: result
     * @Return: net.mamoe.mirai.message.data.Message
     * @Author: magic chen
     * @Date: 2020/8/23 18:25
     **/
    protected <S> Message simpleErrMsg(Member sender, PrivateModel<S> result) {
        return new At(sender).plus(result.getReturnMessage());
    }

    /**
     * @Name: makeAts
     * @Description: 组成at消息链
     * @Param: uids  用户qq号
     * @Param: subject
     * @Return: net.mamoe.mirai.message.data.MessageChain
     * @Author: magic chen
     * @Date: 2020/8/24 21:32
     **/
    protected MessageChain makeAts(List<Long> uids, Group subject) {
        MessageChain chain = MessageUtils.newChain();
        for (Long uid : uids) {
            At temp = new At(subject.get(uid));
            chain = chain.plus(temp);
        }
        return chain;
    }

}
