package com.whitemagic2014.command.impl.group.pcr;

import com.whitemagic2014.command.impl.group.BaseGroupCommand;
import com.whitemagic2014.dic.Dic;
import com.whitemagic2014.vo.PrivateModel;
import com.whitemagic2014.service.PcrBotService;
import com.whitemagic2014.annotate.Switch;
import com.whitemagic2014.util.MagicLock;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.contact.Member;
import net.mamoe.mirai.message.data.At;
import net.mamoe.mirai.message.data.Message;
import net.mamoe.mirai.message.data.MessageChain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;

/**
 * @Description: pcr 公会管理全局加锁 锁群号
 * @author: magic chen
 * @date: 2020/8/23 11:45
 **/
@Switch(name = Dic.Component_Pcr_Guild)
public abstract class PcrBaseCommand extends BaseGroupCommand {

    private static final Logger logger = LoggerFactory.getLogger(PcrBaseCommand.class);

    @Autowired
    protected PcrBotService pcrBotService;

    @Override
    public Message execute(Member sender, ArrayList<String> args, MessageChain messageChain, Group subject) {

        // 总体判别权限,有些指令只能管理员 或者 群主执行
        PrivateModel checkResult = checkRole(sender, subject);
        if (!checkResult.isSuccess()) {
            return new At(sender.getId()).plus(" " + checkResult.getReturnMessage());
        }
        // pcrbase 架构加锁
        Message msg;
        String lockKey = "MagicLock" + subject.getId();
        Object lock = MagicLock.getPrivateLock(lockKey);
        MagicLock.waitLock(lockKey, lock);//等锁
        synchronized (lock) {
            try {
                msg = executeHandle(sender, args, messageChain, subject);
            } catch (Exception e) {
                // pcr 层异常自己处理掉,不继续上抛
                logger.error("pcr command error:", e);
                throw new RuntimeException();//保证事务触发
            } finally {
                MagicLock.removePrivateLock(lockKey, lock);
            }
        }
        return msg;
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
