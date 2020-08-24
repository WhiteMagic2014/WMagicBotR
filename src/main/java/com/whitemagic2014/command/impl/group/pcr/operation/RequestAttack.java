package com.whitemagic2014.command.impl.group.pcr.operation;

import com.whitemagic2014.command.impl.group.pcr.PcrNoAuthCommand;
import com.whitemagic2014.pojo.CommandProperties;
import com.whitemagic2014.pojo.PrivateModel;
import com.whitemagic2014.pojo.pcr.BossLock;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.contact.Member;
import net.mamoe.mirai.message.data.Message;
import net.mamoe.mirai.message.data.MessageChain;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

/**
 * @Description: 申请出刀
 * @author: magic chen
 * @date: 2020/8/24 23:00
 **/
@Component
public class RequestAttack extends PcrNoAuthCommand {
    @Override
    protected Message executeHandle(Member sender, ArrayList<String> args, MessageChain messageChain, Group subject) {

        PrivateModel<String> result = pcrBotService.bossLock(subject.getId(), sender.getId(), sender.getNameCard(), BossLock.request);

        return simpleMsg(sender, result);
    }

    @Override
    public CommandProperties properties() {
        return new CommandProperties("申请出刀");
    }
}
