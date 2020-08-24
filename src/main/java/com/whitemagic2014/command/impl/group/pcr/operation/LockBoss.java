package com.whitemagic2014.command.impl.group.pcr.operation;

import com.whitemagic2014.command.impl.group.pcr.PcrNoAuthCommand;
import com.whitemagic2014.pojo.CommandProperties;
import com.whitemagic2014.pojo.PrivateModel;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.contact.Member;
import net.mamoe.mirai.message.data.Message;
import net.mamoe.mirai.message.data.MessageChain;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

/**
 * @Description: 锁定boss
 * @author: magic chen
 * @date: 2020/8/24 23:08
 **/
@Component
public class LockBoss extends PcrNoAuthCommand {

    @Override
    protected Message executeHandle(Member sender, ArrayList<String> args, MessageChain messageChain, Group subject) {
        String msg;
        if (args.isEmpty()) {
            msg = "没有留言";
        } else {
            msg = args.get(0);
        }
        PrivateModel<String> result = pcrBotService.bossLock(subject.getId(), sender.getId(), sender.getNameCard(), msg);
        return simpleMsg(sender, result);
    }

    @Override
    public CommandProperties properties() {
        return new CommandProperties("锁定");
    }
}
