package com.whitemagic2014.command.impl.group.pcr.operation;

import com.whitemagic2014.command.impl.group.pcr.PcrNoAuthCommand;
import com.whitemagic2014.pojo.CommandProperties;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.contact.Member;
import net.mamoe.mirai.message.data.Message;
import net.mamoe.mirai.message.data.MessageChain;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

/**
 * @Description: 查询boss状态
 * @author: magic chen
 * @date: 2020/8/24 00:12
 **/
@Component
public class CheckBoss extends PcrNoAuthCommand {

    @Override
    protected Message executeHandle(Member sender, ArrayList<String> args, MessageChain messageChain, Group subject) {
        return simpleMsg(sender,pcrBotService.checkBossState(subject.getId()));
    }

    @Override
    public CommandProperties properties() {
        return new CommandProperties("状态");
    }
}
