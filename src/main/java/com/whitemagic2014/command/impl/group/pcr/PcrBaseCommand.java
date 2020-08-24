package com.whitemagic2014.command.impl.group.pcr;

import com.whitemagic2014.command.impl.group.BaseGroupCommand;
import com.whitemagic2014.pojo.PrivateModel;
import com.whitemagic2014.service.PcrBotService;
import com.whitemagic2014.util.MagicLock;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.contact.Member;
import net.mamoe.mirai.message.data.At;
import net.mamoe.mirai.message.data.Message;
import net.mamoe.mirai.message.data.MessageChain;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;

/**
 * @Description: pcr 公会管理全局加锁 锁群号
 * @author: magic chen
 * @date: 2020/8/23 11:45
 **/
public abstract class PcrBaseCommand extends BaseGroupCommand {

    @Autowired
    protected PcrBotService pcrBotService;


    @Override
    public Message execute(Member sender, ArrayList<String> args, MessageChain messageChain, Group subject) {
        // 总体判别权限,有些指令只能管理员 或者 群主执行
        PrivateModel checkResult = checkRole(sender, subject);
        if (!checkResult.isSuccess()) {
            return new At(sender).plus(" " + checkResult.getReturnMessage());
        }
        // pcrbase 架构加锁
        Message msg = null;
        String lockKey = "MagicLock" + subject.getId();
        Object lock = MagicLock.getPrivateLock(lockKey);
        MagicLock.waitLock(lockKey, lock);//等锁
        synchronized (lock) {
            try {
                msg = executeHandle(sender, args, messageChain, subject);
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException();
            } finally {
                MagicLock.removePrivateLock(lockKey, lock);
            }
        }
        return msg;
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
     * @Name: isOwner
     * @Description: 是否是bot主人
     * @Param: gid
     * @Param: uid
     * @Return: boolean
     * @Author: magic chen
     * @Date: 2020/8/23 23:03
     **/
    protected boolean isOwner(Long gid, Long uid) {
        return pcrBotService.userIsOwner(gid, uid);
    }

    /**
     * @Name: isAdmin
     * @Description: 是否是bot管理员
     * @Param: gid
     * @Param: uid
     * @Return: boolean
     * @Author: magic chen
     * @Date: 2020/8/23 23:03
     **/
    protected boolean isAdmin(Long gid, Long uid) {
        return pcrBotService.userIsAdmin(gid, uid);
    }


}
