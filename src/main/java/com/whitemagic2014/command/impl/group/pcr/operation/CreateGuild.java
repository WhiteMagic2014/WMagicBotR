package com.whitemagic2014.command.impl.group.pcr.operation;

import com.whitemagic2014.command.impl.group.pcr.PcrOwnerCommand;
import com.whitemagic2014.pojo.CommandProperties;
import com.whitemagic2014.pojo.PrivateModel;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.contact.Member;
import net.mamoe.mirai.message.data.Message;
import net.mamoe.mirai.message.data.MessageChain;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

/**
 * @Description: 创建公会
 * @author: magic chen
 * @date: 2020/8/22 11:13
 **/
@Component
public class CreateGuild extends PcrOwnerCommand {


    @Override
    public CommandProperties properties() {
        return new CommandProperties("创建工会", "创建公会");
    }

    @Override
    protected Message executeHandle(Member sender, ArrayList<String> args, MessageChain messageChain, Group subject) {
        PrivateModel<String> createResult = pcrBotService.createGuild(subject.getId(), subject.getName());
        return simpleMsg(sender, createResult);
    }


}

